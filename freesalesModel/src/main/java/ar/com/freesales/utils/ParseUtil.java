package ar.com.freesales.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ParseUtil {
	
	private static final Logger logger = Logger.getLogger(ParseUtil.class);
	
	public static Boolean containData(HttpResponse response, String dataToSearch){
		HttpEntity entity = response.getEntity();
	    String pagina;
		try {
			pagina = EntityUtils.toString(entity);
			Document document = Jsoup.parse(pagina);
			if (document.html().contains(dataToSearch)){
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error(e,e);
			throw new RuntimeException(e);
		} finally{
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				logger.error(e,e);
			}
		}
	}
	
	public static Map<String,String> getElementsDataByName(HttpResponse response, String... fields){
		
		if (fields == null || fields.length == 0){
			return null;
		}
		HttpEntity entity = response.getEntity();
	    String pagina;
	    Map<String,String> values = new HashMap<String,String>(fields.length);
		try {
			pagina = EntityUtils.toString(entity);
			logger.info(pagina);
			Document document = Jsoup.parse(pagina);
			for (int i = 0; i < fields.length; i++) {
				Elements element = document.select("input[name="+fields[i] + "]");
				if (element == null || element.size() == 0){
					continue;
				}
				values.put(fields[i],element.first().val());
			}
			return values;
		} catch (Exception e) {
			logger.error(e,e);
			throw new RuntimeException(e);
		} finally{
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				logger.error(e,e);
			}
		}
	}
}
