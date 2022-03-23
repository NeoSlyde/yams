package yams.interaction.parser;

import java.util.Arrays;
import java.util.Optional;

import yams.interaction.Interaction;
import yams.interaction.req.PubReq;
import yams.interaction.req.RcvIdsReq;
import yams.interaction.req.RcvMsg;
import yams.interaction.res.ErrRes;
import yams.interaction.res.MsgIdsRes;
import yams.interaction.res.MsgRes;
import yams.interaction.res.OkRes;
import yams.msg.Msg;
import yams.msg.db.MsgDb;

public record InteractionParser(MsgDb db) {
    public Interaction parse(String header, String body) throws ErrRes {
        try {
            String[] headerParts = header.split(" ");
            String cmd = headerParts[0];

            switch (cmd) {
                case "OK":
                    return new OkRes();
                case "PUBLISH":
                    return parsePublish(headerParts, body);
                case "RCV_IDS":
                    return parseRcvIds(headerParts, body);
                case "MSG_IDS":
                    return parseMsgIds(headerParts, body);
                case "ERROR":
                    return parseError(headerParts, body);
                case "RCV_MSG":
                    return parseRcvMsg(headerParts, body);
                case "MSG":
                    String[] bodyLines = body.split("\n");
                    return parseMsgRes(
                            bodyLines[0].split(" "),
                            Arrays.stream(bodyLines).skip(1).reduce((a, b) -> a + b).orElse(""));
            }
        } catch (IndexOutOfBoundsException e) {
        }
        throw new ErrRes.BadReqFormat();
    }

    private PubReq parsePublish(String[] headerParts, String body) throws ErrRes {
        String[] arg1Parts = headerParts[1].split(":");
        if (!arg1Parts[0].equals("author") || arg1Parts[1].charAt(0) != '@')
            throw new ErrRes.BadReqFormat();
        String author = arg1Parts[1].substring(1);
        return new PubReq(db, author, body);
    }

    private RcvIdsReq parseRcvIds(String[] headerParts, String body) throws ErrRes {
        Optional<String> author = Optional.empty();
        Optional<String> tag = Optional.empty();
        Optional<Long> sinceId = Optional.empty();
        Optional<Integer> limit = Optional.empty();
        for (int i = 1; i < headerParts.length; i++) {
            String[] argParts = headerParts[i].split(":");
            if (argParts[0].equals("author")) {
                if (argParts[1].charAt(0) != '@')
                    throw new ErrRes.BadReqFormat();
                author = Optional.of(argParts[1].substring(1));
            } else if (argParts[0].equals("tag")) {
                if (argParts[1].charAt(0) != '#')
                    throw new ErrRes.BadReqFormat();
                tag = Optional.of(argParts[1].substring(1));
            } else if (argParts[0].equals("since_id")) {
                sinceId = Optional.of(Long.parseLong(argParts[1]));
            } else if (argParts[0].equals("limit")) {
                limit = Optional.of(Integer.parseInt(argParts[1]));
            } else {
                throw new ErrRes.BadReqFormat();
            }
        }
        return new RcvIdsReq(db, author, tag, sinceId, limit);
    }

    private MsgIdsRes parseMsgIds(String[] headerParts, String body) throws ErrRes {
        if (body.trim().isBlank())
            return new MsgIdsRes(new long[0]);
        return new MsgIdsRes(Arrays.stream(body.trim().split("\n")).mapToLong(Long::parseLong).toArray());
    }

    private ErrRes parseError(String[] headerParts, String body) throws ErrRes {
        switch (body) {
            case "bad request format":
                return new ErrRes.BadReqFormat();
            case "unknown message id":
                return new ErrRes.UnknownMsgId();
            default:
                throw new ErrRes.BadReqFormat();
        }
    }

    private RcvMsg parseRcvMsg(String[] headerParts, String body) throws ErrRes {
        String[] arg1Parts = headerParts[1].split(":");
        if (!arg1Parts[0].equals("msg_id"))
            throw new ErrRes.BadReqFormat();
        long id = Long.parseLong(arg1Parts[1]);
        return new RcvMsg(db, id);
    }

    private MsgRes parseMsgRes(String[] bodyl1Parts, String bodyRest) throws ErrRes {
        String author = null;
        long id = -1;
        Optional<Long> replyToId = Optional.empty();
        boolean republished = false;
        for (int i = 0; i < bodyl1Parts.length; i++) {
            String[] argParts = bodyl1Parts[i].split(":");
            if (argParts[0].equals("author")) {
                if (argParts[1].charAt(0) != '@')
                    throw new ErrRes.BadReqFormat();
                author = argParts[1].substring(1);
            } else if (argParts[0].equals("msg_id")) {
                id = Long.parseLong(argParts[1]);
            } else if (argParts[0].equals("reply_to_id")) {
                replyToId = Optional.of(Long.parseLong(argParts[1]));
            } else if (argParts[0].equals("republished")) {
                republished = Boolean.parseBoolean(argParts[1]);
            } else {
                throw new ErrRes.BadReqFormat();
            }
        }

        if (author == null || id == -1)
            throw new ErrRes.BadReqFormat();
        return new MsgRes(new Msg(id, author, replyToId, republished, bodyRest));
    }
}
