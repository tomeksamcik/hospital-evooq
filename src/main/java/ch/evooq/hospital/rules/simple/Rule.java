package ch.evooq.hospital.rules.simple;

import static ch.evooq.hospital.Utils.None;

import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import java.util.List;
import java.util.Optional;
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

    @Builder.Default
    private final Optional<Consumer<Patient>> then = None();

    private final BiPredicate<Patient, List<Cure>> when;

    public static RuleBuilder when(BiPredicate<Patient, List<Cure>> when) {
        return builder().when(when);
    }

    private static RuleBuilder builder() {
        return new RuleBuilder();
    }

    public void apply(Patient patient, List<Cure> cures) {
        if (when.test(patient, cures)) {
            log.debug("Applying rule: {} on {} and {}", description, patient, cures);
            then.ifPresent(t -> {
                t.accept(patient);
                log.debug("Rule outcome: {}", patient);
            });
        }
    }
}
