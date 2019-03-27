package controllers;

import ejb.PointBean;
import ejb.SessionBean;
import ejb.UserBean;
import entities.Point;
import entities.User;
import entities.UserSession;
import responses.PointResponse;

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
import java.util.List;

@Path("/points")
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
        PointResponse response = new PointResponse();
        if (sid != null) {
            String name = sessionBean.findUserBySessionId(sid).getName();
            try {
                response.setPoints(getPointsByLogin(name));
            }
            catch (Exception e){
                System.err.println("Что-то пошло не так на этапе получения точек по логину" + e);
                return Response.status(500).
                        build();
            }
        } else {
            response.setSuccess(false);
            return Response.
                    status(200).
                    entity(response).
                    build();
        }
        response.setSuccess(true);
        return Response.
                status(200).
                entity(response).
                build();
    }

    @Path("/add")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewPoint(@QueryParam(value = "x") double x,
                                @QueryParam(value = "y") double y,
                                @QueryParam(value = "r") double r){
        HttpSession session = request.getSession(false);
        String sid = session.getId();
        PointResponse response = new PointResponse();
        try {
            UserSession Usession = sessionBean.findUserBySessionId(sid);
            String userName = Usession.getName();
            User user = userBean.findUserById(userName);
            Point point = new Point(x, y, r, user);
            pointBean.addPoint(point);
            response.setPoints(getPointsByLogin(userName));
        }
        catch (Exception e){
            System.err.println("Что-то пошло не так на этапе добавления новой точки" + e);
            return Response.status(500).
                    build();
        }
        response.setSuccess(true);
        return Response.
                status(200).
                entity(response).
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
        return pointBean.findPointsByUserId(login);
    }
}