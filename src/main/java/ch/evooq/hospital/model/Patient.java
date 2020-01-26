package ch.evooq.hospital.model;

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
}
