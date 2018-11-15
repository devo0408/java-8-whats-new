import com.devo.maps.Person;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapTest {

    private static List<Person> persons;

    @Before
    public void setUp(){
        persons = new ArrayList<>();
        String inputData = "Sarah 15 F\n" +
                "Philip 21 M\n" +
                "Beth 23 F\n" +
                "Simon 24 M\n" +
                "Nina 23 F\n" +
                "Allan 28 M\n" +
                "Leonard 18 M\n" +
                "Barbara 26 F\n" +
                "Penelope 18 F\n" +
                "Albert 31 M\n" +
                "Lucy 42 F\n" +
                "Charles 26 M\n" +
                "Ella 24 F\n" +
                "Louis 32 M\n" +
                "Liza 17 F\n" +
                "Franck 18 M\n" +
                "Amy 26 F\n" +
                "Nathan 33 M";

            try (
                    BufferedReader reader = new BufferedReader(new StringReader(inputData));
                    Stream<String> stream = reader.lines()
            ) {
                persons = stream.map(line -> {
                    String[] s = line.split(" ");
                    Person p = new Person(
                            s[0].trim(),
                            Integer.parseInt(s[1]),
                            s[2].trim()
                    );
                    return p;
                }).collect(Collectors.toList());
            } catch (IOException ioe) {
                // todo handle IOException
            }
    }

    private static Map<Integer, List<Person>> mapByAge(List<Person> list) {
        Map<Integer, List<Person>> map =
                list.stream().collect(
                        Collectors.groupingBy(
                                Person::getAge
                        )
                );
        return map;
    }

    @Test
    public void mergingMapsTest(){

        List<Person> list1 = persons.subList(1, 10);
        List<Person> list2 = persons.subList(10, persons.size());

        Map<Integer, List<Person>> map1 = mapByAge(list1);
         System.out.println("Map 1");
        map1.forEach((age, list) -> System.out.println(age + " -> " + list));

        Map<Integer, List<Person>> map2 = mapByAge(list2);
         System.out.println("Map 2");
        map2.forEach((age, list) -> System.out.println(age + " -> " + list));

        map2.entrySet().stream()
                .forEach(
                        entry ->
                                map1.merge(
                                        entry.getKey(),
                                        entry.getValue(),
                                        (l1, l2) -> {
                                            l1.addAll(l2);
                                            return l1;
                                        }
                                )
                );
        System.out.println("Map 1 merged");

    }

    @Test
    public void buildingBiMapTest(){

        Map<Integer, List<Person>> map =
                persons.stream().collect(
                        Collectors.groupingBy(
                                Person::getAge
                        )
                );

        map.forEach((age, list) -> System.out.println(age + " -> " + list));

        Map<Integer, Map<String, List<Person>>> bimap =
                new HashMap<>();

        persons.forEach(
                person ->
                        bimap.computeIfAbsent(
                                person.getAge(),
                                HashMap::new
                        ).merge(
                                person.getGender(),
                                new ArrayList<>(Arrays.asList(person)),
                                (l1, l2) -> {
                                    l1.addAll(l2);
                                    return l1;
                                }
                        )
        );

        System.out.println("Bimap : ");
        bimap.forEach(
                (age, m) -> System.out.println(age + " -> " + m)
        );

    }

}
