package ch.evooq.hospital.rules;

import static ch.evooq.hospital.model.Condition.Healthy;
import static ch.evooq.hospital.model.Condition.Tuberculosis;
import static ch.evooq.hospital.model.Cure.Antibiotic;

import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import java.util.List;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

@Rule(description = "Antibiotic cures Tuberculosis")
public class AntibioticRule {

    @org.jeasy.rules.annotation.Condition
    public boolean canPatientBeCured(@Fact("patient") Patient patient, @Fact("cures") List<Cure> cures) {
        return cures.contains(Antibiotic) &&
                patient.has(Tuberculosis);
    }

    @Action
    public void cure(@Fact("patient") Patient patient) {
        patient.setCondition(Healthy);
    }
}
