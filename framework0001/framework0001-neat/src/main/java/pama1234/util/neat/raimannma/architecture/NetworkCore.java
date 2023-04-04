package pama1234.util.neat.raimannma.architecture;

import static pama1234.util.neat.raimannma.methods.Utils.randBoolean;
import static pama1234.util.neat.raimannma.methods.Utils.randFloat;
import static pama1234.util.neat.raimannma.methods.Utils.randInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
  public C connect(final N from,final N to) {
    return this.connect(from,to,0);
  }
  /** * Sets node indices. */
  public void setNodeIndices() {
    // node index equals to position in this.nodes list
    IntStream.range(0,this.nodes.size()).forEach(i->this.nodes.get(i).index=i);
  }
  /**
   * Makes a network node gate a connection
   *
   * @param node       the gating node
   * @param connection the connection to gate with node
   */
  public void gate(final N node,final C connection) {
    if(!this.nodes.contains(node)) {
      throw new ArrayIndexOutOfBoundsException("This node is not part of the network!");
    }else if(connection.gateNode==null) {
      // only gate when connection isn't gated already
      node.gate(connection); // gate node-level
      this.gates.add(connection); // add connection to gates list
    }
  }
  //---------------------------------------------------------------------------------------------------------------
  @FunctionalInterface
  public interface NewNetwork<T extends NetworkCore<?,?>>{
    public T get(int inputSize,int outputSize);
  }
  @FunctionalInterface
  public interface NewNode<N extends Node>{
    public N get(NodeType nodeType);
    public default N get() {
      return get(NodeType.HIDDEN);
    }
  }
  //---------------------------------------------------------------------------------------------------------------
  /**
   * Create an offspring from two parent networks.
   * <p>
   * Networks are not required to have the same size, however input and output size should be the
   * same!
   *
   * @param network_a parent network 1
   * @param network_b parent network 2
   * @param equal     indicate that networks are equally fit
   * @return new network created from mixing parent networks
   */
  public static <N extends Node,C extends Connection,T extends NetworkCore<N,C>> T crossover(
    final NetworkCore<N,C> network_a,final NetworkCore<N,C> network_b,final boolean equal,
    final NewNetwork<T> newNetwork,final NewNode<N> newNode) {
    if(network_a.inputSize!=network_b.inputSize||network_a.outputSize!=network_b.outputSize) {
      // Networks must have same input/output sizes
      throw new IllegalStateException("Networks don't have the same input/output size!");
    }
    // create offspring
    final T offspring=newNetwork.get(network_a.inputSize,network_a.outputSize);
    offspring.connections.clear();
    offspring.nodes.clear();
    final float score1=Float.isNaN(network_a.score)?-Float.MAX_VALUE:network_a.score;
    final float score2=Float.isNaN(network_b.score)?-Float.MAX_VALUE:network_b.score;
    final int size1=network_a.nodes.size(); // size of parent 1
    final int size2=network_b.nodes.size(); // size of parent 2
    // size of offspring
    final int size=equal||score1==score2
      ?randInt(Math.min(size1,size2),Math.max(size1,size2))// select random size between min and max
      :score1>score2?size1:size2; // Select size of fittest.
    network_a.setNodeIndices(); // set indices for network 1
    network_b.setNodeIndices(); // set indices for network 2
    for(int i=0;i<size;i++) {// Create nodes for the offspring
      final Node node; // first choice for the new node
      if(i<size-network_a.outputSize) {
        // creating a input or hidden node
        if(randBoolean()) { // choose random
          // try getting a input or hidden node from network 1, fallback to network 2
          node=i<size1&&network_a.nodes.get(i).type!=Node.NodeType.OUTPUT
            ?network_a.nodes.get(i)
            :network_b.nodes.get(i);
        }else {
          // try getting a input or hidden node from network 2, fallback to network 1
          node=i<size2&&network_b.nodes.get(i).type!=Node.NodeType.OUTPUT
            ?network_b.nodes.get(i)
            :network_a.nodes.get(i);
        }
      }else {
        // create a output node
        // pick random output node
        node=randBoolean()
          ?network_a.nodes.get(i+size1-size)
          :network_b.nodes.get(i+size2-size);
      }
      final N node_2=newNode.get();
      node_2.bias=node.bias;
      node_2.activationType=node.activationType;
      node_2.type=node.type;
      // add node to offspring
      offspring.nodes.add(node_2);
    }
    // Maps for offspring connections
    final Map<Integer,int[]> network1Connections=makeConnections(network_a);
    final Map<Integer,int[]> network2Connections=makeConnections(network_b);
    final List<int[]> connections=new ArrayList<>();
    // List of innovation IDs from both parents
    final List<Integer> innovationIDs1=new ArrayList<>(network1Connections.keySet());
    for(final Integer innovationID:innovationIDs1) {
      if(network2Connections.get(innovationID)!=null) {
        //Choose random connection out of both networks
        connections.add(randBoolean()
          ?network1Connections.get(innovationID)
          :network2Connections.get(innovationID));
        // set to null, because removing is expensive
        network2Connections.put(innovationID,null);
      }else if(score1>=score2||equal) {
        // choose connection from better network, or if they are equal
        connections.add(network1Connections.get(innovationID));
      }
    }
    // Excess/disjoint gene
    final List<Integer> innovationIDs2=new ArrayList<>(network2Connections.keySet());
    if(score2>=score1||equal) {
      innovationIDs2.stream()
        .map(network2Connections::get)
        .filter(Objects::nonNull)
        .forEach(connections::add);
    }
    // Add common conn genes uniformly
    connections.stream()
      .forEach(connectionData-> {
        final int gain=ConnectionData.gain(connectionData);
        if(gain>=size||gain<0) return; // node index must be lower than size
        final int toIndex=ConnectionData.toIndex(connectionData);
        if(toIndex>=size) return;// node index must be lower than size
        final C connection=offspring.connect(
          offspring.nodes.get(gain),
          offspring.nodes.get(toIndex));
        connection.weight=ConnectionData.weight(connectionData);
        if(ConnectionData.gateNodeIndex(connectionData)>=0&&
          ConnectionData.gateNodeIndex(connectionData)<size) offspring.gate(
            offspring.nodes.get(ConnectionData.gateNodeIndex(connectionData)),connection);
      });
    offspring.setNodeIndices();
    return offspring;
  }
  /**
   * Create a connections map with InnovationID as key and ConnectionData as value.
   *
   * @param network the network
   * @return the resulting map
   */
  private static <N extends Node,C extends Connection> Map<Integer,int[]> makeConnections(final NetworkCore<N,C> network) {
    final Map<Integer,int[]> connections=new HashMap<>();
    Stream.concat(network.connections.stream(),network.selfConnections.stream()) // create stream with all connections
      .forEach(connection->connections.put(
        Connection.getInnovationID(connection.from.index,connection.to.index),
        connection.getConnectionDataAsIntArray()));
    return connections;
  }
  //---------------------------------------------------------------------------------------------------------------
}