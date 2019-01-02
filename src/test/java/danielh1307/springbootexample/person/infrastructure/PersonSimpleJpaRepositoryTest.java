package danielh1307.springbootexample.person.infrastructure;

import danielh1307.springbootexample.person.domain.Person;
import danielh1307.springbootexample.person.infrastructure.persistence.PersonSimpleJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static danielh1307.springbootexample.person.domain.Person.newPerson;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonSimpleJpaRepositoryTest {

    @Autowired
    private PersonSimpleJpaRepository personJpaRepository;

    @Test
    public void insert_intoJpaRepository_entityIsInserted() {
        // arrange
        Person hugoHase = newPerson("Hase", "Hugo", LocalDate.of(2000, 1, 1));

        // act
        hugoHase = this.personJpaRepository.save(hugoHase);

        // assert
        assertThat(hugoHase.getId(), is(notNullValue()));
        assertThat(getNumberOfRowsInPerson(), is(equalTo(1L)));
    }

    @Test
    public void findByVorname_regularVorname_correctEntityIsReturned() {
        // arrange
        Person hugoHase = newPerson("Hase", "Hugo", LocalDate.of(2000, 1, 1));
        this.personJpaRepository.save(hugoHase);

        // act
        Optional<Person> foundEntity = this.personJpaRepository.findByVorname("Hugo");

        // assert
        assertTrue(foundEntity.isPresent());
        assertThat(foundEntity.get().getName(), is(equalTo("Hase")));
    }

    @Test
    public void obfuscatedMethod_regularVorname_correctEntityIsReturned() {
        // arrange
        Person hugoHase = newPerson("Hase", "Hugo", LocalDate.of(2000, 1, 1));
        this.personJpaRepository.save(hugoHase);

        // act
        Optional<Person> foundEntity = this.personJpaRepository.obfuscatedMethod("Hugo");

        // assert
        assertTrue(foundEntity.isPresent());
        assertThat(foundEntity.get().getName(), is(equalTo("Hase")));
    }

    private long getNumberOfRowsInPerson() {
        return this.personJpaRepository.count();
    }
}
