package ch.evooq.hospital;

import ch.evooq.hospital.model.Condition;
import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;

class InvalidInputException extends Exception {

    public InvalidInputException() {
        super("Invalid input");
    }
}

@Slf4j
class Quarantine {

    private final Rules rules;

    private final RulesEngine rulesEngine;

    private final List<Cure> cures = new ArrayList<>();

    private final List<Patient> patients = new ArrayList<>();

    Quarantine(Optional<String> subjects, Optional<String> curesCodes, RulesEngine rulesEngine, Rules rules)
            throws InvalidInputException {
        this.rules = rules;
        this.rulesEngine = rulesEngine;

        Arrays.stream(subjects.orElse("").split(","))
                .forEach(p -> Condition.get(p).ifPresent(condition -> patients.add(new Patient(condition))));
        Arrays.stream(curesCodes.orElse("").split(","))
                .forEach(c -> Cure.get(c).ifPresent(cure -> cures.add(cure)));

        if (patients.isEmpty()) {
            throw new InvalidInputException();
        }

        log.debug("Patients: {}, Cures: {}", patients, cures);
    }

    String cure() {
        patients.stream().map(p -> {
            Facts facts = new Facts();
            facts.put("patient", p);
            facts.put("cures", cures);
            return facts;
        }).forEach(facts -> rulesEngine.fire(rules, facts));
        return report();
    }

    String report() {
        return Reporter.report(patients);
    }
}
