package ar.com.freesales;

import java.util.ArrayList;
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

/**
 * A example that demonstrates how HttpClient APIs can be used to perform
 * form-based logon.
 */
public class SplendiaAdapter {

    public static void main(String[] args) throws Exception {

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
            HttpGet httpget = new HttpGet("https://extranet.splendia.com/");

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            System.out.println("Login form get: " + response.getStatusLine());
            EntityUtils.consume(entity);

            HttpPost httpost = new HttpPost("https://extranet.splendia.com/?resource=redirector&component=RedirectLogin");

            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("login", "nuss"));
            nvps.add(new BasicNameValuePair("password", "delfina"));

            httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

            response = httpclient.execute(httpost);
            entity = response.getEntity();

            System.out.println("Login form get: " + response.getStatusLine());
            
            System.out.println(EntityUtils.toString(entity));
            EntityUtils.consume(entity);

            HttpPost httpost2 = new HttpPost("https://extranet.splendia.com/index.php?resource=ajax&component=AjaxInventory");
            
            List <NameValuePair> nvps2 = new ArrayList <NameValuePair>();
            nvps2.add(new BasicNameValuePair("availability", "{\"2013-03-30_61491\":{\"iNumRooms\":\"2\",\"iRoomStatus\":\"1\",\"iOldNumRooms\":\"\",\"iOldRoomStatus\":\"\"}}"));
            nvps2.add(new BasicNameValuePair("token_id", "36282"));

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
