package ar.com.freesales.entity.ota;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import ar.com.freesales.entity.changelog.DateBatch;
import ar.com.freesales.enums.MessagesEnum;
import ar.com.freesales.enums.SplendiaInfoEnum;
import ar.com.freesales.exception.OtaDoActionException;
import ar.com.freesales.exception.OtaLoginException;
import ar.com.freesales.utils.ParseUtil;


@Entity
@DiscriminatorValue("SPLENDIA")
public class Splendia extends Ota{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2663616597322751420L;
	
	private Logger log = Logger.getLogger(Splendia.class);

	@Override
	public String toString() {
		return "soy splendia";
	}
	
	@Override
	protected void checkLogin(HttpResponse response,Map<String,String> otaParams) throws OtaLoginException {
		Boolean containData = ParseUtil.containData(response, SplendiaInfoEnum.LOGIN_WRONG_USER_PASS.getValue());
		if (containData){
			throw new OtaLoginException(MessagesEnum.OTA_LOGIN_FAILED.getValue());
		}
		
	}

	@Override
	public HttpResponse doLogin(String user, String pass, DefaultHttpClient httpclient, Map<String,String> initParam) throws OtaLoginException{
        try {
            HttpPost httpost = new HttpPost(getUrlLog());
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("login", user));
            nvps.add(new BasicNameValuePair("password", pass));
            httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            return httpclient.execute(httpost);
        }catch (Exception e) {
        	logger.error(e,e);
        	throw new OtaLoginException(MessagesEnum.OTA_LOGIN_GENERAL_ERROR.getValue());
		}
	}
	
	private HttpResponse doRequest(DefaultHttpClient httpclient, HttpPost httpPost){
	    HttpResponse response = null;
		try {
			response = httpclient.execute(httpPost);
		}  catch (Exception e) {
			logger.error(e,e);
			throw new RuntimeException(e);
		} finally{
			httpclient.getConnectionManager().shutdown();
		}
		return response;
	}
	
	@Override
	public void doChangeRate(List<DateBatch> dateBatchs,String roomTypeCategoryCode, String roomTypeCode,
			Integer value, String user, String pass) throws OtaLoginException, OtaDoActionException {
		
		log.info("voy a ejecutar un change Rate");
		DefaultHttpClient httpclient = buildHttpClient();
		login(user, pass, httpclient);
		HttpPost httpRatePost = new HttpPost(this.getUrlChangeRate());
        List<NameValuePair> postParams = buildChangeRatePost(dateBatchs,roomTypeCode,value);
        log.debug("postParams: " + postParams);
        httpRatePost.setEntity(new UrlEncodedFormEntity(postParams, Consts.UTF_8));
        HttpResponse response = doRequest(httpclient, httpRatePost);
        Boolean saved = ParseUtil.containData(response, SplendiaInfoEnum.CHANGE_DATA_SAVED.getValue());
        if (!saved){
        	log.info("fallo");
        	throw new OtaDoActionException(MessagesEnum.OTA_ACTION_CHANGE_RATE.getValue());
        }
        log.info("salvo ok");
	}

	@Override
//	FIXME: arreglar el tema del open close
	public void doChangeAvailability(List<DateBatch> dateBatchs,
			String roomTypeCode, boolean open, String user, String pass) throws OtaLoginException, OtaDoActionException {
		DefaultHttpClient httpclient = buildHttpClient();
		login(user, pass, httpclient);
        HttpPost httpost2 = new HttpPost(this.getUrlChangeAvailability());
        List<NameValuePair> nvps2 = buildChangeAvailabilityPost(dateBatchs,roomTypeCode, open);
        httpost2.setEntity(new UrlEncodedFormEntity(nvps2, Consts.UTF_8));
        HttpResponse response = this.doRequest(httpclient, httpost2);
        Boolean saved = ParseUtil.containData(response, SplendiaInfoEnum.CHANGE_DATA_SAVED.getValue());
        if (!saved){
        	throw new OtaDoActionException(MessagesEnum.OTA_ACTION_CHANGE_AVAILABILITY.getValue());
        }
	}
	
	@Override
	public void doChangeStock(List<DateBatch> dateBatchs, String roomTypeCode,
			Integer value, String user, String pass) throws OtaLoginException, OtaDoActionException {
		DefaultHttpClient httpclient = buildHttpClient();
		login(user, pass, httpclient);
        HttpPost httpost2 = new HttpPost(this.getUrlChangeStock());
        List<NameValuePair> nvps2 = buildChangeStockPost(dateBatchs,roomTypeCode,value);
        httpost2.setEntity(new UrlEncodedFormEntity(nvps2, Consts.UTF_8));
        HttpResponse response =  this.doRequest(httpclient, httpost2);
        Boolean saved = ParseUtil.containData(response, SplendiaInfoEnum.CHANGE_DATA_SAVED.getValue());
        if (!saved){
        	throw new OtaDoActionException(MessagesEnum.OTA_ACTION_CHANGE_STOCK.getValue());
        }
	}
	
	public List<NameValuePair> buildChangeAvailabilityPost(List<DateBatch> dateBatchs, String roomTypeCode, boolean open) {
		
//		ejemplo: newValues[ratePlans][102158][2013-10-22][availability]=3
		
		List <NameValuePair> nvps2 = new ArrayList <NameValuePair>();
		
		StringBuffer buffer = null;
		for (DateBatch dateBatch : dateBatchs) {
			buffer = new StringBuffer();
			buffer.append("newValues");
			buffer.append("[ratePlans]");
			buildDatesPost(roomTypeCode, buffer, dateBatch);
			buffer.append("[availability]");
			nvps2.add(new BasicNameValuePair(buffer.toString(), open?"1":"3"));
			
			//le mando también el oldValue
			buffer = new StringBuffer();
			buffer.append("oldValues");
			buffer.append("[ratePlans]");
			buildDatesPost(roomTypeCode, buffer, dateBatch);
			buffer.append("[availability]");
			nvps2.add(new BasicNameValuePair(buffer.toString(), open?"3":"1"));
		}
		
		return nvps2;
	}
	

	public List<NameValuePair> buildChangeStockPost(List<DateBatch> dateBatchs, String roomTypeCode, Integer value) {
//		ejemplo: newValues[rooms][61491][2013-10-20][stock]
		
		List <NameValuePair> nvps2 = new ArrayList <NameValuePair>();
		
		StringBuffer buffer = null;
		for (DateBatch dateBatch : dateBatchs) {
			buffer = new StringBuffer();
			buffer.append("newValues");
			buffer.append("[rooms]");
			buildDatesPost(roomTypeCode, buffer, dateBatch);
			buffer.append("[price]");
			nvps2.add(new BasicNameValuePair(buffer.toString(), String.valueOf(value.intValue())));
			
			//le mando también el oldValue
			buffer = new StringBuffer();
			buffer.append("oldValues");
			buffer.append("[rooms]");
			buildDatesPost(roomTypeCode, buffer, dateBatch);
			buffer.append("[price]");
			nvps2.add(new BasicNameValuePair(buffer.toString(), String.valueOf(value.intValue()+1)));
		}
		
		return nvps2;

	}
	
	public List<NameValuePair> buildChangeRatePost(List<DateBatch> dateBatchs, String roomTypeCode, Integer value) {
		
//		ejemplo: newValues[ratePlans][102158][2013-10-20][price]=300
		
		List <NameValuePair> nvps2 = new ArrayList <NameValuePair>();
		
		StringBuffer buffer = null;
		for (DateBatch dateBatch : dateBatchs) {
			buffer = new StringBuffer();
			buffer.append("newValues");
			buffer.append("[ratePlans]");
			buildDatesPost(roomTypeCode, buffer, dateBatch);
			buffer.append("[price]");
			nvps2.add(new BasicNameValuePair(buffer.toString(), String.valueOf(value)));
			
			//le mando también el oldValue
			buffer = new StringBuffer();
			buffer.append("oldValues");
			buffer.append("[ratePlans]");
			buildDatesPost(roomTypeCode, buffer, dateBatch);
			buffer.append("[price]");
			nvps2.add(new BasicNameValuePair(buffer.toString(), String.valueOf(value+1)));
		}
		
		return nvps2;
	}

	private void buildDatesPost(String roomTypeCode, StringBuffer buffer,
			DateBatch dateBatch) {
		buffer.append("[");
		buffer.append(roomTypeCode);
		buffer.append("][");
		buffer.append(dateBatch.getYearAsString());
		buffer.append("-");
		buffer.append(dateBatch.getMonthAsStringTwoDigits());
		buffer.append("-");
		buffer.append(dateBatch.getDayAsString());
		buffer.append("]");
	}
	
}
