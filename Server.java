
// import java.io.FileInputStream
// import java.io.FileOutputStream
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
// import message.Message;
import message.NodeInfo;

// TODO: Add summary with Authors
public class Server  {

  static List<NodeInfo> users = new ArrayList<NodeInfo>();

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // Prompt user for desired port
      // Add checks to make sure port is a proper port number
    System.out.println("Enter desired port number: ");
    int port = scanner.nextInt();

    // Start Server and listen for traffic
    try(ServerSocket socket = new ServerSocket(port)) {
      System.out.println("Server listening on port: " + port);
      Socket connection = socket.accept();

    } catch(Exception e) {
      System.out.println("Something went wrong: " + e);
    }
  }

  // May remove if server is started in main
  private static void startServer() {
    // TODO
  }

  private static void stopServer() {
    // TODO
  }

  // Sends message to all clients
  private static void sendMessageToClients() {
    // TODO
  }

}
