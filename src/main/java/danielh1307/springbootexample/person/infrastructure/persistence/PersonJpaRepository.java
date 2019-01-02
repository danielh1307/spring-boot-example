package danielh1307.springbootexample.person.infrastructure.persistence;

import danielh1307.springbootexample.person.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonJpaRepository extends JpaRepository<Person, Integer> {
}
