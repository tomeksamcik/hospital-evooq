package ch.evooq.hospital.model;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Cure {
    Aspirin("As"), Antibiotic("An"), Insulin("I"), Paracetamol("P");

    @Getter
    private final String code;

    private static final Map<String, Cure> lookup = Arrays.stream(Cure.values())
            .collect(Collectors.toMap(c -> c.getCode(), c -> c));

    public static Optional<Cure> get(String code) {
        return Optional.ofNullable(lookup.get(code));
    }
}
