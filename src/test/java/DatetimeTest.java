import com.devo.datetime.Person;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class DatetimeTest {

    @Test
    public void doTest(){

        String people = "Sarah 1999 12 15\n" +
                "Philip 1993 8 12\n" +
                "Beth 1991 6 5\n" +
                "Simon 1990 3 23\n" +
                "Nina 1991 7 12\n" +
                "Allan 1985 2 14\n" +
                "Leonard 1996 10 27\n" +
                "Barbara 1988 4 19\n";

        List<Person> persons = new ArrayList<>();

        try (
                BufferedReader reader = new BufferedReader(new StringReader(people));
                Stream<String> stream = reader.lines()
        ) {
            persons = stream.map(line -> {
                String[] s = line.split(" ");

                String name = s[0].trim();
                int year = Integer.parseInt(s[1]);
                Month month = Month.of(Integer.parseInt(s[2]));
                int day = Integer.parseInt(s[3]);

                return new Person(name, LocalDate.of(year, month, day));
            }).collect(Collectors.toList());

        } catch (IOException ioe) {
            // todo handle IOException
        }

        LocalDate now = LocalDate.of(2018, Month.NOVEMBER, 13);
        List<Person> result = persons.stream()
                .filter(p -> Period.between(p.getDateOfBirth(), now).getYears() < 20)
                .collect(Collectors.toList());

        assertEquals(1, result.size());

        Person sarah = result.stream().findFirst().get();
        LocalDate sarahBd = sarah.getDateOfBirth();

        assertEquals(1999, sarahBd.getYear());
        assertEquals(12, sarahBd.getMonthValue());
        assertEquals(15, sarahBd.getDayOfMonth());

    }

}
