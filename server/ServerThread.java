package server;

import java.lang.Thread;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import message.Message;
import message.MessageTypes;
import server.NodeInfo;
import java.io.IOException;

/**
 * Class [ServerThread] accepts data some client associated with the instance of
 * this class and sends the data to all current clients
 *
 * @author Zachary M. Hallemeyer
 */
public class ServerThread extends Thread {

  Socket socket;
  NodeInfo clientInfo;

  public ServerThread(Socket socket, NodeInfo clientInfo) {
    this.socket = socket;
    this.clientInfo = clientInfo;
  }

  // This function accepts data from client and sends the data to all clients
  public void run() {
    try {
      // Create a new input stream for client
      ObjectInputStream inputStream = new ObjectInputStream(
                                            socket.getInputStream());

      // Loop until client disconnects
      while(true) {
        try {
          // Get Message object from client
          Message newMessage = (Message) inputStream.readObject();

          // Check for commands LEAVE and SHUTDOWN
          if(   newMessage.getType() == MessageTypes.LEAVE
             || newMessage.getType() == MessageTypes.SHUTDOWN) {

            String name = newMessage.getContent().toString().split(": ")[0];
            Message leaveMessage = new Message(MessageTypes.NOTE,
                                               name + " has left the chat");
            Server.users.remove(Server.users.indexOf(clientInfo));

            // Send leave message to client (with command LEAVE for client to disconnect)
            clientInfo.outputStream.writeObject(newMessage);
            clientInfo.outputStream.flush();

            // Send message to all other clients that this client has disconnected
            sendMessageToAllClients(newMessage);
            sendMessageToAllClients(leaveMessage);

            // Free resources and return out of function
            inputStream.close();
            clientInfo.outputStream.close();
            return;
          }
          // Check for command SHUTDOWN_ALL
          else if (newMessage.getType() == MessageTypes.SHUTDOWN_ALL) {
            Message serverShutdownMessage = new Message(
                                               MessageTypes.SHUTDOWN_ALL,
                                               "Server is shutting down");
            sendMessageToAllClients(serverShutdownMessage);
            System.exit(0);
          }
          // Check for command NOTE
          else if (newMessage.getType() == MessageTypes.NOTE) {
            sendMessageToAllClients(newMessage);
          }
          // Check for command JOIN
          else if (newMessage.getType() == MessageTypes.JOIN) {
            Message joinMessage = new Message(MessageTypes.NOTE,
                                              "You have already joined the chat");
            clientInfo.outputStream.writeObject(joinMessage);
            clientInfo.outputStream.flush();
          }
        }
        catch(Exception error) {
          System.out.print("Something went wrong: " + error);
        }
      }
    }
    catch (IOException error) {
      System.out.println("I/O error: " + error);
    }

  }

  // This function sends the message from parameter to all current clients
  private void sendMessageToAllClients(Message clientMessage) {
    // Send message to all clients
    for(int index = 0; index < Server.users.size(); index++) {
      try {
        // Send message and clear output stream
        Server.users.get(index).outputStream.writeObject(clientMessage);
        Server.users.get(index).outputStream.flush();

      }
      catch(IOException error) {
        System.out.println("I/O error: " + error);
      }
    }
  }

}
