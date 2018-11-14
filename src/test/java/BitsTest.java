import org.junit.Test;

import java.util.StringJoiner;

import static org.junit.Assert.assertEquals;

public class BitsTest {

    @Test
    public void stringJoinerTest(){
        StringJoiner sj = new StringJoiner(", ", "{", "}");
        sj.add("one").add("two");
        assertEquals("{one, two}", sj.toString());
    }

}
