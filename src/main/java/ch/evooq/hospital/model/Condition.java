package ch.evooq.hospital.model;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Condition {
    Fever("F"), Healthy("H"), Diabetes("D"), Tuberculosis("T"), Dead("X");

    @Getter
    private final String code;

    private static final Map<String, Condition> lookup = Arrays.stream(Condition.values())
            .collect(Collectors.toMap(c -> c.getCode(), c -> c));

    public static Optional<Condition> get(String code) {
        return Optional.ofNullable(lookup.get(code));
    }
}
