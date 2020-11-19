package ims.comments.boundary;

import ims.comments.control.FindUserName;
import ims.comments.entity.Comment;
import ims.comments.entity.CommentInfo;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Arquillian.class)
public class CommentsIntegrationTest {

    @Inject
    private CommentsService service;

    @Inject
    private FindUserName findUserName;

    /*
     * Sets up the in-container testing (resource and class files under test)
     */
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "ims-comments.war")
                .addClasses(CommentsService.class,
                        Comment.class,
                        CommentInfo.class,
                        FindUserName.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("web.xml")
                .addAsResource("persistence.xml",
                        "META-INF/persistence.xml");
    }

    @Before
    public void setupMock() {
        this.findUserName = mock(FindUserName.class);

        when(this.findUserName.getUserName(anyLong()))
                .thenReturn("prashant");
        //Explicitly set mocked FindUserName in CommentsService
        this.service.setFindUserName(this.findUserName);
    }

    @Test
    public void testAddComment() throws Exception {
        Comment comment = new Comment();
        comment.setByUser(11L);
        comment.setForIssue(200L);
        comment.setText("Test");
        comment.setCreated(LocalDateTime.now());
        this.service.add(comment);
        Optional<Comment> dbComment = this.service.get(comment.getId());

        assertTrue(dbComment.isPresent());
        assertThat(dbComment.get().getId(), equalTo(comment.getId()));
    }

    @Test
    public void testCommentInfoUpdatedByName() throws Exception {
        Comment comment = new Comment();
        comment.setByUser(11L);

        CommentInfo updatedInfo = this.service.updateName(new CommentInfo(comment));

        assertNotNull(updatedInfo);
        assertThat(updatedInfo.getByUserName(), equalTo("prashant"));
    }
}
