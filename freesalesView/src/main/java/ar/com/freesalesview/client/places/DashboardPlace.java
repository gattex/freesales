package ar.com.freesalesview.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DashboardPlace extends Place {

    public String getTokenValue() {
        return null;
    }

    public static class Tokenizer implements PlaceTokenizer<DashboardPlace> {
        
        public String getToken(DashboardPlace place) {
            return place.getTokenValue();
        }

        public DashboardPlace getPlace(String token) {
            return new DashboardPlace();
        }
    }

}
