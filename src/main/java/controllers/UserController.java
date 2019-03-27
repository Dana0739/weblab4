package controllers;

import ejb.SessionBean;
import entities.User;
import ejb.UserBean;
import entities.UserSession;
import responses.UserResponse;
import utils.AuthenticationUtils;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserController {
    @EJB
    private UserBean userBean;
    @EJB
    private SessionBean sessionBean;
    @Context
    private HttpServletRequest request;

    @Path("/register")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@QueryParam(value = "username") String name,
                             @QueryParam(value = "password") String password) {
        HttpSession session = request.getSession(true); //if there's no session id, it'll be created
        String sid = session.getId();
        UserResponse response = new UserResponse();
        try {
            User user = userBean.findUserById(name);
            if (user != null) {
                response.setSuccess(false);
                response.setMessage("Пользователь с таким логином уже существует!");
            } else {
                user = new User(name, AuthenticationUtils.encode(password));
                userBean.createUser(user);
                response.setSuccess(true);
                response.setMessage("Регистрация успешно выполнена!");
                UserSession Usession = new UserSession(sid, name);
                sessionBean.addSession(Usession);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            e.printStackTrace();
            response.setMessage(e.getMessage());
            return Response.status(200).
                    entity(response).
                    build();
        }

        return Response.status(200).
                entity(response).
                build();
    }

    @Path("/test")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response test() {
        return Response.ok().
                entity("test!!!").
                build();
    }

    @Path("/login")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam(value = "username") String name,
                          @QueryParam(value = "password") String password) {
        HttpSession session = request.getSession(true); //if there's no session id, it'll be created
        String sid = session.getId();
        System.out.println("login sid #### " + sid);
        UserResponse response = new UserResponse();
        try {
            User user = new User(name, AuthenticationUtils.encode(password));
            if (userBean.checkPassword(user)) {
                response.setSuccess(true);
                response.setMessage("Успешная авторизация!");
                UserSession Usession = new UserSession(sid, name);
                sessionBean.addSession(Usession);
            } else {
                response.setSuccess(false);
                response.setMessage("Неверный логин или пароль!");
                return Response.ok().
                        entity(response).
                        build();
            }
        } catch (Exception e) {
            response.setSuccess(false);

            response.setMessage(e.getMessage());
            return Response.status(200)
                    .entity(response)
                    .build();
        }

        return Response.status(200)
                .entity(response)
                .build();
    }

    @Path("/isAuthorised")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response isAuthorised() {
        HttpSession session = request.getSession(true); //if there's no session id, it'll be created
        String sid = session.getId();
        System.out.println("login sid #### " + sid);
        UserResponse response = new UserResponse();
        try {
            UserSession userSession = sessionBean.findUserBySessionId(sid);
            if (userSession != null) {
                response.setSuccess(true);
                response.setMessage("вы асторизованы");
            } else {
                response.setSuccess(false);
                response.setMessage("вы не авторизованы");
                return Response.ok().
                        entity(response).
                        build();
            }
        } catch (Exception e) {
            response.setSuccess(false);

            response.setMessage(e.getMessage());
            return Response.status(200)
                    .entity(response)
                    .build();
        }

        return Response.status(200)
                .entity(response)
                .build();
    }

    @Path("/logout")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout() {
        HttpSession session = request.getSession();
        String sid = session.getId();
        System.out.println("logout sid #### " + sid);
        sessionBean.removeSession(sid);
        request.getSession(false).invalidate();
        UserResponse response = new UserResponse();
        response.setSuccess(true);
        response.setMessage("Successfully logged out.");
        return Response.status(200)
                .entity(response)
                .build();
    }
}
