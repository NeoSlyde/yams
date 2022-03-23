package yams.interaction.parser;

import yams.interaction.Interaction;
import yams.interaction.req.PubReq;
import yams.interaction.res.ErrRes;
import yams.interaction.res.OkRes;
import yams.msg.db.MsgDb;

public record InteractionParser(MsgDb db) {
    public Interaction parse(String header, String body) throws ErrRes {
        try {
            String[] headerParts = header.split(" ");
            String cmd = headerParts[0];

            if (cmd.equals("PUBLISH")) {
                String[] arg1Parts = headerParts[1].split(":");
                if (!arg1Parts[0].equals("author") || arg1Parts[1].charAt(0) != '@')
                    throw new ErrRes.BadReqFormat();
                String author = arg1Parts[1].substring(1);
                return new PubReq(db, author, body);
            }

            if (cmd.equals("OK")) {
                return new OkRes();
            }

            if (cmd.equals("ERROR")) {
                switch (body) {
                    case "bad request format":
                        return new ErrRes.BadReqFormat();
                    case "unknown message id":
                        return new ErrRes.UnknownMsgId();
                    default:
                        throw new ErrRes.BadReqFormat();
                }
            }
        } catch (IndexOutOfBoundsException e) {
        }
        throw new ErrRes.BadReqFormat();
    }
}
