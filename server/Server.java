package server;

// import java.io.FileInputStream
// import java.io.FileOutputStream
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import server.NodeInfo;
import utils.NetworkUtilities;

// TODO: Add summary with Authors
public class Server  {

  static int clientNum = 0;
  static List<NodeInfo> users = new ArrayList<NodeInfo>();

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Socket clientConnection = null;

    // Prompt user for desired port
      // Add checks to make sure port is a proper port number
    // System.out.println("Enter desired port number: ");
    // int port = scanner.nextInt();
    int port = 8888;

    // Start Server and listen for traffic
    try(ServerSocket socket = new ServerSocket(port)) {
      System.out.println("Server listening on port: " + port);
      // Socket connection = socket.accept();

      // Accept Clients
      while(true) {
        try {
          clientConnection = socket.accept();
          System.out.println("Client Added!");

          // Create thread
          new ServerThread(clientConnection).start();

          // Add client to
        }
        catch (Exception e) {
          System.out.println("Client connection error: " + e);
        }

      }

    } catch(Exception e) {
      System.out.println("Something went wrong: " + e);
    }
  }
}
