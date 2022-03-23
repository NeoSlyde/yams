package yams;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.google.common.primitives.Longs;

import yams.interaction.parser.InteractionParser;
import yams.interaction.req.RcvIdsReq;
import yams.interaction.req.RcvMsg;
import yams.interaction.res.ErrRes;
import yams.interaction.res.MsgIdsRes;
import yams.interaction.res.MsgRes;
import yams.msg.Msg;

public class FollowerClient {
    public static void run(String host, int port) {
        try {
            var stdinScanner = new Scanner(System.in);
            var socket = new Socket(host, port);
            var out = socket.getOutputStream();
            var in = new Scanner(socket.getInputStream()).useDelimiter("\\r\\n");

            System.out.println("Started YAMS Client on " + host + ":" + port);

            System.out.print("Enter a space separated list of users to follow: ");
            String[] users = stdinScanner.nextLine().split(" ");
            var parser = new InteractionParser(null);

            // Get IDs

            List<Long> ids = new ArrayList<>();
            for (String user : users) {
                var req = new RcvIdsReq(null, Optional.of(user), Optional.empty(), Optional.empty(), Optional.empty());
                req.sendTo(out);
                var header = in.next().trim();
                var body = in.next().trim();
                try {
                    var res = parser.parse(header, body);
                    if (!(res instanceof MsgIdsRes))
                        throw new RuntimeException("Expected MsgIdsRes");
                    ids.addAll(Longs.asList(((MsgIdsRes) res).ids()));
                } catch (ErrRes e) {
                    System.out.println("ERROR! " + e.getMessage());
                }
            }
            Collections.sort(ids);

            // Get Messages

            List<Msg> msgs = new ArrayList<>();
            for (long id : ids) {
                var req = new RcvMsg(null, id);
                req.sendTo(out);
                var header = in.next().trim();
                var body = in.next().trim();
                try {
                    var res = parser.parse(header, body);
                    if (!(res instanceof MsgRes))
                        throw new RuntimeException("Expected MsgRes");
                    msgs.add(((MsgRes) res).msg());
                } catch (ErrRes e) {
                    System.out.println("ERROR! " + e.getMessage());
                }
            }

            // Print Messages

            for (Msg msg : msgs) {
                if (msg.republished())
                    System.out.print("[republished] ");
                if (msg.replyToId().isPresent())
                    System.out.print("[reply to " + msg.replyToId().get() + "] ");
                System.out.println(msg.user() + ": " + msg.msg());
            }

            socket.close();
            stdinScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
