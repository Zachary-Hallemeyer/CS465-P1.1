package client;
import java.lang.Thread;
import java.net.Socket;   // Might not need
import java.io.ObjectOutputStream;
import java.util.Scanner;
import message.Message;
import message.MessageTypes;
import java.io.IOException;

/**
 * Class [ClientSender] gathers user input from command line, modifies it
 * into type Message, and sends input to server
 *
 * @author Zachary M. Hallemeyer
 */
public class ClientSender extends Thread {
  Socket socket;
  String name;

  public ClientSender(Socket socket, String name) {
    this.socket = socket;
    this.name = name;
  }

  // This function gather's user input and sends it to server
  // This function runs until client program is exited
  public void run() {
    try {
      // Init. variables
      Scanner scanner = new Scanner(System.in);
      ObjectOutputStream outputStream = new ObjectOutputStream(
                                                    socket.getOutputStream());
      String userInput = "";

      // Infinite loop to gather input to send to server
      while(true) {
        // Get user input
        userInput = scanner.nextLine();

        // Modify input into type Message
        Message message = craftMessage(userInput);

        // Send message to server and clear output stream
        outputStream.writeObject(message);
        outputStream.flush();
      }
    }
    catch(IOException error) {
      System.out.println("I/O error: " + error);
    }
  }

  // Creates Message object with userInput and returns the Message Object
  private Message craftMessage(String userInput) {
    // Init. variables
    int type = MessageTypes.NOTE;
    String message = "";
    String firstWord = userInput.split(" ")[0];

    // Check if any valid command is used
    // If there is a command, set type accordingly to command
    // and delete command from input
    if(firstWord.contains("NOTE")){
      type = MessageTypes.NOTE;
      userInput = userInput.replace("NOTE", "");
    }
    else if(firstWord.contains("LEAVE")){
      type = MessageTypes.LEAVE;
      userInput = userInput.replace("LEAVE", "");
    }
    else if(firstWord.contains("SHUTDOWN_ALL")){
      type = MessageTypes.SHUTDOWN_ALL;
      userInput = userInput.replace("SHUTDOWN_ALL", "");
    }
    else if(firstWord.contains("SHUTDOWN")){
      type = MessageTypes.SHUTDOWN;
      userInput = userInput.replace("SHUTDOWN", "");
    }
    else if(firstWord.contains("JOIN")){
      type = MessageTypes.JOIN;
      userInput = userInput.replace("JOIN", "");
    }

    // Create new message and return it
    return new Message(type, name + ":" + userInput);
  }
}
