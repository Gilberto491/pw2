package org.pw2;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/exercicio")
public class Exercicio {

    @POST
    @Path("/km")
    @Produces(MediaType.TEXT_PLAIN)
    public String km(@FormParam("km") int km) {
        double response = km * 0.621371;
        return "O resultado em km é igual a: " + response;
    }

    @GET
    @Path("/nos/{nos}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response nos(@PathParam("nos") int nos) {
        double response = nos * 1.852;
        return Response.ok(response).build();
    }
}