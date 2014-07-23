package ar.com.freesalesview.client.factories;

import ar.com.freesalesview.client.activities.AvailabilityActivity;
import ar.com.freesalesview.client.activities.DashboardActivity;
import ar.com.freesalesview.client.activities.OpenCloseActivity;
import ar.com.freesalesview.client.activities.RateUpdateActivity;
import ar.com.freesalesview.client.activities.SettingsActivity;
import ar.com.freesalesview.client.places.AvailabilityPlace;
import ar.com.freesalesview.client.places.DashboardPlace;
import ar.com.freesalesview.client.places.OpenClosePlace;
import ar.com.freesalesview.client.places.RateUpdatePlace;
import ar.com.freesalesview.client.places.SettingsPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper implements ActivityMapper {
    
    public Activity getActivity(Place place) {
       	if (place instanceof DashboardPlace){
       		return new DashboardActivity();
        }else if (place instanceof RateUpdatePlace){
        	return new RateUpdateActivity();
        }else if (place instanceof AvailabilityPlace){
        	return new AvailabilityActivity();
        }else if (place instanceof OpenClosePlace){
        	return new OpenCloseActivity();
        }else if (place instanceof SettingsPlace){
        	return new SettingsActivity();
        }
        return null;
    }
}
