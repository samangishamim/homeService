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

}
