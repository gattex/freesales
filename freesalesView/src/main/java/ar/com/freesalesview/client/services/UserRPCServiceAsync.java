package ar.com.freesalesview.client.services;

import ar.com.freesalesview.client.dtos.user.UserDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserRPCServiceAsync {

	void getUserLogged(AsyncCallback<UserDTO> callback);

}
