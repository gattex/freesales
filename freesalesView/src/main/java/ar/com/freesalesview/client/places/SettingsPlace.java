package ar.com.freesalesview.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SettingsPlace  extends Place {
	
    public String getTokenValue() {
        return "settings";
    }

    public static class Tokenizer implements PlaceTokenizer<SettingsPlace> {
        
        public String getToken(SettingsPlace place) {
            return place.getTokenValue();
        }

        public SettingsPlace getPlace(String token) {
            return new SettingsPlace();
        }
    }
}
