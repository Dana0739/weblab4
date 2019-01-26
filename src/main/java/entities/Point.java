package entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity (name = "point")
@NamedQueries({
        @NamedQuery(name="findSpotsByUserId", query="SELECT p FROM point p WHERE p.userName = :userName")
})
public class Point implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pointId")
    Integer pointId;

    @Column(name = "x", nullable = false)
    double x;

    @Column(name = "y", nullable = false)
    double y;

    @Column(name = "r", nullable = false)
    double r;

    @Column(name = "inArea", nullable = false)
    boolean inArea;

    @Column(name = "userName", nullable = false, length=30)
    String userName;

    public Point() {}

    public Point(double x, double y, double r, User user) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.userName = user.getName();
        this.inArea = checkArea();
    }

    private boolean checkArea () {
        if ((x >= 0) && (y <= 0) && (y >= x*2 - r)) {
            return true;
        } else {
            if ((x <= 0) && (y <= 0) && ((x * x + y * y) <= (r * r))) {
                return true;
            } else {
                if ((x >= 0) && (y >= 0) && (x <= r/2) && (y <= r)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean isInArea() {
        return inArea;
    }
}

