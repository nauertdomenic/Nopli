package projectanimal.whatever.rest;

import com.google.gson.Gson;
import java.util.List;
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
@Path("speziesliste/{id}")
public class SpeziesListRest {

    @EJB
    private SpeziesBean speziesBean;

    @GET
    public String doGet(@PathParam("id") long id) {
        // Anzuzeigende Tierarten suchen

        List<Spezies> spezies = this.speziesBean.findAll();
        Spezies test = this.speziesBean.findById(id);
        Gson gson = new Gson();
        String json = gson.toJson(test);
        System.out.println(json);
        return json;
    }
}
