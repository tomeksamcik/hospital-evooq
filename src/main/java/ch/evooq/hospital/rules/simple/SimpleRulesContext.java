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
        rules = new Rules()
            .add(Rule.builder()
                .description("Aspirin cures Fever")
                .condition((patient, cures) -> cures.contains(Aspirin) && patient.has(Fever))
                .action(patient -> patient.setCondition(Healthy))
                .build())
            .add(Rule.builder()
                .description("Antibiotic cures Tuberculosis")
                .condition((patient, cures) -> cures.contains(Antibiotic) && patient.has(Tuberculosis))
                .action(patient -> patient.setCondition(Healthy))
                .build())
            .add(Rule.builder()
                .description("Insulin prevents diabetic subject from dying, does not cure Diabetes")
                .condition((patient, cures) -> cures.contains(Insulin) && patient.has(Diabetes))
                .action(patient -> patient.setCondition(Diabetes))
                .build())
            .add(Rule.builder()
                .description("Patient with Diabetes dies without Insuline")
                .condition((patient, cures) -> !cures.contains(Insulin) && patient.has(Diabetes))
                .action(patient -> patient.setCondition(Dead))
                .build())
            .add(Rule.builder()
                .description("If insulin is mixed with antibiotic, healthy people catch Fever")
                .condition((patient, cures) -> cures.contains(Insulin) && cures.contains(Antibiotic) && patient.isHealthy())
                .action(patient -> patient.setCondition(Fever))
                .build())
            .add(Rule.builder()
                .description("Paracetamol cures Fever")
                .condition((patient, cures) -> cures.contains(Paracetamol) && patient.has(Fever))
                .action(patient -> patient.setCondition(Healthy))
                .build())
            .add(Rule.builder()
                .description("Paracetamol kills subject if mixed with aspirin")
                .condition((patient, cures) -> cures.contains(Paracetamol) && cures.contains(Aspirin))
                .action(patient -> patient.setCondition(Dead))
                .build())
            .add(Rule.builder()
                .description("Spaghetti Monster revives dead man")
                .condition((patient, cures) -> patient.isDead() && miracleHappens())
                .action(patient -> patient.setCondition(Healthy))
                .build());
    }
}
