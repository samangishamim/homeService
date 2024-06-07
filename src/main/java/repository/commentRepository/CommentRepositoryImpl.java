package repository.commentRepository;

import base.repository.BaseRepositoryImpl;
import model.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class CommentRepositoryImpl  extends BaseRepositoryImpl<Comment, Long> implements CommentRepository {
    public CommentRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Comment> getEntityClass() {
        return Comment.class;
    }

    @Override
    public String getMyClass() {
        return "comment";
    }

    @Override
    public List<Comment> findCommentsByProposalId(Long proposalId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Comment> query = session.createQuery("FROM Comment c WHERE c.proposal.id = :proposalId", Comment.class);
        query.setParameter("proposalId", proposalId);
        return query.list();
    }

    @Override
    public List<Comment> findCommentsByCustomerId(Long customerId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Comment> query = session.createQuery("FROM Comment c WHERE c.customer.id = :customerId", Comment.class);
        query.setParameter("customerId", customerId);
        return query.list();
    }
}
