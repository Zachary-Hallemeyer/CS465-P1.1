package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.net.ServerSocket;
import java.net.Socket;
import message.Message;
import message.MessageTypes;
import utils.PropertyHandler;


/**
 * Class [Client] allows for the user to connect to a chat server and chat with
 * other clients. It does this by prompting user for their name as well as which
 * chat server that they would like to join (ip and port number). The program
 * then creates a socket as well as server socket to communicate continuously
 * with server. This class makes use of ClientSender class as a thread to send
 * data to a chat server.
 *
 * @author Zachary M. Hallemeyer
 */
public class Client {

  public static void main(String[] args) {
    // Init. variables
    Scanner scanner = new Scanner(System.in);
    String[] stringArray;
    String ip = "";
    String userInput = "";
    String name = "";
    String configFileName = "";
    int port = 0;
    boolean useConfig;


    // Prompt user for Name
    System.out.println("Enter your name");
    name = scanner.nextLine();

    // Print join options and get options
    System.out.println("Enter 1 for use join information in config file"
                       + "\nEnter 2 to input join information in command line");
    if(scanner.nextLine().equals("2")) {
      useConfig = false;
    }
    else {
      useConfig = true;
    }


    // Get join information from config
    if(useConfig) {

      try {
        // Get path config
        System.out.println("Enter path to config file: ");
        configFileName = scanner.nextLine();

        // Init PropertyHandler and get ip and port
        PropertyHandler propertyHandler = new PropertyHandler(configFileName);
        ip = propertyHandler.getProperty("IP");
        port = Integer.parseInt(propertyHandler.getProperty("PORT"));
      }
      catch(Exception error) {
        // Stop program
        System.out.println("Config file is not valid: " + error);
        stopProgram();
      }
    }
    // Get join information from command line
    else {
      // Prompt user for command, ip, and port
        // Repeat until join message is valid
      do {
        System.out.println("To enter a chat - Input the following command: "
        + "JOIN <Host IP> <Host Port>");
        userInput = scanner.nextLine();
      }
      while(isJoinMessageValid(userInput));

      // Convert string to useable array
      stringArray = userInput.split(" ");
      // Get ip
      ip = stringArray[1];
      // Get port
      port = Integer.parseInt(stringArray[2]);
    }

    System.out.println("Requesting to connect to ip " + ip +
                       " on port " + port);

    // Attempt to connect to server
    try {
      // Create Server variables
      ServerSocket clientSocket = new ServerSocket(port);
      Socket serverConnection = new Socket(ip, port);
      Socket connection = null;
      Message messageFromServer = null;

      // Create thread for sending data to server
      new ClientSender(serverConnection, name).start();
      // Create input stream for data from server
      ObjectInputStream inputStream = new ObjectInputStream(
                                            serverConnection.getInputStream());

      // Print join message
      System.out.println("Connected to chat server!");

      // Enter infinite loop until client is stopped by stopProgram
      // Accept all new data from server
      while(true)
      {
        try {
          // Get new message from server
          messageFromServer = (Message) inputStream.readObject();

          // Check if message did not came from this client
          if(!messageFromServer.getContent().toString().split(": ")[0].contains(name)) {
            // Print content to console
            System.out.println(messageFromServer.getContent());

            // Check for SHUTDOWN_ALL command
            if(messageFromServer.getType() == MessageTypes.SHUTDOWN_ALL) {
              // Stop program
              stopProgram();
            }
          }
          // Otherwise, check if LEAVE/SHUTDOWN/SHUTDOWN_ALL commands
          else if(messageFromServer.getType() == MessageTypes.LEAVE
                  || messageFromServer.getType() == MessageTypes.SHUTDOWN
                  || messageFromServer.getType() == MessageTypes.SHUTDOWN_ALL){
            // Stop program
            stopProgram();
          }
        }
        catch(Exception error) {
          System.out.println("Something went wrong: " + error);
          stopProgram();
        }
      }

    }
    catch(IOException error) {
      System.out.println("I/O error " + error);
    }
  }

  // Returns true if join is in correct form
  // Returns false otherwise
  private static boolean isJoinMessageValid(String userInput) {
    String[] stringArray = userInput.split(" ");

    if(stringArray.length != 3) {
      return false;
    }
    if(stringArray[0] != "JOIN") {
      return false;
    }
    // Check if ip and port are valid

    return true;
  }

  // Close Program
  private static void stopProgram() {
    System.out.println("Shuting down chat program");
    System.exit(0);
  }
}
