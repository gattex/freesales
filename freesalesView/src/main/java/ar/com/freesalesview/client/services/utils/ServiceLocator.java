package ar.com.freesalesview.client.services.utils;

import ar.com.freesalesview.client.services.HotelRPCService;
import ar.com.freesalesview.client.services.HotelRPCServiceAsync;
import ar.com.freesalesview.client.services.NotificationRPCService;
import ar.com.freesalesview.client.services.NotificationRPCServiceAsync;
import ar.com.freesalesview.client.services.UserRPCService;
import ar.com.freesalesview.client.services.UserRPCServiceAsync;

import com.google.gwt.core.client.GWT;

public class ServiceLocator {
	
	private final static UserRPCServiceAsync userRpcService= (UserRPCServiceAsync) GWT.create(UserRPCService.class);
	
	private final static HotelRPCServiceAsync hotelRpcService = (HotelRPCServiceAsync) GWT.create(HotelRPCService.class);
	
	private final static NotificationRPCServiceAsync notificationRpcService = (NotificationRPCServiceAsync) GWT.create(NotificationRPCService.class);

	public static UserRPCServiceAsync getUserRpcService() {
		return userRpcService;
	}
	
	public static HotelRPCServiceAsync getHotelrpcservice() {
		return hotelRpcService;
	}
	
	public static NotificationRPCServiceAsync getNotificationrpcservice() {
		return notificationRpcService;
	}
}
