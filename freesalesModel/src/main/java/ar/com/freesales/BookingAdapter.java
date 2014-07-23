package ar.com.freesales;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * A example that demonstrates how HttpClient APIs can be used to perform
 * form-based logon.
 */
public class BookingAdapter {
	public static final int DIA = 2;
	public static final int MES = Calendar.MARCH;
	public static final int ANIO = 2013;
	
	public static final String SUPERIOR= "24191301";
	public static final String DELUX = "24191302";
	
	public static final String TIPO_HAB = SUPERIOR;
	private static final int CANT_DISP = 5;
	

    public static void main(String[] args) throws Exception {
    	
    	
    	Calendar fecha = GregorianCalendar.getInstance();
    	fecha.add(Calendar.DATE, DIA);
    	fecha.add(Calendar.MONTH, MES);
    	fecha.add(Calendar.YEAR, ANIO);

        DefaultHttpClient httpclient = new DefaultHttpClient();
        
        httpclient.setRedirectStrategy(new DefaultRedirectStrategy() {                
            public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context)  {
                boolean isRedirect=false;
                try {
                    isRedirect = super.isRedirected(request, response, context);
                } catch (ProtocolException e) {
                    // TODO Auto-generated catch block
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
        try {
            HttpGet httpget = new HttpGet("http://admin.bookings.org/hotel/");

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            String pagina = EntityUtils.toString(entity);
            
            System.out.println(pagina);
            
            Document document = Jsoup.parse(pagina);
            
            Elements hiddens = document.getElementsByTag("input");
            
            String ses = hiddens.first().val();
            
            System.out.println("session id: " + ses);
            

            HttpPost httpost = new HttpPost("http://admin.bookings.org/hotel/login.html");

            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("loginname", "241913"));
            nvps.add(new BasicNameValuePair("password", "7365"));
            nvps.add(new BasicNameValuePair("ses", ses));
            nvps.add(new BasicNameValuePair("login", "Login"));

            httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

            response = httpclient.execute(httpost);
            entity = response.getEntity();

            System.out.println("Login form get: " + response.getStatusLine());
            
            System.out.println(EntityUtils.toString(entity));
            
            
            
            EntityUtils.consume(entity);

            HttpPost httpost2 = new HttpPost("http://admin.bookings.org/hotel/availadmin/set_room_inventory.html?ses="+ses+";hotel_id=");
            
            List <NameValuePair> nvps2 = new ArrayList <NameValuePair>();
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMinimumIntegerDigits(2);
            numberFormat.setGroupingUsed(false);
            
            
            //DIA INICIO
            String diaFormateado = numberFormat.format(DIA);
            String mesFormateado = numberFormat.format(MES+1);
            String anioFormateado = numberFormat.format(ANIO);
            
            
			nvps2.add(new BasicNameValuePair("dfd", diaFormateado));

//            ANIO Y MES
			nvps2.add(new BasicNameValuePair("dfym", anioFormateado+"-"+mesFormateado));
            
            
            nvps2.add(new BasicNameValuePair("dld", diaFormateado));
            nvps2.add(new BasicNameValuePair("dlym", anioFormateado+"-"+mesFormateado));
            nvps2.add(new BasicNameValuePair("dt", ""));
            nvps2.add(new BasicNameValuePair("grid_update","Update Grid Overview"));

//            datos habitaciones
            nvps2.add(new BasicNameValuePair("rir_"+TIPO_HAB+"_" +anioFormateado+mesFormateado+diaFormateado, String.valueOf(CANT_DISP) ));
            nvps2.add(new BasicNameValuePair("o_rir_"+TIPO_HAB+"_" +anioFormateado+mesFormateado+diaFormateado,String.valueOf(CANT_DISP -1)));
            

            httpost2.setEntity(new UrlEncodedFormEntity(nvps2, Consts.UTF_8));

            response = httpclient.execute(httpost2);
            entity = response.getEntity();

            System.out.println("Login form get: " + response.getStatusLine());
            
            System.out.println(EntityUtils.toString(entity));

        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }
}
