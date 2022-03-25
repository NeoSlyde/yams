package yams.interaction.flux;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import yams.interaction.res.MsgRes;
import yams.msg.Msg;

public class FluxDb {
    private Map<String, List<Socket>> userSubscribers = new HashMap<>();
    private Map<String, List<Socket>> tagSubscribers = new HashMap<>();

    public void subscribeToUser(String user, Socket socket) {
        userSubscribers.computeIfAbsent(user, k -> new java.util.ArrayList<>()).add(socket);
    }

    public void subscribeToTag(String tag, Socket socket) {
        tagSubscribers.computeIfAbsent(tag, k -> new java.util.ArrayList<>()).add(socket);
    }

    public void unsubscribeFromUser(String user, Socket socket) {
        userSubscribers.computeIfPresent(user, (k, v) -> {
            v.remove(socket);
            return v;
        });
    }

    public void unsubscribeFromTag(String tag, Socket socket) {
        tagSubscribers.computeIfPresent(tag, (k, v) -> {
            v.remove(socket);
            return v;
        });
    }

    public void notify(Msg message){
        var sockets = new HashSet<>(userSubscribers.getOrDefault(message.user(), new java.util.ArrayList<>()));

        for(String tag : message.getTags()){
            sockets.addAll(tagSubscribers.getOrDefault(tag, new java.util.ArrayList<>()));
        }

        sockets.forEach(socket -> {
            try {
                var req = new MsgRes(message);
                req.sendTo(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
