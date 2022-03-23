package yams.logger;

import java.util.stream.Stream;

public interface Logger {
    void lognln(String str);

    default void log(String str) {
        lognln(str + "\n");
    }

    default void log(Object... xs) {
        log(joinObjects(xs));
    }

    default void lognln(Object... xs) {
        lognln(joinObjects(xs));
    }

    private String joinObjects(Object[] xs) {
        return Stream.of(xs).map(Object::toString).reduce((a, b) -> a + "\n" + b).orElse("");
    }
}
