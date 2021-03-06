package ch.evooq.hospital;

import static ch.evooq.hospital.Parser.parseCures;
import static ch.evooq.hospital.Parser.parsePatients;
import static ch.evooq.hospital.Reporter.report;

import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import ch.evooq.hospital.rules.RulesContext;
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

    private final List<Cure> cures;

    private final List<Patient> patients;

    private final RulesContext rulesContext;

    Quarantine(Optional<String> subjects, Optional<String> curesCodes, RulesContext rulesContext)
            throws InvalidInputException {
        this.rulesContext = rulesContext;
        this.patients = parsePatients(subjects);
        this.cures = parseCures(curesCodes);

        if (patients.isEmpty()) {
            throw new InvalidInputException();
        }

        log.debug("Patients: {}, Cures: {}", patients, cures);
    }

    String cure() {
        patients.forEach(p -> rulesContext.applyRules(p, cures));
        return report(patients);
    }
}
