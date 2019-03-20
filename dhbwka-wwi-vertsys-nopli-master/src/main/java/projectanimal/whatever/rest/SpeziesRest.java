package projectanimal.whatever.rest;

import com.google.gson.Gson;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import projectanimal.whatever.ejb.SpeziesBean;
import projectanimal.whatever.jpa.Spezies;

/**
 *
 * @author simon
 */
@Stateless
@Path("speziesRest/{id}")
public class SpeziesRest {

    @EJB
    private SpeziesBean speziesBean;

    @GET
    public String doGet(@PathParam("id") long id) {
        // Anzuzeigende Spezies suchen
        Spezies spezies = this.speziesBean.findById(id);
        Gson gson = new Gson();
        String json = gson.toJson(spezies);
        return json;
    }
}
