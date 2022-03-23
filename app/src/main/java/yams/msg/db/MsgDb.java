package yams.msg.db;

import java.util.Optional;

import yams.msg.Msg;

public interface MsgDb {
    void push(String user,
            Optional<Long> replyToId,
            boolean republished,
            String msg);

    default void publish(String user, String msg) {
        push(user, Optional.empty(), false, msg);
    }

    default void reply(String user, long replyToId) {
        if (getById(replyToId).isEmpty())
            throw new IllegalArgumentException("No such message");
        push(user, Optional.of(replyToId), false, "");
    }

    default void republish(String user, long republishId) {
        if (getById(republishId).isEmpty())
            throw new IllegalArgumentException("No such message");
        push(user, Optional.empty(), true, "");
    }

    long[] getIds(
            Optional<String> user,
            Optional<String> tag,
            Optional<Long> sinceId,
            Optional<Integer> limit);

    Optional<Msg> getById(long id);
}
