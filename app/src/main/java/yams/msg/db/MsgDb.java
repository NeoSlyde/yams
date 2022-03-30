package yams.msg.db;

import java.util.Optional;

import yams.interaction.res.ErrRes;
import yams.msg.Msg;

public interface MsgDb {
    void push(String user,
            Optional<Long> replyToId,
            boolean republished,
            String msg);

    default void publish(String user, String msg) {
        push(user, Optional.empty(), false, msg);
    }

    default void reply(String user, long replyToId, String msg) throws ErrRes.UnknownMsgId {
        if (getById(replyToId).isEmpty())
            throw new ErrRes.UnknownMsgId();
        push(user, Optional.of(replyToId), false, "Reply to msgId " + replyToId + " : "+ msg);
    }

    default void republish(String user, long republishId) throws ErrRes.UnknownMsgId {
        Optional<Msg> msg = getById(republishId);
        if (msg.isEmpty())
            throw new ErrRes.UnknownMsgId();
        push(user, Optional.empty(), true, msg.get().msg());
    }

    long[] getIds(
            Optional<String> user,
            Optional<String> tag,
            Optional<Long> sinceId,
            int limit);

    Optional<Msg> getById(long id);

    boolean userExists(String user);
}
