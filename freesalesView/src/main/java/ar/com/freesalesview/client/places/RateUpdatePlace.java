package ar.com.freesalesview.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class RateUpdatePlace  extends Place {
	
    public String getTokenValue() {
        return "Rate";
    }

    public static class Tokenizer implements PlaceTokenizer<RateUpdatePlace> {
        
        public String getToken(RateUpdatePlace place) {
            return place.getTokenValue();
        }

        public RateUpdatePlace getPlace(String token) {
            return new RateUpdatePlace();
        }
    }
}
