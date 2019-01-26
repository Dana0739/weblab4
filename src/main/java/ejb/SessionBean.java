package ejb;

import javax.ejb.Stateless;
import javax.persistence.*;

import entities.UserSession;

@Stateless
public class SessionBean {

    public UserSession addSession(UserSession session) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lab4");
        EntityManager em = entityManagerFactory.createEntityManager();
        em.persist(session);
        em.close();
        return session;
    }

    public void removeSession(UserSession session) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lab4");
        EntityManager em = entityManagerFactory.createEntityManager();
        em.remove(session);
        em.close();
    }

    public UserSession findUserBySessionId(String sessionId) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lab4");
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<UserSession> query = em.createNamedQuery("findUserBySessionId", UserSession.class);
        query.setParameter("sessionId", sessionId);
        UserSession session = null;
        try {
            session = query.getSingleResult();
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
        }
        em.close();
        return session;
    }
}