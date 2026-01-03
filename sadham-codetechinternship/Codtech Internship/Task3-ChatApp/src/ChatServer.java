import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * ChatServer.java
 * ---------------------------------------------------------
 * Multithreaded server that handles multiple chat clients.
 * Broadcasts messages to all connected users.
 * Shows join/leave notifications with usernames.
 * ---------------------------------------------------------
 * CODTECH Java Internship Task-3
 */
public class ChatServer {

    // Thread-safe list to store client output streams
    private static CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        System.out.println("===== CHAT SERVER STARTED =====");

        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server is running on port 5000...\n");

            // Continuously accept client connections
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);
                handler.start(); // Start a new thread for the client
            }

        } catch (IOException e) {
            System.out.println("Server Error: " + e.getMessage());
        }
    }

    /** Broadcasts message to all clients */
    public static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) { // Don't send back to sender
                client.sendMessage(message);
            }
        }
    }

    /** Removes client when disconnected */
    public static void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}

/** Handles individual client connection in a separate thread */
class ClientHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Setup input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // First message from client should be the username
            username = in.readLine();
            System.out.println(username + " joined the chat.");

            // Notify others
            ChatServer.broadcast("🔔 " + username + " joined the chat!", this);

            String message;
            // Read messages from client
            while ((message = in.readLine()) != null) {
                String formatted = username + ": " + message;
                System.out.println(formatted);
                ChatServer.broadcast(formatted, this);
            }

        } catch (IOException e) {
            System.out.println(username + " disconnected.");
        } finally {
            // Cleanup when client leaves
            ChatServer.removeClient(this);
            ChatServer.broadcast("🔕 " + username + " left the chat.", this);
            System.out.println(username + " left the chat.");

            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    /** Sends a message to this client */
    public void sendMessage(String message) {
        out.println(message);
    }
}
