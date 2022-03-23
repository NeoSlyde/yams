package yams.interaction.res;

import java.util.Optional;
import java.util.stream.Stream;

import yams.msg.Msg;

public record MsgRes(Msg msg) implements Res {
    @Override
    public String serializeHeader() {
        return "MSG";
    }

    @Override
    public String serializeBody() {
        String metadata = Stream.of(
                Optional.of("author:@" + msg.user()),
                Optional.of("msg_id:" + msg.id()),
                msg.replyToId().map(b -> "reply_to_id:" + b),
                Optional.of("republished:" + msg.republished()))
                .flatMap(Optional::stream)
                .reduce((a, b) -> a + " " + b).orElse("");
        return metadata + '\n' + msg.msg();
    }
}
