package ims.comments.boundary;

import ims.comments.entity.Comment;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;

@Path("comments/{issueid}")
public class CommentsResource {

    @DefaultValue("v1") @HeaderParam("X-apiversion")
    private String apiVersion;

    @PathParam("issueid")
    private Long issueId;

    @Inject
    private CommentsService service;

    @GET
    public Response getAll() {
        return Response.ok(service.getAll(issueId)).build();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long id) {
        Optional<Comment> found = service.get(id);
        if(found.isPresent() && found.get().getForIssue().equals(issueId)) {
            return Response.ok(found.get()).build();
        }
        return Response.noContent().build();
    }

    @POST
    public Response add(@Valid Comment newComment, @Context UriInfo uriInfo) {
        newComment.setForIssue(issueId);
        service.add(newComment);
        return Response.created(getLocation(uriInfo, newComment.getId())).build();
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        service.remove(id);
        return Response.ok().build();
    }

    URI getLocation(UriInfo uriInfo, Long id) {
        return uriInfo.getAbsolutePathBuilder().path("" + id).build();
    }
}
