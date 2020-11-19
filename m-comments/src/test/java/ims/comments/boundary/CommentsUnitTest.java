package ims.comments.boundary;

import ims.comments.control.FindUserName;
import ims.comments.entity.Comment;
import ims.comments.entity.CommentInfo;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommentsUnitTest {

    CommentsService service;

    @Before
    public void setup() {
        //No CDI used just POJO unit testing
        this.service = new CommentsService();

        this.service.setFindUserName(mock(FindUserName.class));

        when(this.service.getFindUserName().getUserName(anyLong()))
                .thenReturn("prashant");
    }

    @Test
    public void testCommentInfoUpdatedByName() throws Exception {
        Comment comment = new Comment();
        comment.setByUser(11L);
        //Test updateName which should return our Mock data
        CommentInfo updatedInfo = service.updateName(new CommentInfo(comment));

        assertNotNull(updatedInfo);
        assertThat(updatedInfo.getByUserName(), equalTo("prashant"));
    }

}
