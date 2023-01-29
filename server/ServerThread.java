package server;

import java.lang.Thread;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;


public class ServerThread extends Thread {

  Socket socket;

  public ServerThread(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    // TODO
    // FileInputStream FileInputStream = new FileInputStream();
    try {
      ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

      System.out.println(inputStream.readObject());

    }
    catch (Exception e) {

    }

  }

}
