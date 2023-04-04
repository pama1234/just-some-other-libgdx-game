package pama1234.util.neat.raimannma.architecture;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NetworkCore<N extends Node,C extends Connection>{
  public final int inputSize;
  public final int outputSize;
  /** * The Nodes of the network. */
  public List<N> nodes;
  /** * The Score. used for NEAT. */
  public float score;
  /** * The Connections of the network. */
  public Set<C> connections;
  /** * The Self connections of the network. */
  public Set<C> selfConnections;
  /** * The Gates of the network. */
  public Set<C> gates;
  /** * The Dropout probability. */
  protected float dropout;
  /** * used for misc purpose */
  public float[] floatData;
  /**
   * Instantiates a new Network.
   *
   * @param input  the input size
   * @param output the output size
   */
  public NetworkCore(final int input,final int output) {
    this.inputSize=input;
    this.outputSize=output;
    this.score=Float.NaN;
    this.nodes=new ArrayList<>();
    this.connections=new HashSet<>();
    this.gates=new HashSet<>();
    this.selfConnections=new HashSet<>();
    this.dropout=0;
    // for(int i=0;i<this.inputSize;i++) this.nodes.add(new Node(Node.NodeType.INPUT));
    // for(int i=0;i<this.outputSize;i++) this.nodes.add(new Node(Node.NodeType.OUTPUT));
    // createConnection();
  }
}