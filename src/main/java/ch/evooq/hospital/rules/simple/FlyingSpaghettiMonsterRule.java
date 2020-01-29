package ch.evooq.hospital.rules.simple;

import java.util.Random;

public class FlyingSpaghettiMonsterRule {

    public static final int RESURRECTION_CHANCE_ONE_IN = 1_000_000;

    public static boolean miracleHappens() {
        return new Random().nextInt(RESURRECTION_CHANCE_ONE_IN) == 0;
    }
}
