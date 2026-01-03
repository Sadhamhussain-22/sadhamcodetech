import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * ChatClient.java
 * -----------------------------------------------------
 * Connects to ChatServer and allows user to send messages.
 * Receives broadcast messages in real time using a thread.
 * -----------------------------------------------------
 * CODTECH Java Internship Task-3
 */
public class ChatClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner sc = new Scanner(System.in);

            // Ask username
            System.out.print("Enter your username: ");
            String username = sc.nextLine();
            out.println(username); // Send to server

            System.out.println("\nYou can start chatting now... Type 'exit' to leave.\n");

            // Thread to listen for messages from server
            Thread listener = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException ignored) {}
            });
            listener.start();

            // Send messages loop
            while (true) {
                String message = sc.nextLine();
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Leaving chat...");
                    socket.close();
                    break;
                }
                out.println(message);
            }

            sc.close();

        } catch (IOException e) {
            System.out.println("Client Error: " + e.getMessage());
        }
    }
}
