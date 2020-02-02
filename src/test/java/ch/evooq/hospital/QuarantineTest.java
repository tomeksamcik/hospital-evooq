package ch.evooq.hospital;

import static ch.evooq.hospital.Utils.Some;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsIterableContaining.hasItem;

import ch.evooq.hospital.rules.RulesContext;
import ch.evooq.hospital.rules.easy.FlyingSpaghettiMonsterRule;
import ch.evooq.hospital.rules.simple.SimpleRulesContext;
import com.jasongoodwin.monads.Try;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("In Hospital")
public class QuarantineTest {

    private Quarantine quarantine;

    private RulesContext ctx = new SimpleRulesContext();

    private static Stream<Arguments> provideSubjectsAndCures() {
        return Stream.of(
                Arguments.of("D", "", "F:0,H:0,D:0,T:0,X:1", "Patient with Diabetes will die without Insulin"),
                Arguments.of("F", "P", "F:0,H:1,D:0,T:0,X:0", "Patient with Fever will be cured with Paracetamol"),
                Arguments.of("F", "As", "F:0,H:1,D:0,T:0,X:0", "Patient with Fever will be cured with Aspirin"),
                Arguments.of("T", "An", "F:0,H:1,D:0,T:0,X:0", "Patient with Tuberculosis will be cured with Antibiotic"),
                Arguments.of("D", "I", "F:0,H:0,D:1,T:0,X:0", "Patient with Diabetes will not die if cured with Insulin"),
                Arguments.of("H", "I,An", "F:1,H:0,D:0,T:0,X:0", "Patient that is healthy will catch Fever then treated with Insulin and Antibiotic"),
                Arguments.of("H", "P,As", "F:0,H:0,D:0,T:0,X:1", "Patient will die then cured with Paracetamol and Aspirin"),
                Arguments.of("D", "I,An", "F:0,H:0,D:1,T:0,X:0", "Patient with Diabetes cured with Insulin and Antibiotic will not die and will not catch Fever"),
                Arguments.of("F", "P,As", "F:0,H:0,D:0,T:0,X:1", "Patient with Fever cured with Paracetamol and Aspirin will not recover, but die instead"),
                Arguments.of("H", "I,An,P", "F:0,H:1,D:0,T:0,X:0", "Patient that is Healthy will first catch Fever and then recover then treated with Insulin, Paracetamol and Antibiotic"),
                Arguments.of("T", "I,An", "F:1,H:0,D:0,T:0,X:0", "Patient with Tuberculosis cured with Insulin and Antibiotic will first recover from Tuberculosis and then catch Fever"),
                Arguments.of("T", "I,An,P", "F:0,H:1,D:0,T:0,X:0", "Patient with Tuberculosis cured with Insulin, Antibiotic and Paracetamol will first recover from Tuberculosis, then catch Fever and then recover"),
                Arguments.of("F,H,H,T", "", "F:1,H:2,D:0,T:1,X:0", "Valid patient list will be correctly interpreted"),
                Arguments.of("F,H,X,Y,Z", "", "F:1,H:1,D:0,T:0,X:1", "Invalid patient list will be correctly interpreted"),
                Arguments.of("F,H,D,D,D,H,T", "", "F:1,H:2,D:0,T:1,X:3", "Some patients will die without treatment"),
                Arguments.of("F,H,D,D,D,H,T", "I,An", "F:4,H:0,D:3,T:0,X:0", "Some patients will be cured from Tuberculosis, some won't die from Diabetes, but all healthy will catch Fever"),
                Arguments.of("F,H,D,D,D,H,T", "P,As", "F:0,H:0,D:0,T:0,X:7", "Some patients will be cured from Fever, but eventually all will die")
        );
    }

    @ParameterizedTest(name = "{3}")
    @DisplayName("Parametrized tests")
    @MethodSource("provideSubjectsAndCures")
    public void testPatients(String subjects, String cures, String expected, String display) throws InvalidInputException {
        quarantine = new Quarantine(Some(subjects), Some(cures), ctx);
        assertThat(quarantine.cure(), equalTo(expected));
    }

    private static final int EXECUTION_MULTIPLIER = 2;

    @Test
    @DisplayName("Dead patient will occasionally be resurrected by the Flying Spaghetti Monster")
    public void testResurrection() {
        /*
        Muting logs to speed up execution
         */
        Logger.getRootLogger().setLevel(Level.toLevel("OFF"));
        List<String> results = IntStream
                .range(0, FlyingSpaghettiMonsterRule.RESURRECTION_CHANCE_ONE_IN * EXECUTION_MULTIPLIER)
                .mapToObj(i -> Try.ofFailable(() -> new Quarantine(Some("X"), Some(""), ctx).cure())
                        .recover(e -> ""))
                .collect(Collectors.toList());
        assertThat(results, hasItem("F:0,H:1,D:0,T:0,X:0"));
        Logger.getRootLogger().setLevel(Level.toLevel("DEBUG"));
    }
}
