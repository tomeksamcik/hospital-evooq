package ch.evooq.hospital.rules.easy;

import static ch.evooq.hospital.model.Condition.Fever;
import static ch.evooq.hospital.model.Cure.Antibiotic;
import static ch.evooq.hospital.model.Cure.Insulin;

import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import java.util.List;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

@Rule(description = "If insulin is mixed with antibiotic, healthy people catch Fever")
public class InsulinAntibioticRule {

    @org.jeasy.rules.annotation.Condition
    public boolean canPatientGetFever(@Fact("patient") Patient patient, @Fact("cures") List<Cure> cures) {
        return cures.contains(Insulin) &&
                cures.contains(Antibiotic) &&
                patient.isHealthy();
    }

    @Action
    public void fever(@Fact("patient") Patient patient) {
        patient.setCondition(Fever);
    }
}
