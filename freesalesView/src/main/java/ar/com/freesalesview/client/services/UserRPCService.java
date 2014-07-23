package ar.com.freesalesview.client.services;

import ar.com.freesalesview.client.dtos.user.UserDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("userRPCService")
public interface UserRPCService  extends RemoteService{

	UserDTO getUserLogged();
	
}
