package ch.evooq.hospital;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import ch.evooq.hospital.model.Condition;
import ch.evooq.hospital.model.Patient;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Reporter")
public class ReporterTest {

    @Test
    @DisplayName("Will translate input to output format")
    public void testInvalidArguments() {
        List<Patient> patients = parsePatients("F,H,D,D,D,H,T");
        assertThat(Reporter.report(patients), equalTo("F:1,H:2,D:3,T:1,X:0"));
    }

    private List<Patient> parsePatients(String subjects) {
        return Arrays.stream(subjects.split(","))
                .map(p -> Condition.get(p))
                .filter(Optional::isPresent)
                .map(c -> new Patient(c.get()))
                .collect(Collectors.toList());
    }

}
