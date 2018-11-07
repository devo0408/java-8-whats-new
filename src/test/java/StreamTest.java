import com.devo.stream.model.Person;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StreamTest {

    @Test
    public void collectorsTest(){

        final String people = "Sarah 15\n" +
                "Philip 21\n" +
                "Beth 23 \n" +
                "Simon 24\n" +
                "Nina 23\n" +
                "Allan 28\n" +
                "Leonard 18\n" +
                "Barbara 26\n" +
                "Penelope 18\n" +
                "Albert 31 \n" +
                "Lucy 42\n" +
                "Charles 26\n" +
                "Ella 24\n" +
                "Louis 32\n" +
                "Liza 17\n" +
                "Franck 18\n" +
                "Amy 26\n" +
                "Nathan 33";

        List<Person> persons = new ArrayList<>();

        try (
                BufferedReader reader = new BufferedReader(new StringReader(people));
                Stream<String> stream = reader.lines()
        ) {
            stream.map(line -> {
                String[] s = line.split(" ");
                Person p = new Person(s[0].trim(), Integer.parseInt(s[1]));
                persons.add(p);
                return p;
            });
        } catch (IOException ioe) {
            // todo handle IOException
        }

        Optional<Person> opt = persons.stream().filter(p -> p.getAge() >= 20)
                        .min(Comparator.comparing(Person::getAge));
        System.out.println(opt);

//        Optional<Person> opt2 = persons.stream().max(Person::getAge))
//        System.out.println(opt2);

        Map<Integer, String> map =
                persons.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Person::getAge,
                                        Collectors.mapping(
                                                Person::getName,
                                                Collectors.joining(", ")
                                        )
                                )
                        );
        System.out.println(map);

    }

    @Test
    public void predicatesTest(){
        Supplier<Stream<String>> streamSupplier = () -> Stream.of("one", "two", "three", "four", "five");
        Predicate<String> p1 = s -> s.length() > 3 ;
        Predicate<String> p2 = Predicate.isEqual("two");
        Predicate<String> p3 = Predicate.isEqual("three");

        assertEquals(Arrays.asList("three", "four", "five"),
                streamSupplier.get().filter(p1).collect(Collectors.toList()));
        assertEquals(Arrays.asList("two"),
                streamSupplier.get().filter(p2).collect(Collectors.toList()));
        assertEquals(Arrays.asList("two", "three"),
                streamSupplier.get().filter(p2.or(p3)).collect(Collectors.toList()));
        assertEquals(Arrays.asList("one", "two", "four", "five"),
                streamSupplier.get().filter(p3.negate()).collect(Collectors.toList()));
    }

    @Test
    public void flatMapTest(){

        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        List<Integer> list2 = Arrays.asList(2, 4, 6);
        List<Integer> list3 = Arrays.asList(3, 5, 7);

        List<List<Integer>> list = Arrays.asList(list1, list2, list3);

        Function<List<Integer>, Stream<Integer>> flatmapper = l -> l.stream();

        list.stream()
                .flatMap(flatmapper)
                .forEach(System.out::println);

        assertEquals(list1.size() + list2.size() + list3.size(),
                list.stream().flatMap(flatmapper).collect(Collectors.toList()).size());
    }

    @Test
    public void intermediaryAndFinalTest(){

        Stream<String> stream = Stream.of("one", "two", "three", "four", "five");
        Predicate<String> p1 = Predicate.isEqual("two");
        Predicate<String> p2 = Predicate.isEqual("three");

        List<String> listBeforeFilter = new ArrayList<>();
        List<String> listAfterFilter = new ArrayList<>();

        stream
                .peek(listBeforeFilter::add)
                .filter(p1.or(p2))
                .forEach(listAfterFilter::add);

        assertEquals(5, listBeforeFilter.size());
        assertEquals(2, listAfterFilter.size());
    }

    @Test
    public void reductionTest(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> red = list.stream().reduce(Integer::max);
        assertTrue(red.get() == 5);
    }

}
