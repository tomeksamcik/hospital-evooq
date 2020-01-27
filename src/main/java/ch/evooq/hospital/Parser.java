package ch.evooq.hospital;

import ch.evooq.hospital.model.Condition;
import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Parser {

    public static List<Patient> parsePatients(Optional<String> subjects) {
        List<Patient> patients = new ArrayList<>();
        Arrays.stream(subjects.orElse("").split(","))
                .forEach(p -> Condition.get(p).ifPresent(condition -> patients.add(new Patient(condition))));
        return patients;
    }

    public static List<Cure> parseCures(Optional<String> curesCodes) {
        List<Cure> cures = new ArrayList<>();
        Arrays.stream(curesCodes.orElse("").split(","))
                .forEach(c -> Cure.get(c).ifPresent(cure -> cures.add(cure)));
        return cures;
    }

}
