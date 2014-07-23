package ar.com.freesales.entity.ota;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import ar.com.freesales.entity.changelog.DateBatch;
import ar.com.freesales.entity.room.HotelOtaRoomType;
import ar.com.freesales.enums.ActionEnum;
import ar.com.freesales.enums.MessagesEnum;
import ar.com.freesales.exception.OtaDoActionException;
import ar.com.freesales.exception.OtaLoginException;

@Entity
@Table(name = "OTA")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="NAME",
    discriminatorType=DiscriminatorType.STRING
)
public abstract class Ota implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String urlMain;
	private String urlLog;
	private String name;
	private String urlChangeRate;
	private String urlChangeAvailability;
	private String urlChangeStock;
	protected final Logger logger = Logger.getLogger(this.getClass());
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	
	@Column(name = "URL_MAIN")
	public String getUrlMain() {
		return urlMain;
	}
	
	@Column(name = "URL_LOG")
	public String getUrlLog() {
		return urlLog;
	}
	
	@Column(name = "URL_AVAILABILITY")
	public String getUrlChangeAvailability() {
		return urlChangeAvailability;
	}
	
	@Column(name = "URL_RATE")
	public String getUrlChangeRate() {
		return urlChangeRate;
	}
	
	@Column(name = "URL_STOCK")
	public String getUrlChangeStock() {
		return urlChangeStock;
	}
	
	public void setUrlMain(String urlMain) {
		this.urlMain = urlMain;
	}
	
	public void setUrlLog(String urlLog) {
		this.urlLog = urlLog;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setUrlChangeAvailability(String urlChangeAvailability) {
		this.urlChangeAvailability = urlChangeAvailability;
	}
	
	public void setUrlChangeRate(String urlChangeRate) {
		this.urlChangeRate = urlChangeRate;
	}
	
	public void setUrlChangeStock(String urlChangeStock) {
		this.urlChangeStock = urlChangeStock;
	}
	
	// TODO NO SE ESTÁ CERRANDO EL HTTPCLIENT, ESTARIA BUENO ORQUESTAR CON EL LOGOUT
	//ejemplo
	// LOGIN(HTTPcLIENT)
	// doSOMETHING(HTTPcLIENT)
	// LOGOUT(HTTPcLIENT) (HAY QUE CERRARLO, SE VAN A GENERAR VARIAS SESIONES)
	// VER CON EL CHINO
	
	
//	BUSINESS METHODS
	public final Map<String,String> login(String user, String pass, DefaultHttpClient httpClient) throws OtaLoginException{
	
		if(httpClient==null){
			httpClient = buildHttpClient();
		}
		if (logger.isDebugEnabled()){
			logger.debug("## START LOGIN OTA: " + this.getName() + " ...");
		}
		Map<String,String> otaParams = this.initLogin(user, pass, httpClient);
		
		if (logger.isDebugEnabled()){
			logger.debug("## DO LOGIN OTA: " + this.getName() + " ...");
		}
		HttpResponse response = this.doLogin(user, pass, httpClient, otaParams);
		this.checkLogin(response,otaParams);
		if (logger.isDebugEnabled()){
			logger.debug("## LOGIN SUCCESSFUL OTA: " + this.getName() + "##");
		}
//		if(createHttpClient){
//			httpClient.getConnectionManager().shutdown();
//    	}
		return otaParams;
		
	}
	private Map<String,String> initLogin(String user, String pass, DefaultHttpClient httpClient) throws OtaLoginException{
		HttpEntity entity = null;
		try{
			HttpGet httpget = new HttpGet(getUrlMain());
			HttpResponse response = httpClient.execute(httpget);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
				throw new OtaLoginException(MessagesEnum.OTA_LOGIN_PAGE_NOT_FOUND.getValue());
			}
			entity = response.getEntity();
			return initLoginParameters(response);
		}catch (Exception e) {
			logger.error(e,e);
			throw new OtaLoginException(MessagesEnum.OTA_LOGIN_GENERAL_ERROR.getValue());
		}finally{
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				logger.error(e,e);
				throw new OtaLoginException(MessagesEnum.OTA_LOGIN_GENERAL_ERROR.getValue());
			}
		}
		
	}
	

	protected abstract HttpResponse doLogin(String user, String pass, DefaultHttpClient httpClient,Map<String,String> initLoginParam) throws OtaLoginException;
	protected abstract void checkLogin(HttpResponse response,Map<String,String> otaParams) throws OtaLoginException;
	protected Map<String, String> initLoginParameters(HttpResponse response) throws OtaLoginException{
		return null;
	}
	
	
	public abstract void doChangeRate(List<DateBatch> dateBatchs, String roomTypeCategoryCode ,String roomTypeCode, Integer value, String user, String pass) throws OtaLoginException, OtaDoActionException;
	public abstract void doChangeAvailability(List<DateBatch> dateBatchs, String roomTypeCode, boolean open, String user, String pass) throws OtaLoginException, OtaDoActionException;
	public abstract void doChangeStock(List<DateBatch> dateBatchs, String roomTypeCode, Integer value, String user, String pass) throws OtaLoginException, OtaDoActionException;
	protected DefaultHttpClient buildHttpClient() {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.setRedirectStrategy(new DefaultRedirectStrategy() {                
            public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context)  {
                boolean isRedirect=false;
                try {
                    isRedirect = super.isRedirected(request, response, context);
                } catch (ProtocolException e) {
                	
                    e.printStackTrace();
                }
                if (!isRedirect) {
                    int responseCode = response.getStatusLine().getStatusCode();
                    if (responseCode == 301 || responseCode == 302) {
                        return true;
                    }
                }
                return isRedirect;
            }
        });
		return httpclient;
	}

	public boolean supportMassiveAction(ActionEnum actionEnum) {
		return true;
	}
	
	@Transient
	public String getOpenCloseAdditionalInfo(HotelOtaRoomType hotelOtaRoomType) {
		// TODO Auto-generated method stub
		return null;
	}
}

