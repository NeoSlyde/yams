package yams.interaction.req;

import java.util.Optional;
import java.util.stream.Stream;

import yams.interaction.res.MsgIdsRes;
import yams.interaction.res.Res;
import yams.msg.db.MsgDb;

public record RcvIdsReq(
        MsgDb db,
        Optional<String> author,
        Optional<String> tag,
        Optional<Long> sinceId,
        Optional<Integer> limit)
        implements Req {
    @Override
    public String serializeHeader() {
        return Stream.of(
                Optional.of("RCV_IDS"),
                author.map(s -> "author:@" + s),
                tag.map(s -> "tag:#" + s),
                sinceId.map(s -> "since_id:" + s),
                limit.map(s -> "limit:" + s))
                .flatMap(Optional::stream).reduce((a, b) -> a + " " + b).orElse("");
    }

    @Override
    public String serializeBody() {
        return "";
    }

    @Override
    public Res handle() {
        int limit = limit().orElse(5);
        long[] ids = db.getIds(author, tag, sinceId, limit);
        return new MsgIdsRes(ids);
    }
}
