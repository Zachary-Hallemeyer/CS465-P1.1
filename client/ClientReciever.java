package client;

import java.lang.Thread;
import java.net.Socket;   // Might not need

import java.io.ObjectInputStream;

public class ClientReciever extends Thread {
  Socket socket;

  public ClientReciever(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    System.out.println("Reciever is ready");
  }
}
