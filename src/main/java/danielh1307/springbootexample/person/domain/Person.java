package danielh1307.springbootexample.person.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String name;

    private String vorname;

    private LocalDate geburtsdatum;

    private Person(String name, String vorname, LocalDate geburtsdatum) {
        this.name = name;
        this.vorname = vorname;
        this.geburtsdatum = geburtsdatum;
    }

    public static Person newPerson(String name, String vorname, LocalDate geburtsdatum) {
        return new Person(name, vorname, geburtsdatum);
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getVorname() {
        return this.vorname;
    }

    public LocalDate getGeburtsdatum() {
        return this.geburtsdatum;
    }

    public void setName(String name) {
        this.name = name;
    }
}
