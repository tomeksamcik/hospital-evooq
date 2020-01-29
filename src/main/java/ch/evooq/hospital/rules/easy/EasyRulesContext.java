package ch.evooq.hospital.rules.easy;

import ch.evooq.hospital.model.Cure;
import ch.evooq.hospital.model.Patient;
import ch.evooq.hospital.rules.RulesContext;
import java.util.List;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

public class EasyRulesContext implements RulesContext {

    private final Rules rules = new Rules();

    private final RulesEngine rulesEngine = new DefaultRulesEngine();

    public EasyRulesContext() {
        registerRules();
    }

    public void applyRules(Patient p, List<Cure> cures) {
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
