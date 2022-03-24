package yams.interaction.res;

import java.net.Socket;

import yams.interaction.Interaction;

public interface Res extends Interaction {
    String serializeHeader();

    String serializeBody();

    @Override
    default void receive(Socket socket) {
        // ???????
    }
}
