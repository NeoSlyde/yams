package yams;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    public static void main(String args[]) {
        run("localhost", 12345);
    }

    public static void run(String host, int port) {
        try {
            var stdinScanner = new Scanner(System.in);
            var socket = new Socket(host, port);

            System.out.println("Started YAMS Client on " + host + ":" + port);

            while (stdinScanner.hasNextLine()) {
                String str = stdinScanner.nextLine();
                socket.getOutputStream().write(str.trim().getBytes());
                socket.getOutputStream().write("\r\n".getBytes());
                socket.getOutputStream().flush();
                System.out.println("Sent: " + str);

            }

            socket.close();
            stdinScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
