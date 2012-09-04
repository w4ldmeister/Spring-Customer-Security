package de.computerlyrik.scs.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import de.computerlyrik.scs.UserDetailsSCS;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
	
    private static final Logger log = Logger.getLogger(UserDetailsServiceImpl.class);
    
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException 
	{
		UserDetailsSCS user;
		try {
			TypedQuery<UserDetailsSCS> q = UserDetailsSCS.findUserDetailsSCSsByUsernameEquals(username);
			if (q.getMaxResults() == 0) {
				throw new UsernameNotFoundException("Username "+ username + "not found");
			}
			user = q.getSingleResult();
		}
		catch (IllegalArgumentException e) {
			throw new RecoverableDataAccessException("Error loading User "+username,e);
		}
		log.debug("Loaded "+user.getUsername());
		log.trace(user);
		return (UserDetails) user;
	}
}
