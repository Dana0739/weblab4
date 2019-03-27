package responses;

import entities.Point;
import java.util.List;

public class PointResponse {
    private boolean success;
    private List<Point> points;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }


}
