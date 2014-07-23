package ar.com.freesales.entity.ota;

import static ar.com.freesales.enums.BookingInfoEnum.CHANGE_PARAM_END_DAY;
import static ar.com.freesales.enums.BookingInfoEnum.CHANGE_PARAM_END_MONTH_AND_YEAR;
import static ar.com.freesales.enums.BookingInfoEnum.CHANGE_PARAM_INIT_DAY;
import static ar.com.freesales.enums.BookingInfoEnum.CHANGE_PARAM_INIT_MONTH_AND_YEAR;
import static ar.com.freesales.enums.BookingInfoEnum.CHANGE_STOCK_NEW_VALUE;
import static ar.com.freesales.enums.BookingInfoEnum.CHANGE_STOCK_OLD_VALUE;
import static ar.com.freesales.enums.BookingInfoEnum.HOTEL_ID;
import static ar.com.freesales.enums.BookingInfoEnum.LOGIN_PARAM_SESSION;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import ar.com.freesales.entity.changelog.DateBatch;
import ar.com.freesales.entity.room.BookingAdditionalInfo;
import ar.com.freesales.entity.room.HotelOtaRoomType;
import ar.com.freesales.enums.ActionEnum;
import ar.com.freesales.exception.OtaDoActionException;
import ar.com.freesales.exception.OtaLoginException;
import ar.com.freesales.utils.DateUtil;
import ar.com.freesales.utils.ParseUtil;

@Entity
@DiscriminatorValue("BOOKING")
public class Booking extends Ota{
	
	private static final Logger log = Logger.getLogger(Booking.class);

	@Override
	public String toString() {
		return "soy booking";
	}
	
	@Override
	protected Map<String, String> initLoginParameters(HttpResponse response) throws OtaLoginException {
    	String pagina;
    	Map<String,String> params = new HashMap<String,String>();
    	HttpEntity entity = null;
		try {
			entity = response.getEntity();
			pagina = EntityUtils.toString(entity);
			Document document = Jsoup.parse(pagina);
			Elements hiddens = document.getElementsByTag("input");
			params.put(LOGIN_PARAM_SESSION.getValue(), hiddens.first().val());
			return params;
		} catch (Exception e) {
			logger.error(e,e);
			throw new OtaLoginException();
		}finally{
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				logger.error(e,e);
			}
		}
	}
	
	//FIXME esta mal la forma de validar si no se logueo ok!
	@Override
	protected void checkLogin(HttpResponse response, Map<String,String> otaParams) throws OtaLoginException {
		HttpEntity entity = response.getEntity();
	    String pagina;
		try {
			pagina = EntityUtils.toString(entity);
			Document document = Jsoup.parse(pagina);
			String html = document.html();
			if (html.contains("Login failed") || html.contains("Please login again, your session has expired")){
				throw new OtaLoginException("Usuario / password incorrecto o Sesion expirada");
			}
		} catch (Exception e) {
			logger.error(e,e);
			throw new OtaLoginException();
		} finally{
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				logger.error(e,e);
			}
		}
	}

	@Override
	public HttpResponse doLogin(String user, String pass, DefaultHttpClient httpclient, Map<String,String> initLoginParam) throws OtaLoginException{
		
        try {
            String time = String.valueOf((new Date()).getTime());
			String urlLog = MessageFormat.format(getUrlLog(), initLoginParam.get(LOGIN_PARAM_SESSION.getValue()),user,time);
            if (logger.isDebugEnabled()){
            	logger.debug("UrlLog " + urlLog);
            }
            HttpPost httpost = new HttpPost(urlLog);
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("login", "Login"));
            nvps.add(new BasicNameValuePair("loginname", user));
            nvps.add(new BasicNameValuePair("password", pass));
            nvps.add(new BasicNameValuePair("ses", initLoginParam.get(LOGIN_PARAM_SESSION.getValue())));
            httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            HttpResponse response = httpclient.execute(httpost);
			return response;
        }catch (Exception e) {
        	logger.error(e,e);
			throw new OtaLoginException(e.getMessage());
		}
	}
	
	private HttpResponse doRequest(DefaultHttpClient httpclient, HttpRequestBase httpPost){
	    HttpResponse response = null;
		try {
			response = httpclient.execute(httpPost);
		}  catch (Exception e) {
			logger.error(e,e);
			throw new RuntimeException(e);
		}
		return response;
	}
	
	@Override
//	booking no soporta open close masivo
	public boolean supportMassiveAction(ActionEnum actionEnum) {
		return (actionEnum != ActionEnum.CHANGE_AVAILABILITY);
	}

	@Override
	public void doChangeRate(List<DateBatch> dateBatchs, String roomTypeCategoryCode, String roomTypeCode,
			Integer value, String user, String pass) throws OtaLoginException,
			OtaDoActionException {
		DefaultHttpClient httpclient = buildHttpClient();
		Map<String, String> otaParams = login(user, pass, httpclient);
		otaParams.put(HOTEL_ID.getValue(), user);
		
		
		//0. hago el get de change rate
		String urlChangeRate = MessageFormat.format(getUrlChangeRate(), otaParams.get(LOGIN_PARAM_SESSION.getValue()),user,roomTypeCategoryCode, roomTypeCode);
		HttpGet httpGetRoom = new HttpGet(urlChangeRate);
		HttpResponse doRequest = this.doRequest(httpclient, httpGetRoom);
		try {
			EntityUtils.consume(doRequest.getEntity());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		//1. configuro las fechas
		DateUtil dateUtil = new DateUtil(dateBatchs);
		
		HttpPost postRate0 = new HttpPost(urlChangeRate);
		List<NameValuePair> param0 = new ArrayList<NameValuePair>();
		
		
		param0.add(new BasicNameValuePair(CHANGE_PARAM_INIT_DAY.getValue(), dateUtil.firstDayAsString()));
		param0.add(new BasicNameValuePair(CHANGE_PARAM_INIT_MONTH_AND_YEAR.getValue(), dateUtil.firstYearAsString() + "-" + dateUtil.firstMonthAsStringTwoDigit()));
		param0.add(new BasicNameValuePair(CHANGE_PARAM_END_DAY.getValue(), dateUtil.lastDayAsString()));
		param0.add(new BasicNameValuePair(CHANGE_PARAM_END_MONTH_AND_YEAR.getValue(), dateUtil.lastYearAsString() + "-" + dateUtil.lastMonthAsStringTwoDigit()));
		param0.add(new BasicNameValuePair("go", "Select Period"));
		param0.add(new BasicNameValuePair(HOTEL_ID.getValue(), user));
		
		postRate0.setEntity(new UrlEncodedFormEntity(param0, Consts.UTF_8));
		HttpResponse response2 = this.doRequest(httpclient, postRate0);
		String stringDt = ParseUtil.getElementsDataByName(response2, "dt").get("dt");		
		
		//1. ejecuto el cambio
		HttpPost httpPostChangeRate = null;
		HttpResponse response = null;
		httpPostChangeRate = new HttpPost(urlChangeRate);
		httpPostChangeRate.addHeader("Referer", urlChangeRate);
		List<NameValuePair> paramsChangeRate = new ArrayList<NameValuePair>();
		paramsChangeRate.add(new BasicNameValuePair(HOTEL_ID.getValue(), user));
		paramsChangeRate.add(new BasicNameValuePair(LOGIN_PARAM_SESSION.getValue(),otaParams.get(LOGIN_PARAM_SESSION.getValue())));
		paramsChangeRate.add(new BasicNameValuePair("dt", stringDt));
		for (DateBatch date : dateBatchs) {
			paramsChangeRate.add(new BasicNameValuePair("f1pr" + dateUtil.daysBetween(date.getDate()) , value.toString()));
		}
		httpPostChangeRate.setEntity(new UrlEncodedFormEntity(paramsChangeRate, Consts.UTF_8));
		response = this.doRequest(httpclient, httpPostChangeRate);
		Map<String, String> element = ParseUtil.getElementsDataByName(response, "f1pr0");
		if (element == null || element.size() == 0 || !value.equals(Integer.valueOf(element.get("f1pr0")))){
			throw new OtaLoginException("error en el camio de valor");
		}
		
		httpclient.getConnectionManager().shutdown();
		
	}
	
	@Override
	public void doChangeAvailability(List<DateBatch> dateBatchs,
			String roomTypeCode, boolean open, String user, String pass)
			throws OtaLoginException, OtaDoActionException {
		//0 es open, 1 es close
		int action = open ? 0 : 1;
		
		DefaultHttpClient httpclient = buildHttpClient();
		Map<String, String> otaParams = login(user, pass, httpclient);
		
		String urlChangeAvailability = MessageFormat.format(getUrlChangeAvailability(), otaParams.get(LOGIN_PARAM_SESSION.getValue()),user);
		
		//1. configuro las fechas
//		como no soporta masivo, la lista de dateBatch debería ser de un solo elemento. 
		
		DateUtil dateUtil = new DateUtil(dateBatchs);
		
		HttpPost postAvailability0 = new HttpPost(urlChangeAvailability);
		List<NameValuePair> param0 = new ArrayList<NameValuePair>();
		
		
		param0.add(new BasicNameValuePair(CHANGE_PARAM_INIT_DAY.getValue(), dateUtil.firstDayAsString()));
		param0.add(new BasicNameValuePair(CHANGE_PARAM_INIT_MONTH_AND_YEAR.getValue(), dateUtil.firstYearAsString() + "-" + dateUtil.firstMonthAsStringTwoDigit()));
		param0.add(new BasicNameValuePair(CHANGE_PARAM_END_DAY.getValue(), dateUtil.lastDayAsString()));
		param0.add(new BasicNameValuePair(CHANGE_PARAM_END_MONTH_AND_YEAR.getValue(), dateUtil.lastYearAsString() + "-" + dateUtil.lastMonthAsStringTwoDigit()));
		param0.add(new BasicNameValuePair("go", "Select Period"));
		param0.add(new BasicNameValuePair(HOTEL_ID.getValue(), user));
		
		postAvailability0.setEntity(new UrlEncodedFormEntity(param0, Consts.UTF_8));
		HttpResponse response2 = this.doRequest(httpclient, postAvailability0);
		String stringDt = ParseUtil.getElementsDataByName(response2, "dt").get("dt");		
		
		
		
		StringBuffer url = new StringBuffer(urlChangeAvailability);
		url.append(";");
		url.append(roomTypeCode);
		url.append("0=");
		url.append(action);
//		https://admin.bookings.org/hotel/availadmin/index.html?ses=2da0339b59639f65deb60ae64cebea73;hotel_id=241913;f6cl0=1
		log.debug("por invocar: " + url);
		
		HttpGet getAvailability = new HttpGet(url.toString());
		getAvailability.addHeader("Referer", urlChangeAvailability);
		HttpResponse response = this.doRequest(httpclient, getAvailability);
		
		Map<String, String> element = ParseUtil.getElementsDataByName(response, "f1pr0");
		
	}

	@Override
	public void doChangeStock(List<DateBatch> dateBatchs, String roomTypeCode,
			Integer value, String user, String pass) throws OtaLoginException,
			OtaDoActionException {

		
		DefaultHttpClient httpclient = buildHttpClient();
		Map<String, String> otaParams = login(user, pass, httpclient);
		
		String urlChangeStock = MessageFormat.format(getUrlChangeStock(), otaParams.get(LOGIN_PARAM_SESSION.getValue()),user);
		
		HttpPost postStock = new HttpPost(urlChangeStock);
		List <NameValuePair> params = new ArrayList <NameValuePair>();
        
		String lastStockNewValue=null,lastStockOldValue=null;
        DateBatch firstDate = dateBatchs.get(0);
        params.add(new BasicNameValuePair(CHANGE_PARAM_INIT_DAY.getValue(), firstDate.getDayAsString()));
        params.add(new BasicNameValuePair(CHANGE_PARAM_INIT_MONTH_AND_YEAR.getValue(), firstDate.getYearAsString()+"-"+firstDate.getMonthAsStringTwoDigits()));
        DateBatch lastDate = dateBatchs.get(dateBatchs.size()-1);
        params.add(new BasicNameValuePair(CHANGE_PARAM_END_DAY.getValue(), lastDate.getDayAsString()));
        params.add(new BasicNameValuePair(CHANGE_PARAM_END_MONTH_AND_YEAR.getValue(), lastDate.getYearAsString()+"-"+lastDate.getMonthAsStringTwoDigits()));
        params.add(new BasicNameValuePair("dt", ""));
        params.add(new BasicNameValuePair("grid_update","Update Grid Overview"));

        for (DateBatch dateBatch : dateBatchs) {
			lastStockNewValue = CHANGE_STOCK_NEW_VALUE.getValue()+roomTypeCode+"_" +dateBatch.getYearAsString()+dateBatch.getMonthAsStringTwoDigits()+dateBatch.getDayAsStringTwoDigits();
			params.add(new BasicNameValuePair(lastStockNewValue,value.toString()));
			lastStockOldValue = CHANGE_STOCK_OLD_VALUE.getValue()+roomTypeCode+"_" +dateBatch.getYearAsString()+dateBatch.getMonthAsStringTwoDigits()+dateBatch.getDayAsStringTwoDigits();
			params.add(new BasicNameValuePair(lastStockOldValue,String.valueOf(value -1)));
		}
		postStock.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        HttpResponse response = this.doRequest(httpclient, postStock);
        Map<String, String> responseElements = ParseUtil.getElementsDataByName(response, lastStockOldValue,lastStockNewValue);
        if (responseElements != null  ){
        	String val = responseElements.get(lastStockNewValue);
        	if (!value.equals(val)){
        		throw new OtaDoActionException("error");
        	}
        }
        
	}
	
	@Override
	public String getOpenCloseAdditionalInfo(HotelOtaRoomType hotelOtaRoomType) {
		// TODO Auto-generated method stub
//		ej: f6cl
		BookingAdditionalInfo bookingAdditionalInfo = hotelOtaRoomType.getBookingAdditionalInfo();
		String numberOpenClose = bookingAdditionalInfo.getNumberOpenClose();
		String codeOpenClose = bookingAdditionalInfo.getCodeOpenClose();
		
		return "f" + numberOpenClose + codeOpenClose;
	}
	
}
