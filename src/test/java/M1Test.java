import com.devo.m1.Comparator;
import com.devo.m1.Person;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class M1Test {

    @Test
    public void doTest(){

        Person person1 = new Person("John", "Mcclane", 31);
        Person person2 = new Person("Peter", "Parker", 23);

        Function<Person, String> f1 = p -> p.getFirstName();
        Function<Person, String> f2 = p -> p.getLastName();
        Function<Person, Integer> f3 = p -> p.getAge();
        assertEquals(person1.getFirstName(), f1.apply(person1));
        assertEquals(person1.getLastName(), f2.apply(person1));
        assertEquals(Long.valueOf(person1.getAge()), Long.valueOf(f3.apply(person1)));

        Comparator<Person> cmpAge = (p1, p2) -> p1.getAge() - p2.getAge() ;
        Comparator<Person> cmpFirstName = (p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName()) ;
        Comparator<Person> cmpLastName = (p1, p2) -> p1.getLastName().compareTo(p2.getLastName()) ;
        assertTrue(cmpAge.compare(person1, person2) > 0);
        assertTrue(cmpFirstName.compare(person1, person2) < 0);
        assertTrue(cmpLastName.compare(person1, person2) < 0);

        Comparator<Person> cmpPersonAge = Comparator.comparing(Person::getAge);
        Comparator<Person> cmpPersonLastName = Comparator.comparing(Person::getLastName);
        Comparator<Person> cmp = Comparator.comparing(Person::getLastName)
                .thenComparing(Person::getFirstName)
                .thenComparing(Person::getAge);
        assertTrue(cmpPersonAge.compare(person1, person2) > 0);
        assertTrue(cmpPersonLastName.compare(person1, person2) < 0);
        assertTrue(cmp.compare(person1, person2) < 0);
    }

}
