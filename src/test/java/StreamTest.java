import com.devo.stream.model.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StreamTest {

    private List<Person> persons = null;

    @Before
    void setUp() {

    }

    @Test
    void collectorsTest(){

    }

    @Test
    void firstPredicatesTest(){
        Stream<String> stream = Stream.of("one", "two", "three", "four", "five");

        Predicate<String> p1 = s -> s.length() > 3 ;

        Predicate<String> p2 = Predicate.isEqual("two");
        Predicate<String> p3 = Predicate.isEqual("three");

        stream
                .filter(p2.or(p3))
                .forEach(s -> System.out.println(s));
    }

    @Test
    void flatMapExampleTest(){

    }

    @Test
    void intermediaryAndFinalTest(){

    }

    @Test
    void reductionTest(){

    }

}
