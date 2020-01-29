package ch.evooq.hospital.rules.easy;

import static ch.evooq.hospital.model.Condition.Fever;
import static ch.evooq.hospital.model.Condition.Healthy;
import static ch.evooq.hospital.model.Cure.Aspirin;

import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import java.util.List;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

@Rule(description = "Aspirin cures Fever")
public class AspirinRule {

    @org.jeasy.rules.annotation.Condition
    public boolean canPatientBeCured(@Fact("patient") Patient patient, @Fact("cures") List<Cure> cures) {
        return cures.contains(Aspirin) &&
                patient.has(Fever);
    }

    @Action
    public void cure(@Fact("patient") Patient patient) {
        patient.setCondition(Healthy);
    }
}
