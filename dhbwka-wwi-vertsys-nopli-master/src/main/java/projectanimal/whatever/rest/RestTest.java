package projectanimal.whatever.rest;

/**
 *
 * @author simon
 */
import dhbwka.wwi.vertsys.javaee.projectanimal.common.ejb.UserBean;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import projectanimal.whatever.ejb.SpeziesBean;

/**
 *
 * @author Florian
 */
@Stateless
@Path("demo")
public class RestTest {

    @EJB
    private SpeziesBean speziesBean;

    @GET
    public String test() {

        return speziesBean.findAllSorted().toString();
    }

}
