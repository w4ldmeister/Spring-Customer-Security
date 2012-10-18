package de.computerlyrik.scs.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.MailSender;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import de.computerlyrik.scs.service.UserDetailsServiceImpl;
import de.computerlyrik.scs.exception.EncodingException;
import org.springframework.security.authentication.encoding.PasswordEncoder;

@RooJavaBean
public class PasswordEncoderImpl implements PasswordEncoder {

	private static final Logger log = Logger.getLogger(PasswordEncoderImpl.class);

	private String salt;
	private String hash;
	
	/**
	 * Throws salt away, because it is configured externally
	 */
	public String encodePassword(String rawPass, Object nothing)
			throws DataAccessException  {
		log.trace("using hash value "+hash);
		log.trace("using salt value "+salt);
		try {
			MessageDigest md = MessageDigest.getInstance((hash==null?"sha-256":hash).toUpperCase());
			byte[] hash = md.digest((rawPass
					+((salt==null)?"":"{" + salt + "}")).getBytes());
			StringBuffer sb = new StringBuffer();
			for (byte b : hash) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (Exception e) {
			log.error("error encoding password",e);
			throw new EncodingException("error encoding password");
		}
	}

	/**
	 * Throw salt away - again
	 */
	public boolean isPasswordValid(String encPass, String rawPass, Object nothing)
			throws DataAccessException {
		return encodePassword(rawPass,null).equals(encPass);
	}

}
