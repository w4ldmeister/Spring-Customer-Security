// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package de.computerlyrik.scs;

import de.computerlyrik.scs.UserDetailsSCS;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect UserDetailsSCS_Roo_Finder {
    
    public static TypedQuery<UserDetailsSCS> UserDetailsSCS.findUserDetailsSCSsByUsernameEquals(String username) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = UserDetailsSCS.entityManager();
        TypedQuery<UserDetailsSCS> q = em.createQuery("SELECT o FROM UserDetailsSCS AS o WHERE o.username = :username", UserDetailsSCS.class);
        q.setParameter("username", username);
        return q;
    }
    
}
