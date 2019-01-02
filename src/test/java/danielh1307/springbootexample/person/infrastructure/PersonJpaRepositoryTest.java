package danielh1307.springbootexample.person.infrastructure;

import danielh1307.springbootexample.person.domain.Person;
import danielh1307.springbootexample.person.infrastructure.persistence.PersonJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static danielh1307.springbootexample.person.domain.Person.newPerson;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonJpaRepositoryTest {

    @Autowired
    // as with @JdbcTest, a ROLLBACK is done after each test
    // if you want to avoid this, you can add @Rollback(false) to a method
    private PersonJpaRepository personJpaRepository;

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
    public void update_intoJpaRepository_entityIsUpdated() {
        // arrange
        Person hugoHase = newPerson("Hase", "Hugo", LocalDate.of(2000, 1, 1));
        hugoHase = this.personJpaRepository.save(hugoHase);

        // act
        Person updatePerson = this.personJpaRepository.getOne(hugoHase.getId());
        updatePerson.setName("Igel");

        // assert
        hugoHase = this.personJpaRepository.getOne(hugoHase.getId());
        assertThat(hugoHase.getName(), is(equalTo("Igel")));
    }

    private long getNumberOfRowsInPerson() {
        return this.personJpaRepository.count();
    }
}
