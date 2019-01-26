package controllers;

import ejb.SessionBean;
import entities.User;
import ejb.UserBean;
import entities.UserSession;
import responses.LoginResponse;
import responses.LogoutResponse;
import responses.RegisterResponse;
import utils.AuthenticationUtils;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
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

    @Path("/register/{name}/{password}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@PathParam(value = "name") String name,
                             @PathParam(value = "password") String password,
                             @Context HttpServletRequest request) {

        RegisterResponse response = new RegisterResponse();
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
                UserSession session = new UserSession(request.getRequestedSessionId(), name);
                sessionBean.addSession(session);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return Response.status(200).
                    header("Access-Control-Allow-Origin", "*").
                    entity(response).
                    build();
        }

        return Response.ok().
                header("Access-Control-Allow-Origin", "*").
                entity(response).
                build();
    }

    @Path("/test")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response test() {
        return Response.ok().
                header("Access-Control-Allow-Origin", "*").
                entity("test!!!").
                build();
    }

    @Path("/login/{name}/{password}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@PathParam(value = "name") String name,
                          @PathParam(value = "password") String password,
                          @Context HttpServletRequest request){

        LoginResponse response = new LoginResponse();
        try {
            User user = new User(name, AuthenticationUtils.encode(password));
            if(userBean.checkPassword(user)) {
                response.setSuccess(true);
            }
            else{
                response.setSuccess(false);
                response.setMessage("Неверный логин или пароль!");
                UserSession session = new UserSession(request.getRequestedSessionId(), name);
                sessionBean.addSession(session);
                return  Response.ok().
                        header("Access-Control-Allow-Origin", "*").
                        entity(response).
                        build();
            }
        }
        catch (Exception e){
            response.setSuccess(false);

            response.setMessage(e.getMessage());
            return  Response.status(200).
                    header("Access-Control-Allow-Origin", "*").
                    entity(response).
                    build();
        }

        return Response.ok().
                header("Access-Control-Allow-Origin", "*").
                entity(response).
                build();
    }

    @Path("/logout")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(
                          @Context HttpServletRequest request){

        UserSession session = sessionBean.findUserBySessionId(request.getRequestedSessionId());
        sessionBean.removeSession(session);

        LogoutResponse response = new LogoutResponse();
        response.setSuccess(true);
        response.setMessage("Successfully logged out.");
        return Response.ok().
                header("Access-Control-Allow-Origin", "*").
                entity(response).
                build();
    }
}
