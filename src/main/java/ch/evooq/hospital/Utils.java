package ch.evooq.hospital;

import java.util.Optional;

public class Utils {

    /*
    Not Java convention, but Emulating Scala
     */
    public static <T> Optional<T> Some(T some) {
        return Optional.of(some);
    }

    public static Optional None() {
        return Optional.empty();
    }

}
