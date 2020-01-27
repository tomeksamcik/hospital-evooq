package ch.evooq.hospital;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsIterableContaining.hasItem;

import ch.evooq.hospital.rules.FlyingSpaghettiMonsterRule;
import ch.evooq.hospital.rules.RulesContext;
import com.jasongoodwin.monads.Try;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("In Hospital")
public class QuarantineTest {

    private Quarantine quarantine;

    private RulesContext ctx = new RulesContext();

    /*
    Not Java convention, but Emulating Scala
     */
    public static Optional<String> Some(String str) {
        return Optional.of(str);
    }

    @Test
    @DisplayName("Valid patient list will be correctly interpreted")
    public void testCorrectPatientList() throws InvalidInputException {
        quarantine = new Quarantine(Some("F,H,D,D,D,H,T"), Some(""), ctx);
        assertThat(quarantine.report(), equalTo("F:1,H:2,D:3,T:1,X:0"));
    }

    @Test
    @DisplayName("Invalid patient list will be correctly interpreted")
    public void testIncorrectPatientList() throws InvalidInputException {
        quarantine = new Quarantine(Some("F,H,D,D,X,Y,Z"), Some(""), ctx);
        assertThat(quarantine.report(), equalTo("F:1,H:1,D:2,T:0,X:1"));
    }

    @Nested
    @DisplayName("Some Patients")
    class Patients {

        @Test
        @DisplayName("Will die without treatment")
        public void testSomePatientsWillDieWithoutTreatment() throws InvalidInputException {
            quarantine = new Quarantine(Some("F,H,D,D,D,H,T"), Some(""), ctx);
            assertThat(quarantine.cure(), equalTo("F:1,H:2,D:0,T:1,X:3"));
        }

        @Test
        @DisplayName("Will be cured from Tuberculosis, some won't die from Diabetes, but all healthy will catch Fever")
        public void testSomePatientsWillBeCuredSomeWontDieButSomeCatchFever() throws InvalidInputException {
            quarantine = new Quarantine(Some("F,H,D,D,D,H,T"), Some("I,An"), ctx);
            assertThat(quarantine.cure(), equalTo("F:4,H:0,D:3,T:0,X:0"));
        }

        @Test
        @DisplayName("Will be cured from Fever, but eventually all will die")
        public void testSomePatientsWillBeCuredButThenAllDie() throws InvalidInputException {
            quarantine = new Quarantine(Some("F,H,D,D,D,H,T"), Some("P,As"), ctx);
            assertThat(quarantine.cure(), equalTo("F:0,H:0,D:0,T:0,X:7"));
        }
    }

    @Nested
    @DisplayName("Patient")
    class Patient {

        @Test
        @DisplayName("With Diabetes will die without Insulin")
        public void testDiabetesPatientsDieWithoutInsulin() throws InvalidInputException {
            quarantine = new Quarantine(Some("D,D"), Some(""), ctx);
            assertThat(quarantine.cure(), equalTo("F:0,H:0,D:0,T:0,X:2"));
        }

        @Test
        @DisplayName("With Fever will be cured with Paracetamol")
        public void testParacetamolCuresFever() throws InvalidInputException {
            quarantine = new Quarantine(Some("F"), Some("P"), ctx);
            assertThat(quarantine.cure(), equalTo("F:0,H:1,D:0,T:0,X:0"));
        }

        @Test
        @DisplayName("With Fever will be cured with Aspirin")
        public void testAspirinCuresFever() throws InvalidInputException {
            quarantine = new Quarantine(Some("F"), Some("As"), ctx);
            assertThat(quarantine.cure(), equalTo("F:0,H:1,D:0,T:0,X:0"));
        }

        @Test
        @DisplayName("With Tuberculosis will be cured with Antibiotic")
        public void testAntibioticCuresTuberculosis() throws InvalidInputException {
            quarantine = new Quarantine(Some("T"), Some("An"), ctx);
            assertThat(quarantine.cure(), equalTo("F:0,H:1,D:0,T:0,X:0"));
        }

        @Test
        @DisplayName("With Diabetes will not die if cured with Insulin")
        public void testInsulinPreventsDiabeticSubjectFromDyingDoesNotCureDiabetes() throws InvalidInputException {
            quarantine = new Quarantine(Some("D"), Some("I"), ctx);
            assertThat(quarantine.cure(), equalTo("F:0,H:0,D:1,T:0,X:0"));
        }

        @Test
        @DisplayName("That is healthy will catch Fever when treated with Insulin and Antibiotic")
        public void testIfInsulinIsMixedWithAntibioticHealthyPeopleCatchFever() throws InvalidInputException {
            quarantine = new Quarantine(Some("H"), Some("I,An"), ctx);
            assertThat(quarantine.cure(), equalTo("F:1,H:0,D:0,T:0,X:0"));
        }

        @Test
        @DisplayName("Will die when cured with Paracetamol and Aspirin")
        public void testParacetamolKillsSubjectIfMixedWithAspirin() throws InvalidInputException {
            quarantine = new Quarantine(Some("H"), Some("P,As"), ctx);
            assertThat(quarantine.cure(), equalTo("F:0,H:0,D:0,T:0,X:1"));
        }

        @Test
        @DisplayName("With Diabetes cured with Insulin and Antibiotic will not die and will not catch Fever")
        public void testInsulinMixedWithAntibioticOnDiabetic() throws InvalidInputException {
            quarantine = new Quarantine(Some("D"), Some("I,An"), ctx);
            assertThat(quarantine.cure(), equalTo("F:0,H:0,D:1,T:0,X:0"));
        }

        @Test
        @DisplayName("With Fever cured with Paracetamol and Aspirin will not recover, but die instead")
        public void testParacetamolMixedWithAspirineOnFever() throws InvalidInputException {
            quarantine = new Quarantine(Some("F"), Some("P,As"), ctx);
            assertThat(quarantine.cure(), equalTo("F:0,H:0,D:0,T:0,X:1"));
        }

        /*
        Result depends on priorities of InsulinAntibioticRule and ParacetamolRule
        The assumption here is that InsulinAntibioticRule is applied first.
         */
        @Test
        @DisplayName("That is Healthy will first catch Fever and then recover when treated with Insulin, Paracetamol and Antibiotic ")
        public void testInsulinWithAntibioticWithParacetamolOnHealty() throws InvalidInputException {
            quarantine = new Quarantine(Some("H"), Some("I,An,P"), ctx);
            assertThat(quarantine.cure(), equalTo("F:0,H:1,D:0,T:0,X:0"));
        }

        /*
        Result depends on priorities of InsulinAntibioticRule and AntibioticRule
        The assumption here is that AntibioticRule is applied first.
         */
        @Test
        @DisplayName("With Tuberculosis cured with Insulin and Antibiotic will first recover from Tuberculosis and then catch Fever")
        public void testInsulinMixedWithAntibioticOnTuberculosis() throws InvalidInputException {
            quarantine = new Quarantine(Some("T"), Some("I,An"), ctx);
            assertThat(quarantine.cure(), equalTo("F:1,H:0,D:0,T:0,X:0"));
        }

        /*
        Result depends on priorities of InsulinAntibioticRule, AntibioticRule and ParacetamolRule
        The assumption here is that AntibioticRule is applied first, InsulinAntibioticRule second,
        ParacetamolRule third.
         */
        @Test
        @DisplayName("With Tuberculosis cured with Insulin, Antibiotic and Paracetamol will first recover from Tuberculosis, then catch Fever and then recover")
        public void testInsulinWithAntibioticWithParacetamolOnTuberculosis() throws InvalidInputException {
            quarantine = new Quarantine(Some("T"), Some("I,An,P"), ctx);
            assertThat(quarantine.cure(), equalTo("F:0,H:1,D:0,T:0,X:0"));
        }

        private static final int EXECUTION_MULTIPLIER = 2;

        @Test
        @DisplayName("That is Dead will occasionally be resurrected by the Flying Spaghetti Monster")
        public void testResurrection() {
            /*
            Muting logs to speed up execution
             */
            Logger.getRootLogger().setLevel(Level.toLevel("OFF"));
            List<String> results = IntStream
                    .range(0, FlyingSpaghettiMonsterRule.RESURRECTION_CHANCE_ONE_IN * EXECUTION_MULTIPLIER)
                    .mapToObj(i -> Try.ofFailable(() -> {
                        quarantine = new Quarantine(Some("X"), Some(""), ctx);
                        return quarantine.cure();
                    }).recover(e -> ""))
                    .collect(Collectors.toList());
            assertThat(results, hasItem("F:0,H:1,D:0,T:0,X:0"));
        }
    }
}
