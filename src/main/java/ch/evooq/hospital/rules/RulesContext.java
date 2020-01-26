package ch.evooq.hospital.rules;

import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import java.util.List;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

public class RulesContext {

    private final Rules rules = new Rules();

    private final RulesEngine rulesEngine = new DefaultRulesEngine();

    public RulesContext() {
        registerRules();
    }

    public void apply(Patient p, List<Cure> cures) {
        Facts facts = new Facts();
        facts.put("patient", p);
        facts.put("cures", cures);
        rulesEngine.fire(rules, facts);
    }

    private void registerRules() {
        rules.register(new AspirinRule());
        rules.register(new AntibioticRule());
        rules.register(new InsulinRule());
        rules.register(new NoInsulinRule());
        rules.register(new InsulinAntibioticRule());
        rules.register(new ParacetamolRule());
        rules.register(new ParacetamolAspirineRule());
        rules.register(new FlyingSpaghettiMonsterRule());
    }
}
