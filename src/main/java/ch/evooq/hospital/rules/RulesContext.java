package ch.evooq.hospital.rules;

import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import java.util.List;

public interface RulesContext {

    void applyRules(Patient p, List<Cure> cures);
}
