package yams.interaction.res;

import java.util.Arrays;

public record MsgIdsRes(long... ids) implements Res {
    @Override
    public String serializeHeader() {
        return "MSG_IDS";
    }

    @Override
    public String serializeBody() {
        return Arrays.stream(ids).boxed().map(String::valueOf).reduce("", (a, b) -> a + "\n" + b);
    }
}
