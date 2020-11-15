package org.jee8ng.ims.users.boundary;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.jee8ng.ims.users.entity.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("users")
public class UsersResource {

    @Inject
    private UsersService service;

    @GET
    @ApiOperation(value = "Get all users", notes = "This can only be done by the logged in user.")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "User not found") })
    public Response getAll() {
        return Response.ok(service.getAll()).build();
    }

  /*  @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        return Response.ok("user works").build();
    }
   */

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long id) {
        System.out.println("get id: " + id);
        return Response.ok(service.getUser(id)).build();
    }

    @POST
    public Response add(User newUser, @Context UriInfo uriInfo) {
        Long newId = service.add(newUser);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(newId))
                .build();

        return Response.created(location).build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, User existingUser) {
        System.out.println("update id: " + id + ",existingUser " + existingUser.getName());
        service.updateIfExists(id, existingUser);

        return Response.ok(existingUser).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        System.out.println("delete id: " + id);
        service.delete(id);

        return Response.ok().build();
    }

}
