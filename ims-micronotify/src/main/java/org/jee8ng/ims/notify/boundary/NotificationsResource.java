package org.jee8ng.ims.notify.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("notifications")
public class NotificationsResource {

    @GET
    @Produces
    public Response get() {
        return Response.ok("notification work").build();
    }
}
