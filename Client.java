import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
// import java.lang.StringUtils;
import java.util.Scanner;

// TODO: Add summary with Authors
public class Client {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String userInput = "";

    // Prompt user for command, ip, and port
    do {
      System.out.println("To enter a chat - Input the following command: "
      + "JOIN <Host IP> <Host Port>");
      userInput = scanner.nextLine();
    }
    while(isJoinMessageWrong(userInput));

    // Convert string to useable array
    String[] stringArray = userInput.split(" ");
    // Get ip
    String ip = stringArray[1];
    // Get port
    int port = Integer.parseInt(stringArray[2]);

    System.out.println("Requesting to connect to ip " + ip +
                       " on port " + port);

    // Infinite loop

      // Listen for traffic from server and display to console

      // Prompt user for message and send
        // Use a char limit (maybe 256?)
  }

  private static boolean isJoinMessageWrong(String userInput) {
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

  private static void parseUserInput() {
    // Parse join message

    // Parse leave message

    // Parse note message
  }

  private static void sendMessageToServer() {
    // TODO

    // Format message

    // Send to server
  }

  // May not need (can probably just be done in sendMessageToServer)
  private static void formatMessage() {
    // TODO
  }

  // Processes command from user
  private static void processCommand() {
    // TODO
  }

  private static void displayMessageFromServer() {
    // TODO

    // Print message from server to console
      // FORMAT: <Name>: <Message>
  }

  // May not need (can probably just be done in main)
  private static void closeConnection() {
    // TODO
  }
}
