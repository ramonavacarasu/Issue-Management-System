package ims.users.boundary;


import ims.security.boundary.TokenIssuer;
import ims.users.entity.Credential;
import ims.users.entity.User;
import io.swagger.annotations.*;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;

@Path("users")
@Api(value = "users")
public class UsersResource {

    @DefaultValue("v1")
    @HeaderParam("X-apiversion")
    private String apiVersion;

    @Inject
    private UsersService service;

    @Inject
    private TokenIssuer issuer;

    @GET
    @ApiOperation(value = "Get all users")
    public Response getAll(@QueryParam("names") String names) {
        if (names != null) {
            return Response.ok(service.getNames()).build();
        }
        return Response.ok(service.getAll()).build();
    }

    @GET
    @Path("{id}")
    @ApiOperation(value = "Get user by user id", response = User.class)
    @ApiResponses({@ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "User not found")})
    public Response get(@ApiParam(value = "ID of user that needs to be fetched", required = true) @PathParam("id") Long id) {
        System.out.println("request for " + id);
        final Optional<User> userFound = service.get(id);
        if (userFound.isPresent()) {
            return Response.ok(userFound.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @ApiOperation(value = "Create user", notes = "This can only be done by the logged in user.")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid user input"),
            @ApiResponse(code = 201, message = "User added")})
    public Response add(@ApiParam(value = "User that needs to be added", required = true) User newUser, @Context UriInfo uriInfo) {
        service.add(newUser);
        return Response.created(getLocation(uriInfo, newUser.getId())).build();
    }

    URI getLocation(UriInfo uriInfo, Long id) {
        return uriInfo.getAbsolutePathBuilder().path("" + id).build();
    }

    @Path("/authenticate")
    @POST
    @ApiOperation(value = "Authenticate user", notes = "Validate and issue token to user.")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid credentials input"),
            @ApiResponse(code = 200, message = "Token issued")})
    public Response authenticate(@ApiParam(value = "User that needs to be authenticated", required = true) Credential creds) {
        User validUser = service.isValid(creds.getUsername(), creds.getPassword());

        if (validUser != null) {
            String token = issuer.issueToken(creds.getUsername());
            //Set token and user id as part of response
            JsonObject json = Json.createObjectBuilder()
                    .add("id",    validUser.getId())
                    .add("token", token)
                    .build();

            return Response.ok(json).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @PUT
    @Path("{id}")
    @ApiOperation(value = "Update user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid user input")
            ,
            @ApiResponse(code = 404, message = "User not found")
            ,
            @ApiResponse(code = 200, message = "User updated")})
    public Response update(@ApiParam(value = "ID of user that needs to be updated",
            required = true)
                           @PathParam("id") Long id,
                           @ApiParam(value = "User that needs to be updated", required = true) User updated) {
        updated.setId(id);
        boolean done = service.update(updated);

        return done
                ? Response.ok(updated).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(value = "Remove the user")
    public Response remove(@ApiParam(value = "ID of user that needs to be removed",
            required = true)
                           @PathParam("id") Long id) {
        service.remove(id);
        return Response.ok().build();
    }
}
