package ar.com.freesalesview.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class AvailabilityPlace  extends Place {
	
    public String getTokenValue() {
        return "Availability";
    }

    public static class Tokenizer implements PlaceTokenizer<AvailabilityPlace> {
        
        public String getToken(AvailabilityPlace place) {
            return place.getTokenValue();
        }

        public AvailabilityPlace getPlace(String token) {
            return new AvailabilityPlace();
        }
    }
}
