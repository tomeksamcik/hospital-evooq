package ch.evooq.hospital.rules.simple;

import static ch.evooq.hospital.Utils.Some;
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


public class SimpleRulesContext implements RulesContext {

    private Rules rules;

    public SimpleRulesContext() {
        registerRules();
    }

    public void applyRules(Patient patient, List<Cure> cures) {
        rules.getRules().forEach(rule -> rule.apply(patient, cures));
    }

    private void registerRules() {
        rules = Rules.builder()
                .rule(Rule.when((patient, cures) -> cures.contains(Aspirin) && patient.has(Fever))
                        .then(Some(patient -> patient.willBe(Healthy)))
                        .description("Aspirin cures Fever").build())
                .rule(Rule.when((patient, cures) -> cures.contains(Antibiotic) && patient.has(Tuberculosis))
                        .then(Some(patient -> patient.willBe(Healthy)))
                        .description("Antibiotic cures Tuberculosis").build())
                .rule(Rule.when((patient, cures) -> cures.contains(Insulin) && patient.has(Diabetes))
                        .then(Some(patient -> patient.willHave(Diabetes)))
                        .description("Insulin prevents diabetic subject from dying, does not cure Diabetes").build())
                .rule(Rule.when((patient, cures) -> !cures.contains(Insulin) && patient.has(Diabetes))
                        .then(Some(patient -> patient.willBe(Dead)))
                        .description("Patient with Diabetes dies without Insulin").build())
                .rule(Rule.when((patient, cures) -> cures.contains(Insulin) && cures.contains(Antibiotic) && patient.isHealthy())
                        .then(Some(patient -> patient.willHave(Fever)))
                        .description("If insulin is mixed with antibiotic, healthy people catch Fever").build())
                .rule(Rule.when((patient, cures) -> cures.contains(Paracetamol) && patient.has(Fever))
                        .then(Some(patient -> patient.willBe(Healthy)))
                        .description("Paracetamol cures Fever").build())
                .rule(Rule.when((patient, cures) -> cures.contains(Paracetamol) && cures.contains(Aspirin))
                        .then(Some(patient -> patient.willBe(Dead)))
                        .description("Paracetamol kills subject if mixed with aspirin").build())
                .rule(Rule.when((patient, cures) -> patient.isDead() && miracleHappens())
                        .then(Some(patient -> patient.willBe(Healthy)))
                        .description("Spaghetti Monster revives dead man").build()).build();
    }
}
