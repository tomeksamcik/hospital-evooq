package ch.evooq.hospital;

import ch.evooq.hospital.rules.RulesContext;
import com.jasongoodwin.monads.Try;
import java.util.Optional;

public class Main {

    private static final String ERROR_MESSAGE = "Please provide arguments in format: F:NP,H:NP,D:NP,T:NP,X:NP\n" +
            "Where:\n" +
            "● F, H, D, T, X are patients’ health status codes;\n" +
            "● NP is a number of patients for a given state;";

    public Main(String... args) {
        RulesContext ctx = new RulesContext();
        Optional<String> cures = parseArgument(2, args);
        Optional<String> subjects = parseArgument(1, args);
        String out = Try.ofFailable(() -> {
            Quarantine quarantine = new Quarantine(subjects, cures, ctx.getRulesEngine(), ctx.getRules());
            return quarantine.cure();
        }).recover(e -> ERROR_MESSAGE);
        System.out.println(out);
    }

    private Optional<String> parseArgument(int idx, String... args) {
        return Try.ofFailable(() -> args[idx]).toOptional();
    }

    public static void main(String... args) {
        new Main(args);
    }
}
