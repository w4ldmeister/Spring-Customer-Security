package de.computerlyirk.scs;

import java.util.Map;
import java.util.HashMap;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.computerlyrik.scs.UserDetailsSCS;
import de.computerlyrik.scs.service.UserDetailsServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/noSalt/applicationContext.xml" })
public class UserDetailsSCSFunctional {
	
	private static final Logger LOG = Logger.getLogger(UserDetailsSCSFunctional.class);

	@Qualifier("userDetailsService")
    @Autowired 
	private UserDetailsServiceImpl uds;
    
	/**
	 * Try out some preconfigured password/hash combinations
	 */
	@Test
	public void passwordHashing() {
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

	/**
	 * Try out some preconfigured password/hash combinations WITH SALT
	 */
	@Test
	public void saltedPasswordHashing() {
		uds.setSalt("myTestSalt");
		Map<String,String> passwordhashes = new HashMap<String,String>();
		passwordhashes.put("admin", "de6d77d42b352da7c1edd9400529c4602f33220bf19d41759220137ea640e4d1");
		UserDetailsSCS u = new UserDetailsSCS();
		for(String password : passwordhashes.keySet()) {
			u.setPassword(password);
			Assert.assertTrue(
					"Password hashes should be equal!", 
					u.getPassword()
						.equals(passwordhashes.get(password)));
		}
	}	

}
