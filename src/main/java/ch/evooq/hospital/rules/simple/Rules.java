package ch.evooq.hospital.rules.simple;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Rules {

    @Getter
    private final List<Rule> rules = new ArrayList<>();

    public Rules add(Rule rule) {
        rules.add(rule);
        return this;
    }
}
