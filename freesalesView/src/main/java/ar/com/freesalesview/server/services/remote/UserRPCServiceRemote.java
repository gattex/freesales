package ar.com.freesalesview.server.services.remote;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import ar.com.freesalesview.client.dtos.user.RoleDTO;
import ar.com.freesalesview.client.dtos.user.UserDTO;
import ar.com.freesalesview.client.services.UserRPCService;
import ar.com.freesalesview.server.entities.UserLogged;
import ar.com.freesalesview.server.utils.LogRemoteServiceServlet;

public class UserRPCServiceRemote extends LogRemoteServiceServlet implements UserRPCService{

	private static final long serialVersionUID = 1L;

	@Override
	public UserDTO getUserLogged() {
		UserLogged userLogged= (UserLogged)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Set<RoleDTO> roles = new HashSet<>();
		for (GrantedAuthority authority : userLogged.getAuthorities()) {
			roles.add(new RoleDTO(authority.getAuthority()));
		}
		return new UserDTO(userLogged.getUser().getName(), userLogged.getUser().getLastName(), roles);
	}
}
