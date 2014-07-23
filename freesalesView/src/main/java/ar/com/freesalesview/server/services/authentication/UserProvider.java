package ar.com.freesalesview.server.services.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ar.com.freesales.dao.UserDetailDao;
import ar.com.freesales.entity.user.UserDetail;
import ar.com.freesales.exception.UserNotFoundException;
import ar.com.freesalesview.server.entities.UserLogged;

public class UserProvider implements UserDetailsService {
	
	private UserDetailDao userDetailDao;

	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		
		UserDetail user = null;
		try {
			user = this.userDetailDao.getUserByUserName(userName);
		} catch (UserNotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
		return new UserLogged(user);
	}

	public void setUserDetailDao(UserDetailDao userDetailDao) {
		this.userDetailDao = userDetailDao;
	}
}
