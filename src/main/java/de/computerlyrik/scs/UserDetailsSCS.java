package de.computerlyrik.scs;

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
import org.springframework.mail.MailSender;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import de.computerlyrik.scs.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.encoding.PasswordEncoder;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findUserDetailsSCSsByUsernameEquals" })
public class UserDetailsSCS implements UserDetails {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(UserDetailsSCS.class);

    @Autowired 
	@Qualifier("userDetailsService")
	@Transient
	private UserDetailsServiceImpl uds;
    
    @Autowired 
	@Qualifier("passwordEncoder")
	@Transient
	private PasswordEncoder pe;  
    
	@NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = false;


    public static UserDetailsSCS findMyUserByUsername(String username) {
        if (username == null) return null;
        log.debug("call findMyUserByUsername");
        TypedQuery<UserDetailsSCS> q = entityManager().createQuery("SELECT o FROM UserDetailsSCS AS o WHERE o.username = :username", UserDetailsSCS.class);

        return entityManager().createQuery("SELECT o FROM UserDetailsSCS o WHERE o.username = '" + username + "'", UserDetailsSCS.class).getSingleResult();
    }
    
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> glist = new ArrayList<GrantedAuthority>();
        log.debug("call getAuthorities");
        String roleString = "ROLE_" + this.getClass().getSimpleName().toUpperCase();
        log.debug("Adding " + roleString + " for User " + this.getUsername());
        glist.add(new GrantedAuthorityImpl(roleString));
        return glist;
    }

    public void setPassword(String password)  {
    	try {
    		this.password = pe.encodePassword(password, null);
    	}
    	catch(Exception e) {
    		log.error("Failed setting password of "+this.username);
    		log.info("Login of "+this.username+" disabled");
    		this.password = "";
    	}
        log.debug("Password of user " + username + " set to " + this.getPassword() +" (hash)");
    }
}
