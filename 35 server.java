
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket; 
import java.net.Socket;

public class ChatServer {
    private static final int PORT = 12345; 

    public static void main(String[] args) {
        System.out.println("Server started. Listening on port " + PORT + "...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) { 
            System.out.println("Waiting for a client to connect...");
            Socket clientSocket = serverSocket.accept(); 
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

           
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 
            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

            String clientMessage;
            String serverMessage;

            while (true) {
                
                if (in.ready()) { 
                    clientMessage = in.readLine();
                    if (clientMessage == null) {
                        System.out.println("Client disconnected.");
                        break;
                    }
                    System.out.println("Client: " + clientMessage);
                    if (clientMessage.equalsIgnoreCase("bye")) {
                        System.out.println("Client requested to end chat. Server ending...");
                        break;
                    }
                }

                
                if (consoleIn.ready()) { 
                    System.out.print("Server: ");
                    serverMessage = consoleIn.readLine();
                    out.println(serverMessage);
                    if (serverMessage.equalsIgnoreCase("bye")) {
                        System.out.println("Server ending chat.");
                        break;
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Server closed.");
        }
    }
}