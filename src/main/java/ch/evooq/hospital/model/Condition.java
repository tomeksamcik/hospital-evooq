package ch.evooq.hospital.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;

public enum Condition {
    Fever("F"), Healthy("H"), Diabetes("D"), Tuberculosis("T"), Dead("X");

    @Getter
    private String code;

    private static final Map<String, Condition> lookup = new HashMap<>();

    static {
        Arrays.stream(Condition.values()).forEach(c -> lookup.put(c.getCode(), c));
    }

    Condition(String code) {
        this.code = code;
    }

    public static Optional<Condition> get(String code) {
        return Optional.ofNullable(lookup.get(code));
    }
}
