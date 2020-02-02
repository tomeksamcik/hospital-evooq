package ch.evooq.hospital;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Main class")
public class MainTest {

    private static Stream<Arguments> provideInput() {
        return Stream.of(
                Arguments.of(new String[] { "ch.evooq.hospital.Main", "invalid", "invalid" }, "Please provide arguments in format", "Will display error message on invalid input"),
                Arguments.of(new String[] { "ch.evooq.hospital.Main", "D,D", "" }, "F:0,H:0,D:0,T:0,X:2", "Will run and display result on valid input, no cures"),
                Arguments.of(new String[] { "ch.evooq.hospital.Main", "F", "P" }, "F:0,H:1,D:0,T:0,X:0", "Will run and display result on valid input")
        );
    }

    @MethodSource("provideInput")
    @ParameterizedTest(name = "{2}")
    @DisplayName("Parametrized tests")
    public void testMainInput(String[] args, String expected, String display) {
        assertThat(new Main(args).init(), containsString(expected));
    }
}
