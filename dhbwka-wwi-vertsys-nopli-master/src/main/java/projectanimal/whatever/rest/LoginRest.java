package projectanimal.whatever.rest;

import javax.ejb.Stateless;
import javax.ws.rs.*;

/**
 *
 * @author phoenix
 *
 * Rest für User Login
 */
@Stateless
@Path("loginRest")
public class LoginRest {

//    @EJB
//    private UserBean userbean;
//
//    @GET
//    public String doGet(@PathParam("un") String username, @PathParam("pw") String password) {
//        // Login des Users
//        List<User> userliste = this.userbean.findUser(username);
//        String msg = "";
//
//        if (userliste.size() > 0) {
//            if (userliste.get(0).checkPassword(password)) {
//                msg = "1";
//            } else {
//                msg = "2";
//            }
//        } else {
//            msg = "0";
//        }
//        Gson gson = new Gson();
//        String json = gson.toJson(msg);
//        return json;
//    }
    @POST
    public void doPost(String inputJsonObj) throws Exception {
        System.out.print(inputJsonObj);
    }

    public static class SignUpRequest {

        public String username;

        public String password;
    }

    @POST
    @Path("login")
    public StatusResponse doPost(HttpServletRequest request) {
        // Login des Users

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        StatusResponse response = new StatusResponse();
        response.status = "OK";
        response.message = "Benutzer wurde angelegt";
        return response;

    }
}
