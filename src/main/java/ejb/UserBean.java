package ejb;

import entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class UserBean {
    @PersistenceContext
    EntityManager em;

    public User createUser(User user) {
        try {
            user.setPassword(user.getPassword());
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        em.persist(user);
        return user;
    }

    public User findUserById(String id) {
        TypedQuery<User> query = em.createNamedQuery("findUserById", User.class);
        query.setParameter("name", id);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
        }
        return user;
    }

    public boolean checkPassword(User user) {
        User existingUser = findUserById(user.getName());
        return (existingUser != null &&
                user.getPassword().equals(existingUser.getPassword()));
    }
}
