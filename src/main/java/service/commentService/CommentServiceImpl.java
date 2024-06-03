package service.commentService;

import base.service.BaseServiceImpl;
import model.Comment;
import org.hibernate.SessionFactory;
import repository.commentRepository.CommentRepository;

public class CommentServiceImpl extends BaseServiceImpl<Comment, Long, CommentRepository> implements CommentService {
    public CommentServiceImpl(CommentRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }
}
