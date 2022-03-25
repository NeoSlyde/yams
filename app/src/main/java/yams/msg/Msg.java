package yams.msg;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    public List<String> getTags(){
        return Stream.of(msg.split(" "))
                .filter(predicate -> predicate.startsWith("#"))
                .map(predicate -> predicate.substring(1))
                .collect(Collectors.toList());
    }
}
