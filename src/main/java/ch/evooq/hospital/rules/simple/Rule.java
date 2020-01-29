package ch.evooq.hospital.rules.simple;

import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class Rule {

    private final Patient patient;

    private final List<Cure> cures;

    @Getter
    private final String description;

    private final Consumer<Patient> action;

    private final BiPredicate<Patient, List<Cure>> condition;

    public void apply(Patient patient, List<Cure> cures) {
        if (condition.test(patient, cures)) {
            action.accept(patient);
        }
    }
}
