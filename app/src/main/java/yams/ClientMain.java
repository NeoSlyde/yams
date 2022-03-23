package yams;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    public static void run(String host, int port) {
        try {
            var stdinScanner = new Scanner(System.in);
            var socket = new Socket(host, port);

            String str;
            while ((str = stdinScanner.nextLine()) != null) {
                for (char c : str.toCharArray())
                    System.out.print((int) (c) + " ");
                System.out.println();

                socket.getOutputStream().write(str.getBytes());
                socket.getOutputStream().flush();
            }

            socket.close();
            stdinScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
