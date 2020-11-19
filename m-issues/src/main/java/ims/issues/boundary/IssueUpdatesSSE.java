package ims.issues.boundary;


import ims.issues.entity.IssueEvent;

import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

@Singleton
@Path("feed")
public class IssueUpdatesSSE {

    private SseBroadcaster broadcaster;
    private Sse sse;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void subscribe(@Context Sse sse, @Context SseEventSink eventSink) {
        this.sse = sse;
        if(this.broadcaster == null) {
            this.broadcaster = sse.newBroadcaster();
        }
        this.broadcaster.register(eventSink);
    }

    public void observeTaskUpdates(@Observes IssueEvent event){
        if(this.broadcaster == null) {
            //No one's listening
            return;
        }
        String stats = JsonbBuilder.create().toJson(event);
        this.broadcaster.broadcast(this.sse.newEvent(stats));
    }

    @PreDestroy
    public void free(){
        if(this.broadcaster != null) {
            this.broadcaster.close();
        }
    }
}
