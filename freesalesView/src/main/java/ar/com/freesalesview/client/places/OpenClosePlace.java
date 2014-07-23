package ar.com.freesalesview.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class OpenClosePlace  extends Place {
	
    public String getTokenValue() {
        return "openClose";
    }

    public static class Tokenizer implements PlaceTokenizer<OpenClosePlace> {
        
        public String getToken(OpenClosePlace place) {
            return place.getTokenValue();
        }

        public OpenClosePlace getPlace(String token) {
            return new OpenClosePlace();
        }
    }
}
