package yams;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import yams.logger.Logger;
import yams.logger.StdOutLogger;

public class ClientMain {
    private static Logger logger = new StdOutLogger();

    public static void main(String args[]) {
        run("localhost", 12345);
    }

    public static void run(String host, int port) {
        try {
            var stdinScanner = new Scanner(System.in);
            var socket = new Socket(host, port);

            logger.log("Started YAMS Client on " + host + ":" + port);

            while (stdinScanner.hasNextLine()) {
                String str = stdinScanner.nextLine();
                socket.getOutputStream().write(str.trim().getBytes());
                socket.getOutputStream().write("\r\n".getBytes());
                socket.getOutputStream().flush();
                logger.log("Sent: " + str);

            }

            socket.close();
            stdinScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
