package projectanimal.whatever.rest;

import com.google.gson.Gson;
import dhbwka.wwi.vertsys.javaee.projectanimal.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.projectanimal.common.jpa.User;
import java.util.List;
import javax.ejb.EJB;
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

    @EJB
    private UserBean userbean;

    @POST
    public String doPost(String inputJsonObj) throws Exception {
        String[] test = inputJsonObj.split("&password=");

        String username = test[0].replace("username=", "");
        String password = test[1];

        //Login des Users
        List<User> userliste = this.userbean.findUser(username);
        String msg = "";

        if (userliste.size() > 0) {
            if (userliste.get(0).checkPassword(password)) {
                msg = "1";
            } else {
                msg = "2";
            }
        } else {
            msg = "0";
        }

        Gson gson = new Gson();
        String json = gson.toJson(msg);
        return json;

    }
}
