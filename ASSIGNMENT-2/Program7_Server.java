// TCP Echo Server

import java.io.*;
import java.net.*;

public class Program7_Server {

    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Server started...");

            Socket socket = server.accept();
            System.out.println("Client connected");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String msg = in.readLine();
            System.out.println("Received: " + msg);

            // Echo back
            out.println("Echo: " + msg);

            socket.close();
            server.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}