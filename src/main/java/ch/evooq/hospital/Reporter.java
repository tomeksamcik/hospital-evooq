package ch.evooq.hospital;

import static java.util.stream.Collectors.groupingBy;

import ch.evooq.hospital.model.Condition;
import ch.evooq.hospital.model.Patient;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Reporter {

    static String report(List<Patient> patients) {
        Map<String, Long> output = new LinkedHashMap();
        Arrays.stream(Condition.values())
                .forEach((Condition p) -> output.put(p.getCode(), 0l));
        patients.stream()
                .collect(groupingBy(p -> p.getCondition().getCode(),
                        Collectors.counting()))
                .forEach((k, v) -> output.merge(k, v, (v1, v2) -> v2));
        return String.join(",", output.entrySet().stream()
                .map(e -> String.join(":", e.getKey(), e.getValue().toString()))
                .collect(Collectors.toList()));
    }
}
