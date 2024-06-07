package repository.commentRepository;

import base.repository.BaseRepository;
import model.Comment;

import java.util.List;

public interface CommentRepository extends BaseRepository<Comment, Long> {
     List<Comment> findCommentsByProposalId(Long proposalId);
    List<Comment> findCommentsByCustomerId(Long customerId);

}
