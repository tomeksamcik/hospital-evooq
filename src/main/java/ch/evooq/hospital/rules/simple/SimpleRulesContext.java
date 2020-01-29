package ch.evooq.hospital.rules.simple;

import static ch.evooq.hospital.model.Condition.Dead;
import static ch.evooq.hospital.model.Condition.Diabetes;
import static ch.evooq.hospital.model.Condition.Fever;
import static ch.evooq.hospital.model.Condition.Healthy;
import static ch.evooq.hospital.model.Condition.Tuberculosis;
import static ch.evooq.hospital.model.Cure.Antibiotic;
import static ch.evooq.hospital.model.Cure.Aspirin;
import static ch.evooq.hospital.model.Cure.Insulin;
import static ch.evooq.hospital.model.Cure.Paracetamol;
import static ch.evooq.hospital.rules.simple.FlyingSpaghettiMonsterRule.miracleHappens;

import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import ch.evooq.hospital.rules.RulesContext;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SimpleRulesContext implements RulesContext {

    private Rules rules;

    public SimpleRulesContext() {
        registerRules();
    }

    public void applyRules(Patient patient, List<Cure> cures) {
        rules.getRules().forEach(rule -> {
            log.debug("Applying rule: {}", rule.getDescription());
            rule.apply(patient, cures);
        });
    }

    private void registerRules() {
        rules = Rules.builder()
            .rule(Rule.builder()
                .description("Aspirin cures Fever")
                .when((patient, cures) -> cures.contains(Aspirin) && patient.has(Fever))
                .then(patient -> patient.setCondition(Healthy))
                .build())
            .rule(Rule.builder()
                .description("Antibiotic cures Tuberculosis")
                .when((patient, cures) -> cures.contains(Antibiotic) && patient.has(Tuberculosis))
                .then(patient -> patient.setCondition(Healthy))
                .build())
            .rule(Rule.builder()
                .description("Insulin prevents diabetic subject from dying, does not cure Diabetes")
                .when((patient, cures) -> cures.contains(Insulin) && patient.has(Diabetes))
                .then(patient -> patient.setCondition(Diabetes))
                .build())
            .rule(Rule.builder()
                .description("Patient with Diabetes dies without Insuline")
                .when((patient, cures) -> !cures.contains(Insulin) && patient.has(Diabetes))
                .then(patient -> patient.setCondition(Dead))
                .build())
            .rule(Rule.builder()
                .description("If insulin is mixed with antibiotic, healthy people catch Fever")
                .when((patient, cures) -> cures.contains(Insulin) && cures.contains(Antibiotic) && patient.isHealthy())
                .then(patient -> patient.setCondition(Fever))
                .build())
            .rule(Rule.builder()
                .description("Paracetamol cures Fever")
                .when((patient, cures) -> cures.contains(Paracetamol) && patient.has(Fever))
                .then(patient -> patient.setCondition(Healthy))
                .build())
            .rule(Rule.builder()
                .description("Paracetamol kills subject if mixed with aspirin")
                .when((patient, cures) -> cures.contains(Paracetamol) && cures.contains(Aspirin))
                .then(patient -> patient.setCondition(Dead))
                .build())
            .rule(Rule.builder()
                .description("Spaghetti Monster revives dead man")
                .when((patient, cures) -> patient.isDead() && miracleHappens())
                .then(patient -> patient.setCondition(Healthy))
                .build())
            .build();
    }
}
