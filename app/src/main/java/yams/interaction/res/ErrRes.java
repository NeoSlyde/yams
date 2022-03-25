package yams.interaction.res;

public abstract class ErrRes extends Exception implements Res {
    @Override
    public String serializeHeader() {
        return "ERROR";
    }

    @Override
    public String getMessage() {
        return this.serializeBody();
    }

    public static class BadReqFormat extends ErrRes {
        @Override
        public String serializeBody() {
            return "bad request format";
        }
    }

    public static class UnknownMsgId extends ErrRes {
        @Override
        public String serializeBody() {
            return "unknown message id";
        }
    }

    public static class UserNotFound extends ErrRes {
        @Override
        public String serializeBody() {
            return "user not found";
        }
    }

    public static class NotSubscribed extends ErrRes {
        @Override
        public String serializeBody() {
            return "not subscribed";
        }
    }

}
