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
        HttpSession session = request.getSession(true);
        String sid = session.getId();
        RegisterResponse response = new RegisterResponse();
        try {
            User user = userBean.findUserById(name);
            if (user != null) {
                response.setSuccess(false);
                response.setMessage("Пользователь с таким логином уже существует!");
            } else {
                request.login(name, password);
                user = new User(name, AuthenticationUtils.encode(password));
                userBean.createUser(user);
                response.setSuccess(true);
                response.setMessage("Регистрация успешно выполнена!");
                UserSession Usession = new UserSession(sid, name);
                sessionBean.addSession(Usession);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return Response.status(200).
                    header("Access-Control-Allow-Origin", "*").
                    entity(response).
                    build();
        }

        return Response.status(200).
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

    @Path("/login")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam(value = "username") String name,
                          @QueryParam(value = "password") String password) {
        HttpSession session = request.getSession(true);
        String sid = session.getId();
        LoginResponse response = new LoginResponse();
        try {
            User user = new User(name, AuthenticationUtils.encode(password));
            if(userBean.checkPassword(user)) {
                request.login(name, password);
                response.setSuccess(true);
                response.setMessage("Успешная авторизация!\n" + request.isUserInRole("users"));
                UserSession Usession = new UserSession(sid, name);
                sessionBean.addSession(Usession);
            }
            else{
                response.setSuccess(false);
                response.setMessage("Неверный логин или пароль!");
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

        return Response.status(200).
                header("Access-Control-Allow-Origin", "*").
                entity(response).
                build();
    }

    @Path("/logout")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(){
        HttpSession session = request.getSession();
        String sid = session.getId();
        UserSession Usession = sessionBean.findUserBySessionId(sid);
        sessionBean.removeSession(Usession);
        try {request.logout(); }
            catch (Exception e) {
            //smth
        }
        request.getSession(false).invalidate();
        LogoutResponse response = new LogoutResponse();
        response.setSuccess(true);
        response.setMessage("Successfully logged out.");
        return Response.status(200).
                header("Access-Control-Allow-Origin", "*").
                entity(response).
                build();
    }
}
