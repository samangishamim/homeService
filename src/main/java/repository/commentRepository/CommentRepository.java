package repository.commentRepository;

import base.repository.BaseRepository;
import model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends BaseRepository<Comment, Long> {
     List<Comment> findCommentsByProposalId(Long proposalId);
    List<Comment> findCommentsByCustomerId(Long customerId);
    void deleteCommentById(Long id);
    Optional<Comment> findCommentById(Long id);
}
