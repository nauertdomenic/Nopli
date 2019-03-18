package projectanimal.whatever.rest;

import com.google.gson.Gson;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import projectanimal.whatever.ejb.SpeziesBean;
import projectanimal.whatever.ejb.TierartBean;
import projectanimal.whatever.jpa.Spezies;
import projectanimal.whatever.jpa.Tierart;

/**
 *
 * @author simon
 */
@Stateless
@Path("tierliste")
public class TierartListRest {

    @EJB
    private SpeziesBean speziesBean;

    @EJB
    private TierartBean tierartBean;

    @GET
    public String doGet() {
        // Anzuzeigende Aufgaben suchen
        Spezies spezies = null;

        List<Tierart> tierarten = this.tierartBean.findAll();
        Gson gson = new Gson();
        String json = gson.toJson(tierarten);
        System.out.println(json);
        return json;
    }
}
