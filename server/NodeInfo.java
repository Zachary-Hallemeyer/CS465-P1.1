package server;

import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Class [NodeInfo] defines an object to containing the necessary information
 * to keep track as well as send data to clients from server
 *
 * @author Zachary M. Hallemeyer
 */
public class NodeInfo implements Serializable{
  ObjectOutputStream outputStream;

  public NodeInfo( ObjectOutputStream outputStream) {
    this.outputStream = outputStream;
  }
}
