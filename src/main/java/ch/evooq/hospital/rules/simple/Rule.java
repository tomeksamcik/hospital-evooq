package ch.evooq.hospital.rules.simple;

import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@RequiredArgsConstructor
public class Rule {

    @Getter
    private final String description;

    private final Consumer<Patient> then;

    private final BiPredicate<Patient, List<Cure>> when;

    public void apply(Patient patient, List<Cure> cures) {
        if (when.test(patient, cures)) {
            log.debug("Applying rule: {} on {} and {}", description, patient, cures);
            if (then != null) {
                then.accept(patient);
                log.debug("Rule outcome: {}", patient);
            }
        }
    }
}
