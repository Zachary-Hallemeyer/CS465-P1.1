package server;

import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import server.NodeInfo;
import utils.PropertyHandler;

/**
 * Class [Server] is a chat server that accepts chat clients and sends data
 * from client to all other clients. This class makes use of ServerThread.
 * A ServerThread thread is created for each client which accepts all data from
 * its respective client
 *
 * @author Zachary M. Hallemeyer
 */
public class Server  {

  static List<NodeInfo> users = new ArrayList<NodeInfo>();

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Socket clientConnection = null;
    String configFileName = "";
    int port = 0;

    // Get config file from user
    System.out.println("Enter path to config file: ");
    configFileName = scanner.nextLine();

    // Get port from config file
    try {
      // Init PropertyHandler and use it to get port
      PropertyHandler propertyHandler = new PropertyHandler(configFileName);
      port = Integer.parseInt(propertyHandler.getProperty("PORT"));
    }
    catch (Exception e) {
      // Terminate config file and print error
      System.out.println("Config file is not valid: " + e);
      System.out.println("Terminating program...");
      System.exit(0);
    }

    // Start Server and listen for traffic
    try(ServerSocket socket = new ServerSocket(port)) {
      System.out.println("Server listening on port: " + port);

      // Accept Client Requests until server is closed
      while(true) {
        try {
          // Accept client connection
          clientConnection = socket.accept();

          // Create an output stream for client
          ObjectOutputStream clientOutputStream = new ObjectOutputStream(
                                          clientConnection.getOutputStream());

          // Create a new NodeInfo to contain client info and add it to user list
          NodeInfo newClient = new NodeInfo(clientOutputStream);
          users.add(newClient);
          System.out.println("Client Added!");

          // Create thread for handling client input (and sending client input to other clients)
          new ServerThread(clientConnection, newClient).start();

        }
        catch (IOException error) {
          System.out.println("I/O error: " + error);
        }

      }

    } catch(IOException error) {
      System.out.println("I/O error: " + error);
    }
  }
}
