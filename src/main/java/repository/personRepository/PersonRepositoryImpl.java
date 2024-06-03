package repository.personRepository;

import base.repository.BaseRepositoryImpl;
import model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;


public class PersonRepositoryImpl extends BaseRepositoryImpl<Person, Long> implements PersonRepository {
    public PersonRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Person> getEntityClass() {
        return Person.class;
    }

    @Override
    public String getMyClass() {
        return "person";
    }

    @Override
    public List<Person> findAllPersons() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query<Person> query = session.createQuery("FROM Person", Person.class);
            List<Person> resultList = query.getResultList();
            session.getTransaction().commit();
            return resultList;

        }    }

    @Override
    public List<Person> findPersonsByName(String name) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query<Person> query = session.createQuery("FROM Person p WHERE p.name = :name", Person.class);
            query.setParameter("name", name);
            List<Person> resultList = query.getResultList();
            session.getTransaction().commit();
            return resultList;

        }
    }

    @Override
    public List<Person> findPersonsByEmail(String email) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query<Person> query = session.createQuery("FROM Person p WHERE p.email = :email", Person.class);
            query.setParameter("email", email);
            List<Person> resultList = query.getResultList();
            session.getTransaction().commit();
            return resultList;

        }
    }

    @Override
    public long countPersons() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query<Long> query = session.createQuery("SELECT COUNT(p) FROM Person p", Long.class);
            long count = query.getSingleResult();
            session.getTransaction().commit();
            return count;

        }
    }

    @Override
    public boolean existsPersonById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery("SELECT COUNT(p) FROM Person p WHERE p.id = :id", Long.class);
        query.setParameter("id", id);
        long count = query.getSingleResult();
        return count > 0;
    }
}
