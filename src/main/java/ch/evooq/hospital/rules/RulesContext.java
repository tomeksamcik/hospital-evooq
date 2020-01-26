package ch.evooq.hospital.rules;

import lombok.Getter;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

public class RulesContext {

    @Getter
    private final Rules rules = new Rules();

    @Getter
    private final RulesEngine rulesEngine = new DefaultRulesEngine();

    public RulesContext() {
        registerRules();
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
