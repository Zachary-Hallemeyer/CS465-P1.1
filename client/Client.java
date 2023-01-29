package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.net.Socket;
import client.ClientReciever;
import client.ClientSender;

// TODO: Add summary with Authors
public class Client {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String userInput = "";

    // Prompt user for command, ip, and port
    // do {
    //   System.out.println("To enter a chat - Input the following command: "
    //   + "JOIN <Host IP> <Host Port>");
    //   userInput = scanner.nextLine();
    // }
    // while(isJoinMessageValid(userInput));

    // Convert string to useable array
    // String[] stringArray = userInput.split(" ");
    // // Get ip
    // String ip = stringArray[1];
    // // Get port
    // int port = Integer.parseInt(stringArray[2]);

    // Get ip
    String ip = "127.0.0.1";
    // Get port
    int port = 8888;

    System.out.println("Requesting to connect to ip " + ip +
                       " on port " + port);

    // Attempt to connect to server
    try {
      Socket socket = new Socket(ip, port);
      // new ClientSender(socket).start();
      new ClientReciever(socket).start();
    }
    catch(Exception e) {
      System.out.println("Something went wrong! " + e);
    }

    // Infinite loop
    while(true) {

      // FOR SONI
      // ===============================================
      // do, while loop
      // Prompt user for message and send
        // Use a char limit (maybe 256?)

      // Check for valid message (Has valid command (<COMMAND> <Message>))
        // isMessageValid
        // Repeat if not valid
      // ================================================

      //
    }
  }

  private static boolean isJoinMessageValid(String userInput) {
    String[] stringArray = userInput.split(" ");

    if(stringArray.length < 3) {
      return false;
    }
    if(stringArray[0] != "JOIN") {
      return false;
    }
    // Check if ip and port are valid

    return true;
  }

  // FOR SONI
  // Message is valid if it follows the form "<COMMAND> <MESSAGE>"
  // Returns true if valid, returns false otherwise
  private static boolean isMessageValid(String userInput) {

  }

  // FOR SONI
  private static void displayMessageFromServer(String message) {
    // Print message from server to console
      // FORMAT: <Name>: <Message>
  }
}
