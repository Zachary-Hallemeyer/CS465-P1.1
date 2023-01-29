package server;

// TODO: Add summary with Authors
public class NodeInfo {
  public String name;
  public String ip; // May have to change to different data type
  public int port;

  public NodeInfo(String name, String ip, int port) {
    this.name = name;
    this.ip = ip;
    this.port = port;
  }
}
