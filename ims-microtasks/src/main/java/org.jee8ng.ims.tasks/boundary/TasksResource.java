package org.jee8ng.ims.tasks.boundary;


import org.jee8ng.ims.tasks.entity.Ticket;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("tasks")
public class TasksResource {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response get() {
        return Response.ok(new Ticket(1, "Fix slow loading")).build();
    }
}
