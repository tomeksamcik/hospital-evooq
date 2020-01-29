package ch.evooq.hospital.rules.simple;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Builder
@AllArgsConstructor
public class Rules {

    @Getter @Singular
    private List<Rule> rules;
}
