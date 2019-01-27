package ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;

import entities.Point;

@Stateless
public class PointBean {

    public Object addObject(Object object) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lab4");
        EntityManager em = entityManagerFactory.createEntityManager();
        em.persist(object);
        em.close();
        return object;
    }

    public Point addPoint(Point point) {
        return (Point) addObject(point);
    }

    public List<Point> findPointsByUserId(String id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lab4");
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Point> query = em.createNamedQuery("findSpotsByUserId", Point.class);
        query.setParameter("userName", id);
        List<Point> points = new ArrayList<Point>();
        try {
            points = query.getResultList();
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
        }
        em.close();
        return points;
    }
}