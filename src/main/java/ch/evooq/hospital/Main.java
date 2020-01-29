package ch.evooq.hospital;

import ch.evooq.hospital.rules.simple.SimpleRulesContext;
import com.jasongoodwin.monads.Try;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Main {

    private final String[] args;

    private static final String ERROR_MESSAGE = "Please provide arguments in format: F:NP,H:NP,D:NP,T:NP,X:NP\n" +
            "Where:\n" +
            "● F, H, D, T, X are patients’ health status codes;\n" +
            "● NP is a number of patients for a given state;";

    public String init() {
        SimpleRulesContext ctx = new SimpleRulesContext();
        Optional<String> cures = parseArgument(2, args);
        Optional<String> subjects = parseArgument(1, args);
        return Try.ofFailable(() -> new Quarantine(subjects, cures, ctx).cure())
                .recover(e -> String.format("%s. %s", e.getMessage(), ERROR_MESSAGE));
    }

    private Optional<String> parseArgument(int idx, String... args) {
        return Try.ofFailable(() -> args[idx]).toOptional();
    }

    public static void main(String... args) {
        System.out.println(new Main(args).init());
    }
}
