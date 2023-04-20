package ifrs.pw2;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class TestResource {

    @ConfigProperty(name = "Ifrs.pw2", defaultValue = "")
    String disciplina;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Essa disciplina é de " + disciplina;
    }
}