package yams.interaction.res;

import java.io.OutputStream;

import yams.interaction.Interaction;

public interface Res extends Interaction {
    String serializeHeader();

    String serializeBody();

    @Override
    default void receive(OutputStream out) {
        // ???????
    }
}
