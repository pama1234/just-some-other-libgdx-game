package pama1234.util.neat.raimannma.architecture;

import static pama1234.util.neat.raimannma.methods.Utils.randFloat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pama1234.math.UtilMath;
import pama1234.util.neat.raimannma.architecture.Node.NodeType;

public abstract class NetworkCore<N extends Node,C extends Connection>{
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
    for(int i=0;i<this.inputSize;i++) this.nodes.add(newNode(Node.NodeType.INPUT));
    for(int i=0;i<this.outputSize;i++) this.nodes.add(newNode(Node.NodeType.OUTPUT));
    createConnection();
  }
  public void createConnection() {
    // Create simplest Network with input and output size matching parameters
    final float initWeight=this.inputSize*UtilMath.sqrt(2f/this.inputSize);
    for(int i=0;i<this.inputSize;i++) { // iterate over the input nodes
      final N inputNode=this.nodes.get(i);
      for(int j=this.inputSize;j<this.outputSize+this.inputSize;j++) { // iterate over the output nodes
        this.connect(inputNode,
          this.nodes.get(j),randFloat(initWeight));// connect input and output node
      }
    }
  }
  public abstract N newNode(NodeType in);
  public abstract C newConnection(Node from,Node to,float weight);
  /**
   * Connect two nodes with given weight.
   *
   * @param from   the connection input node
   * @param to     the connection output node
   * @param weight the connection weight
   * @return the created connection
   */
  protected C connect(final N from,final N to,final float weight) {
    final C connection=newConnection(from,to,weight); // connect from with to
    if(from.equals(to)) {// if from equals to
      // add connection to self connections
      this.selfConnections.add(connection);
    }else {// if from unequals to
      // add connection to connections
      this.connections.add(connection);
    }
    // return created connection
    return connection;
  }
  /**
   * Connect two nodes with weight equals 0.
   *
   * @param from the connection input node
   * @param to   the connection output node
   * @return the created connection
   */
  public Connection connect(final N from,final N to) {
    return this.connect(from,to,0);
  }
}