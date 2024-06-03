package repository.personRepository;

import base.repository.BaseRepository;
import model.Person;
import java.util.List;

public interface PersonRepository  extends BaseRepository<Person,Long> {
    List<Person> findAllPersons();

    List<Person> findPersonsByName(String name);

    List<Person> findPersonsByEmail(String email);

    long countPersons();

    boolean existsPersonById(Long id);
}
