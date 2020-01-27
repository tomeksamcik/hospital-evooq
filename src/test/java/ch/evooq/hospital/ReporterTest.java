package ch.evooq.hospital;

import static ch.evooq.hospital.QuarantineTest.Some;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import ch.evooq.hospital.model.Patient;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Reporter")
public class ReporterTest {

    @Test
    @DisplayName("Will translate input to output format")
    public void testInvalidArguments() {
        List<Patient> patients = Parser.parsePatients(Some("F,H,D,D,D,H,T"));
        assertThat(Reporter.report(patients), equalTo("F:1,H:2,D:3,T:1,X:0"));
    }
}
