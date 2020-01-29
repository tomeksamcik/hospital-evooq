package ch.evooq.hospital.rules.easy;

import static ch.evooq.hospital.model.Condition.Dead;
import static ch.evooq.hospital.model.Cure.Aspirin;
import static ch.evooq.hospital.model.Cure.Paracetamol;

import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import java.util.List;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

@Rule(description = "Paracetamol kills subject if mixed with aspirin")
public class ParacetamolAspirineRule {

    @org.jeasy.rules.annotation.Condition
    public boolean canPatientDie(@Fact("patient") Patient patient, @Fact("cures") List<Cure> cures) {
        return cures.contains(Paracetamol) &&
                cures.contains(Aspirin);
    }

    @Action
    public void die(@Fact("patient") Patient patient) {
        patient.setCondition(Dead);
    }
}
