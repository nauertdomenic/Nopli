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
import java.util.*;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.*;
 
@ApplicationPath("RestAPI")
public class ApplicationConfig extends Application {
 
@Override
public Set<Class<?>> getClasses() {
Set<Class<?>> resources = new HashSet<>();
 
resources.add(projectanimal.whatever.rest.RestTest.class);
resources.add(projectanimal.whatever.rest.TierartListRest.class);
 
return resources;
}
 
}
