package danielh1307.springbootexample.person.infrastructure.persistence;

import danielh1307.springbootexample.person.domain.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

// in contrast to PersonJpaRepository, we do not have a bunch of pre-defined methods here
public interface PersonSimpleJpaRepository extends Repository<Person, Integer> {

    long count();

    Person save(Person person);

    Optional<Person> findByVorname(String vorname);

    // does the same as findByVorname
    @Query("SELECT a FROM #{#entityName} a WHERE a.vorname LIKE :vorname")
    Optional<Person> obfuscatedMethod(@Param("vorname") String obfuscated);
}
