package ch.evooq.hospital.model;

import static ch.evooq.hospital.model.Condition.Dead;
import static ch.evooq.hospital.model.Condition.Healthy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class Patient {

    @Getter @Setter
    private Condition condition;

    public boolean has(Condition condition) {
        return this.condition == condition;
    }

    public boolean isDead() {
        return condition == Dead;
    }

    public boolean isHealthy() {
        return condition == Healthy;
    }
}
