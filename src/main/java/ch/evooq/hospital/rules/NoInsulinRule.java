package ch.evooq.hospital.rules;

import static ch.evooq.hospital.model.Condition.Dead;
import static ch.evooq.hospital.model.Condition.Diabetes;
import static ch.evooq.hospital.model.Cure.Insulin;

import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import java.util.List;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

@Rule(description = "Patient with Diabetes dies without Insuline")
public class NoInsulinRule {

    @org.jeasy.rules.annotation.Condition
    public boolean canPatientDie(@Fact("patient") Patient patient, @Fact("cures") List<Cure> cures) {
        return !cures.contains(Insulin) &&
                patient.has(Diabetes);
    }

    @Action
    public void die(@Fact("patient") Patient patient) {
        patient.setCondition(Dead);
    }
}
