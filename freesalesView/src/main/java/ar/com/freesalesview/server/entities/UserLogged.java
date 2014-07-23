package ar.com.freesalesview.server.entities;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ar.com.freesales.entity.user.UserDetail;
import ar.com.freesales.entity.user.UserRole;

public class UserLogged implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private UserDetail user;
	private Collection<GrantedAuthority>  authorities;
	
	public UserLogged(UserDetail user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (authorities == null){
			authorities = new ArrayList<GrantedAuthority>();
			for (UserRole role : user.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(role.getRole()));
			}
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getUserPass();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public UserDetail getUser() {
		return user;
	}
}
