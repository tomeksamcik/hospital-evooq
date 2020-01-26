package ch.evooq.hospital.rules;

import ch.evooq.hospital.model.Condition;
import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import java.util.List;
import java.util.Random;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

@Rule(description = "Spaghetti Monster revives dead man")
public class FlyingSpaghettiMonsterRule {

    public static final int RESURRECTION_CHANCE_ONE_IN = 100_000;

    @org.jeasy.rules.annotation.Condition
    public boolean canPatientBeResurrected(@Fact("patient") Patient patient, @Fact("cures") List<Cure> cures) {
        return patient.has(Condition.Dead) &&
                miracleHappens();
    }

    @Action
    public void resurrect(@Fact("patient") Patient patient) {
        patient.setCondition(Condition.Healthy);
    }

    private boolean miracleHappens() {
        return new Random().nextInt(RESURRECTION_CHANCE_ONE_IN) == 0;
    }
}
