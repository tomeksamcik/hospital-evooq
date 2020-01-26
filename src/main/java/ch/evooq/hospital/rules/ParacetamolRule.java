package ch.evooq.hospital.rules;

import ch.evooq.hospital.model.Condition;
import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import java.util.List;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

@Rule(description = "Paracetamol cures Fever")
public class ParacetamolRule {

    @org.jeasy.rules.annotation.Condition
    public boolean canPatientBeCured(@Fact("patient") Patient patient, @Fact("cures") List<Cure> cures) {
        return cures.contains(Cure.Paracetamol) &&
                patient.has(Condition.Fever);
    }

    @Action
    public void cure(@Fact("patient") Patient patient) {
        patient.setCondition(Condition.Healthy);
    }
}
