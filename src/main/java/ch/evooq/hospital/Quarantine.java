package ch.evooq.hospital;

import ch.evooq.hospital.model.Condition;
import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import ch.evooq.hospital.rules.RulesContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

class InvalidInputException extends Exception {

    public InvalidInputException() {
        super("Invalid input");
    }
}

@Slf4j
class Quarantine {

    private final RulesContext rulesContext;

    private final List<Cure> cures = new ArrayList<>();

    private final List<Patient> patients = new ArrayList<>();

    Quarantine(Optional<String> subjects, Optional<String> curesCodes, RulesContext rulesContext)
            throws InvalidInputException {
        this.rulesContext = rulesContext;

        parsePatients(subjects);
        parseCures(curesCodes);

        if (patients.isEmpty()) {
            throw new InvalidInputException();
        }

        log.debug("Patients: {}, Cures: {}", patients, cures);
    }

    private void parsePatients(Optional<String> subjects) {
        Arrays.stream(subjects.orElse("").split(","))
                .forEach(p -> Condition.get(p).ifPresent(condition -> patients.add(new Patient(condition))));
    }

    private void parseCures(Optional<String> curesCodes) {
        Arrays.stream(curesCodes.orElse("").split(","))
                .forEach(c -> Cure.get(c).ifPresent(cure -> cures.add(cure)));
    }

    String cure() {
        patients.forEach(p -> rulesContext.applyRules(p, cures));
        return report();
    }

    String report() {
        return Reporter.report(patients);
    }
}
