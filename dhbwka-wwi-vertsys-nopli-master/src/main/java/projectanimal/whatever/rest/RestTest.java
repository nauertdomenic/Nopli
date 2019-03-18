/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
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
    public String test(){
        
        
        return speziesBean.findAllSorted().toString();
    }
   
}
