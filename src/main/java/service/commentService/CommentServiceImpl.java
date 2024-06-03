package service.commentService;

import base.service.BaseServiceImpl;
import model.Comment;
import model.Customer;
import model.Order;
import model.Proposal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.commentRepository.CommentRepository;

import java.util.List;

public class CommentServiceImpl extends BaseServiceImpl<Comment, Long, CommentRepository> implements CommentService {
    public CommentServiceImpl(CommentRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }


    @Override
    public void addComment(Long proposalId, Long customerId, String comment, int rating) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Proposal proposal = session.get(Proposal.class, proposalId);
            Customer customer = session.get(Customer.class, customerId);
            Comment commentEntity = Comment.builder()
                    .comment(comment)
                    .rating(rating)
                    .proposal(proposal)
                    .customer(customer)
                    .build();
            repository.saveOrUpdate(commentEntity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Comment> getCommentsForProposal(Long proposalId) {
        return repository.findCommentsByProposalId(proposalId);
    }
    @Override
    public List<Comment> getCommentsForCustomer(Long customerId) {
        return repository.findCommentsByCustomerId(customerId);
    }
}
