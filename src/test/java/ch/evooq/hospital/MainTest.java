package ch.evooq.hospital;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Main class")
public class MainTest {

    @Test
    @DisplayName("Will display error message on invalid input")
    public void testInvalidArguments() {
        Main main = new Main(new String[] { "ch.evooq.hospital.Main", "invalid", "invalid" });
        assertThat(main.init(), containsString("Please provide arguments in format"));
    }

    @Test
    @DisplayName("Will run and display result on valid input, no cures")
    public void testValidArgumentsNoCures() {
        Main main = new Main(new String[] { "ch.evooq.hospital.Main", "D,D", "" });
        assertThat(main.init(), containsString("F:0,H:0,D:0,T:0,X:2"));
    }

    @Test
    @DisplayName("Will run and display result on valid input")
    public void testValidArguments() {
        Main main = new Main(new String[] { "ch.evooq.hospital.Main", "F", "P" });
        assertThat(main.init(), containsString("F:0,H:1,D:0,T:0,X:0"));
    }
}
