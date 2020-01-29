package ch.evooq.hospital.rules.simple;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Builder
@NoArgsConstructor
public class Rules {

    @Getter @Singular
    private List<Rule> rules = new ArrayList<>();
}
