
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket; 

public class ChatClient {
    private static final String SERVER_IP = "127.0.0.1"; 
    private static final int SERVER_PORT = 12345; 

    public static void main(String[] args) {
        System.out.println("Connecting to server at " + SERVER_IP + ":" + SERVER_PORT + "...");
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) { 
            System.out.println("Connected to the chat server.");

           
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

            String serverMessage;
            String clientMessage;

            System.out.println("Type 'bye' to exit the chat.");

            while (true) {
                
                if (consoleIn.ready()) { 
                    System.out.print("Client: ");
                    clientMessage = consoleIn.readLine();
                    out.println(clientMessage);
                    if (clientMessage.equalsIgnoreCase("bye")) {
                        System.out.println("Client ending chat.");
                        break;
                    }
                }

                
                if (in.ready()) { 
                    serverMessage = in.readLine();
                    if (serverMessage == null) { 
                        System.out.println("Server disconnected.");
                        break;
                    }
                    System.out.println("Server: " + serverMessage);
                    if (serverMessage.equalsIgnoreCase("bye")) {
                        System.out.println("Server requested to end chat. Client ending...");
                        break;
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Client closed.");
        }
    }
}