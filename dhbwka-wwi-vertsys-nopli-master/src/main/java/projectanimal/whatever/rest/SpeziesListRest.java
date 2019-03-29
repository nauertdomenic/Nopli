package projectanimal.whatever.rest;

import com.google.gson.Gson;
import dhbwka.wwi.vertsys.javaee.projectanimal.common.ejb.UserBean;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import projectanimal.whatever.ejb.SpeziesBean;
import projectanimal.whatever.jpa.Spezies;

/**
 *
 * @author simon
 */
@Stateless
@Path("speziesliste")
public class SpeziesListRest {

    @EJB
    private SpeziesBean speziesBean;

    @EJB
    private UserBean userBean;

    @GET
    public String doGet(@HeaderParam("Authorization") String authorization) {
        // Anzuzeigende Spezies suchen
        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            final String[] values = credentials.split(":", 2);

            System.out.println("");
            System.out.println(values[0] + " bla " + values[1]);

        }

        System.out.println("");

        // System.out.println(values[0] + " " + values[1]);
        List<Spezies> spezies = this.speziesBean.findAll();
        Gson gson = new Gson();
        String json = gson.toJson(spezies);
        return json;
    }

}
