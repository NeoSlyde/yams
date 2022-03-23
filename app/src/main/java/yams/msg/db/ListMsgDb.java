package yams.msg.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import yams.msg.Msg;

public class ListMsgDb implements MsgDb {
    private List<Msg> _dbUnsync = new ArrayList<>();

    @Override
    public void push(String user,
            Optional<Long> replyToId,
            boolean republished,
            String msg) {
        synchronized (_dbUnsync) {
            _dbUnsync.add(new Msg(_dbUnsync.size(), user, replyToId, republished, msg));
        }
    }

    @Override
    public long[] getIds(Optional<String> user, Optional<String> tag, Optional<Long> sinceId, int limit) {
        synchronized (_dbUnsync) {
            return _dbUnsync.stream()
                    .filter(msg -> user.isEmpty() || msg.user().equals(user.get()))
                    .filter(msg -> tag.isEmpty() || msg.hasTag(tag.get()))
                    .filter(msg -> sinceId.isEmpty() || msg.id() > sinceId.get())
                    .mapToLong(Msg::id)
                    .limit(limit)
                    .toArray();
        }
    }

    @Override
    public Optional<Msg> getById(long id) {
        synchronized (_dbUnsync) {
            return _dbUnsync.stream()
                    .filter(msg -> msg.id() == id)
                    .findAny();
        }
    }
}
