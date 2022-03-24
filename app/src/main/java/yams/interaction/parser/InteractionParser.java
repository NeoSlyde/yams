package yams.interaction.parser;

import java.util.Arrays;
import java.util.Optional;

import yams.interaction.Interaction;
import yams.interaction.req.PubReq;
import yams.interaction.req.RcvIdsReq;
import yams.interaction.req.RcvMsgReq;
import yams.interaction.req.ReplyReq;
import yams.interaction.req.RepubReq;
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
            var argParser = new ArgumentParser(headerParts);

            switch (cmd) {
                // RES
                case "OK":
                    return new OkRes();
                case "ERROR":
                    return parseErrorRes(body);
                case "MSG":
                    String[] bodyLines = body.split("\n");
                    return parseMsgRes(
                            bodyLines[0].split(" "),
                            Arrays.stream(bodyLines).skip(1).reduce((a, b) -> a + '\n' + b).orElse(""));
                case "MSG_IDS":
                    return parseMsgIdsRes(body);

                // REQ
                case "PUBLISH":
                    return parsePublishReq(argParser, body);
                case "RCV_IDS":
                    return parseRcvIdsReq(argParser, body);
                case "RCV_MSG":
                    return parseRcvMsgReq(argParser, body);
                case "REPLY":
                    return parseReplyReq(argParser, body);
                case "REPUBLISH":
                    return parseRepubReq(argParser, body);
            }
        } catch (IndexOutOfBoundsException e) {
        }
        throw new ErrRes.BadReqFormat();
    }

    private MsgIdsRes parseMsgIdsRes(String body) throws ErrRes {
        if (body.trim().isBlank())
            return new MsgIdsRes(new long[0]);
        long[] ids = Arrays.stream(body.trim().split("\n")).mapToLong(Long::parseLong).toArray();
        return new MsgIdsRes(ids);
    }

    private ErrRes parseErrorRes(String body) throws ErrRes {
        switch (body) {
            case "bad request format":
                return new ErrRes.BadReqFormat();
            case "unknown message id":
                return new ErrRes.UnknownMsgId();
            default:
                throw new ErrRes.BadReqFormat();
        }
    }

    private MsgRes parseMsgRes(String[] bodyl1Parts, String bodyRest) throws ErrRes {
        var bodyArgParser = new ArgumentParser(bodyl1Parts);
        String author = parseUser(bodyArgParser.getRequired("author"));
        long id = Long.parseLong(bodyArgParser.getRequired("msg_id"));
        Optional<Long> replyToId = bodyArgParser.get("reply_to_id").map(Long::parseLong);
        boolean republished = bodyArgParser.get("republished").map(Boolean::parseBoolean).orElse(false);

        return new MsgRes(new Msg(id, author, replyToId, republished, bodyRest));
    }

    private PubReq parsePublishReq(ArgumentParser argParser, String body) throws ErrRes {
        String val = argParser.getRequired("author");
        String author = val.substring(1);
        return new PubReq(db, author, body);
    }

    private RcvIdsReq parseRcvIdsReq(ArgumentParser argParser, String body) throws ErrRes {
        Optional<String> author = argParser.get("author");
        Optional<String> tag = argParser.get("tag");
        Optional<Long> sinceId = argParser.get("since_id").map(Long::parseLong);
        Optional<Integer> limit = argParser.get("limit").map(Integer::parseInt);

        // Can't use map because of exceptions. Gotta love java
        if (author.isPresent())
            author = Optional.of(parseUser(author.get()));
        if (tag.isPresent())
            tag = Optional.of(parseTag(tag.get()));

        return new RcvIdsReq(db, author, tag, sinceId, limit);
    }

    private RcvMsgReq parseRcvMsgReq(ArgumentParser argParser, String body) throws ErrRes {
        long id = Long.parseLong(argParser.getRequired("msg_id"));
        return new RcvMsgReq(db, id);
    }

    private ReplyReq parseReplyReq(ArgumentParser argParser, String body) throws ErrRes {
        String author = parseUser(argParser.getRequired("author"));
        long replyToId = Long.parseLong(argParser.getRequired("reply_to_id"));
        return new ReplyReq(null, author, replyToId, body);
    }

    private RepubReq parseRepubReq(ArgumentParser argParser, String body) throws ErrRes {
        String author = parseUser(argParser.getRequired("author"));
        long republishId = Long.parseLong(argParser.getRequired("msg_id"));
        return new RepubReq(null, author, republishId, body);
    }

    private String parseUser(String rawUser) throws ErrRes.BadReqFormat {
        if (rawUser.charAt(0) != '@')
            throw new ErrRes.BadReqFormat();
        return rawUser.substring(1);
    }

    private String parseTag(String rawTag) throws ErrRes.BadReqFormat {
        if (rawTag.charAt(0) != '#')
            throw new ErrRes.BadReqFormat();
        return rawTag.substring(1);
    }
}
