package ch.evooq.hospital.rules;

import static ch.evooq.hospital.model.Condition.Diabetes;
import static ch.evooq.hospital.model.Cure.Insulin;

import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import java.util.List;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

@Rule(description = "Insulin prevents diabetic subject from dying, does not cure Diabetes")
public class InsulinRule {

    @org.jeasy.rules.annotation.Condition
    public boolean canPatientBePreventedFromDying(@Fact("patient") Patient patient, @Fact("cures") List<Cure> cures) {
        return cures.contains(Insulin) &&
                patient.has(Diabetes);
    }

    @Action
    public void preventFromDying(@Fact("patient") Patient patient) {
        patient.setCondition(Diabetes);
    }
}
