package de.computerlyirk.scs;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.computerlyrik.scs.UserDetailsSCS;
import de.computerlyrik.scs.service.PasswordEncoderImpl;
import de.computerlyrik.scs.service.UserDetailsServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/Default/applicationContext.xml" })
public class UserDetailsSCSTest {
	
	private static final Logger LOG = Logger.getLogger(UserDetailsSCSTest.class);

	@Qualifier("userDetailsService")
    @Autowired 
	private UserDetailsServiceImpl uds;
	
    @Autowired 
	@Qualifier("passwordEncoder")
	private PasswordEncoderImpl pe;  
    
    @Autowired 
	@Qualifier("hashingPasswordEncoder")
	private PasswordEncoderImpl pe2;     
	
    @Autowired 
	@Qualifier("saltedPasswordEncoder")
	private PasswordEncoderImpl pe3; 
    /**
	 * Try out some preconfigured password/hash combinations
	 */
	@Test
	public void userDetailsSCSDefaultTest() {
		Map<String,String> passwordhashes = new HashMap<String,String>();
		passwordhashes.put("admin", "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");		
		UserDetailsSCS u = new UserDetailsSCS();
		for(String password : passwordhashes.keySet()) {
			u.setPassword(password);
			Assert.assertTrue(
					"Password hashes should be equal!", 
					u.getPassword()
						.equals(passwordhashes.get(password)));
		}
	}	
	
	@Test
	public void customHashingPasswordGeneration() {
		Map<String,String> passwordhashes = new HashMap<String,String>();
		passwordhashes.put("admin", "d033e22ae348aeb5660fc2140aec35850c4da997");		
		for(String password : passwordhashes.keySet()) {			
			Assert.assertTrue(
					pe2.encodePassword(password,null)
						.equals(passwordhashes.get(password)));
		}
	}
	
	@Test
	public void customHashingSaltedPasswordGeneration() {
		Map<String,String> passwordhashes = new HashMap<String,String>();
		passwordhashes.put("admin", "f7011b38f770ec641e7629efe56c2dd1d181c70e");		
		for(String password : passwordhashes.keySet()) {			
			Assert.assertTrue(
					pe3.encodePassword(password,null)
						.equals(passwordhashes.get(password)));
		}
	}	
}
