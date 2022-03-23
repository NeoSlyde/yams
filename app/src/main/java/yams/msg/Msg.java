package yams.msg;

import java.util.Optional;
import java.util.stream.Stream;

public record Msg(
        long id,
        String user,
        Optional<Long> replyToId,
        boolean republished,
        String msg) {
    public boolean hasTag(String tag) {
        return Stream.of(msg.split(" ")).anyMatch(predicate -> predicate.equals("#" + tag));
    }
}
