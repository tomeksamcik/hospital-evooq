package ch.evooq.hospital.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Cure {
    Aspirin("As"), Antibiotic("An"), Insulin("I"), Paracetamol("P");

    @Getter
    private final String code;

    private static final Map<String, Cure> lookup = new HashMap<>();

    static {
        Arrays.stream(Cure.values()).forEach(c -> lookup.put(c.getCode(), c));
    }

    public static Optional<Cure> get(String code) {
        return Optional.ofNullable(lookup.get(code));
    }
}
