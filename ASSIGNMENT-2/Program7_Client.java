// TCP Echo Client

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Program7_Client{

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 5000);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner sc = new Scanner(System.in);

            System.out.print("Enter message: ");
            String msg = sc.nextLine();

            out.println(msg);

            String response = in.readLine();
            System.out.println("Server Response: " + response);

            socket.close();
            sc.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
