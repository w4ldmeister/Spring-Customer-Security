package de.computerlyrik.scs.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import de.computerlyrik.scs.UserDetailsSCS;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
	
    private static final Logger log = Logger.getLogger(UserDetailsServiceImpl.class);

	private String salt;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException 
	{
		UserDetailsSCS user = UserDetailsSCS.findMyUserByUsername(username);
		log.debug("Got User "+user);

		/*
		 * TODO: if username not found:
		 *       throw new UsernameNotFoundException(username + "not found");

		 */
		return (UserDetails) user;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public String calcPassword(String password) throws NoSuchAlgorithmException {
	
	    MessageDigest md = MessageDigest.getInstance("SHA-256");
	    String salt = "mysecretsalt";
	    byte[] hash = md.digest((password + "{" + salt + "}").getBytes());
	    StringBuffer sb = new StringBuffer();
	    for (byte b : hash) {
	        sb.append(String.format("%02x", b));
	    }
	    return sb.toString();
	}
}
