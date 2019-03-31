package ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;

import entities.UserSession;

@Stateless
public class SessionBean {

    @PersistenceContext
    EntityManager em;

    @EJB
    private PointBean pointBean;

    public UserSession addSession(UserSession session) {
        return (UserSession) pointBean.addObject(session);
    }

    public void removeSession(String sessionId) {
        UserSession u = findUserBySessionId(sessionId);
        if (u != null) {
            UserSession newU = em.merge(u);
            em.remove(newU);
        } else {
            System.err.println("######NOT FOUND#########" + " " + sessionId);
        }
    }

    public UserSession findUserBySessionId(String sessionId) {
        TypedQuery<UserSession> query = em.createNamedQuery("findUserBySessionId", UserSession.class);
        query.setParameter("sessionId", sessionId);
        UserSession session = null;
        try {
            session = query.getSingleResult();
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
        }
        return session;
    }
}