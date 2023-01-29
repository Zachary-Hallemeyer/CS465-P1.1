package client;
import java.lang.Thread;
import java.net.Socket;   // Might not need
import java.io.ObjectOutputStream;

public class ClientSender extends Thread {
  Socket socket;

  public ClientSender(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    System.out.println("Sender is ready");

    try {
      ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

      outputStream.writeObject("Hellllloooo");
      outputStream.close();
    }
    catch (Exception e) {

    }
  }
}
