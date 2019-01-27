package controllers;

import ejb.PointBean;
import ejb.SessionBean;
import ejb.UserBean;
import entities.Point;
import entities.User;
import entities.UserSession;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/points")
@RolesAllowed("users")
public class PointController {
    @EJB
    private PointBean pointBean;
    @EJB
    private UserBean userBean;
    @EJB
    private SessionBean sessionBean;
    @Context
    private HttpServletRequest request;

    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll(){
        HttpSession session = request.getSession(false);
        String sid = session.getId();
        List<Point> points;
        String sessionId = sid;
        String name = sessionBean.findUserBySessionId(sessionId).getName();
        try {
            points = getPointsByLogin(name);
        }
        catch (Exception e){
            System.err.println("Что-то пошло не так на этапе получения точек по логину" + e);

            return Response.status(500).
                    header("Access-Control-Allow-Origin", "*").
                    build();
        }

        return Response.
                status(200).
                header("Access-Control-Allow-Origin", "*").
                entity(convertPointsInJSON(points)).
                build();
    }

    @Path("/add")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewPoint(@QueryParam(value = "x") int x,
                                @QueryParam(value = "y") int y,
                                @QueryParam(value = "r") int r){
        HttpSession session = request.getSession(false);
        String sid = session.getId();
        List<Point> points;
        try {

            String sessionId = sid;
            UserSession Usession = sessionBean.findUserBySessionId(sessionId);
            String userName = Usession.getName();
            User user = userBean.findUserById(userName);
            Point point = new Point(x, y, r, user);
            pointBean.addPoint(point);

            points = getPointsByLogin(userName);
        }
        catch (Exception e){
            System.err.println("Что-то пошло не так на этапе добавления новой точки" + e);

            return Response.status(500).
                    header("Access-Control-Allow-Origin", "*").
                    build();
        }
        return Response.
                status(200).
                header("Access-Control-Allow-Origin", "*").
                entity(convertPointsInJSON(points)).
                build();
    }

    private JsonArray convertPointsInJSON(List<Point> points){
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (Point p : points) {
            jsonArrayBuilder.add(
                    Json.createObjectBuilder().
                            add("x", p.getX()).
                            add("y", p.getY()).
                            add("r", p.getR()).
                            add("isHit", p.isInArea())
            );
        }
        return  jsonArrayBuilder.build();
    }

    private List<Point> getPointsByLogin(String login){
        return (login == null ? new ArrayList<Point>() : pointBean.findPointsByUserId(login));
    }
}