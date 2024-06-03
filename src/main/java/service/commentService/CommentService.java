package service.commentService;

import base.service.BaseService;
import model.Comment;

import java.util.List;

public interface CommentService extends BaseService<Comment,Long> {
    void addComment(Long proposalId, Long customerId, String comment, int rating);

    List<Comment> getCommentsForProposal(Long proposalId);

    List<Comment> getCommentsForCustomer(Long customerId);
}
