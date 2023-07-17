package pama1234.util.neat.raimannma.architecture;

import static pama1234.util.neat.raimannma.methods.Mutation.SUB_NODE;
import static pama1234.util.neat.raimannma.methods.Utils.pickRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import pama1234.util.neat.raimannma.architecture.Node.NodeType;
import pama1234.util.neat.raimannma.methods.Loss;
import pama1234.util.neat.raimannma.methods.Mutation;

/**
 * The type Network.
 *
 * @author Manuel Raimann
 */
public class Network extends NetworkCore<Node,Connection>{
  /**
   * Instantiates a new Network.
   *
   * @param input  the input size
   * @param output the output size
   */
  public Network(final int input,final int output) {
    super(input,output);
  }
  @Override
  public Node newNode(NodeType in) {
    return new Node(in);
  }
  @Override
  public Connection newConnection(Node from,Node to,float weight) {
    return from.connect(to,weight);
  }
  /**
   * Convert a json object to a network
   *
   * @param json A network represented as a JsonObject
   * @return the network created out of the JsonObject
   */
  public static Network fromJSON(final JsonObject json) {
    // create default network with input and output size from json
    final Network network=new Network(json.get("input").getAsInt(),json.get("output").getAsInt());
    network.dropout=json.get("dropout").getAsFloat(); // set dropout probability
    network.score=json.get("score").getAsFloat(); // set score value
    // clearing the network
    network.nodes.clear();
    network.connections.clear();
    // Filling the network
    // add nodes to the network
    final JsonArray jsonNodes=json.get("nodes").getAsJsonArray();
    final JsonArray jsonConnections=json.get("connections").getAsJsonArray();
    jsonNodes.forEach(jsonNode->network.nodes.add(Node.fromJSON(jsonNode.getAsJsonObject())));
    // add connection to the network
    jsonConnections.forEach(connJSON-> {
      final Connection connection=Connection.fromJSON(connJSON.getAsJsonObject(),network.nodes); // get connection from json
      if(connection.isSelfConnection()) {
        connection.from.self=connection; // set self connection in node
        network.selfConnections.add(connection); // add connection to self connections list
      }else {
        connection.from.out.add(connection); // set connection to "from" node
        connection.to.in.add(connection); // set connection to "to" node
        network.connections.add(connection); // add connection to connections list
      }
      if(connection.isGated()) {
        connection.gateNode.gated.add(connection); // set connections as gated in gate node
        network.gates.add(connection); // add connection to gates list
      }
    });
    return network;
  }
  /**
   * Mutates the network with the given method.
   *
   * @param method the mutation method
   */
  public void mutate(final Mutation method) {
    if(Arrays.stream(Mutation.ALL).noneMatch(meth->meth==method)) {
      throw new IllegalArgumentException("No correct mutate method given!");
    }
    method.mutate(this);
  }
  /**
   * Calculates growth score of this network.
   *
   * @param growth the growth rate
   * @return the growth score
   */
  public float getGrowthScore(final float growth) {
    return growth*(this.nodes.size()
      +this.connections.size()
      +this.gates.size()
      -this.inputSize
      -this.outputSize);
  }
  /**
   * Test the network on dataset.
   *
   * @param inputs  the inputs of the dataset
   * @param outputs the outputs of the dataset
   * @return the error
   */
  public float test(final float[][] inputs,final float[][] outputs) {
    return this.test(inputs,outputs,Loss.MSE);
  }
  /**
   * Test the network on dataset with given loss function.
   *
   * @param inputs  the inputs of the dataset
   * @param outputs the outputs of the dataset
   * @param loss    the loss function
   * @return the error
   */
  public float test(final float[][] inputs,final float[][] outputs,final Loss loss) {
    if(loss==null) {
      return this.test(inputs,outputs);
    }else if(this.dropout!=0) {
      this.nodes.stream()
        .filter(node->node.type==Node.NodeType.HIDDEN)
        .forEach(node->node.mask=1-this.dropout);
    }
    return (float)(IntStream.range(0,inputs.length)
      .mapToDouble(i->loss.calc(outputs[i],this.activate(inputs[i])))
      .sum()
      /inputs.length);
  }
  /**
   * Evolves the network to reach a lower error on a dataset using the NEAT algorithm.
   *
   * @param inputs  the inputs of the dataset
   * @param outputs the outputs of the dataset
   * @param options options evolution options
   * @return the error of the network after evolution
   */
  public float evolve(final float[][] inputs,final float[][] outputs,final EvolveOptions options) {
    if(inputs[0].length!=this.inputSize||outputs[0].length!=this.outputSize) {
      // check dataset dimensions
      throw new IllegalStateException("Dataset input/output size should be same as network input/output size!");
    }
    float targetError=options.getError();
    final float growth=options.getGrowth();
    final Loss loss=options.getLoss();
    final int amount=options.getAmount();
    if(options.getIterations()==-1&&Float.isNaN(options.getError())) {
      throw new IllegalArgumentException("At least one of the following options must be specified: error, iterations");
    }else if(Float.isNaN(options.getError())) {
      targetError=-1; // run until iterations
    }
    // else if(options.getIterations()<=0) {
    //   options.setIterations(Integer.MAX_VALUE);
    // }
    if(options.getFitnessFunction()==null) {
      // if user doesn't specified a fitness function -> create default fitness function
      options.setFitnessFunction(genome-> {
        final float sum=(float)IntStream.range(0,amount)
          .mapToDouble(i->-genome.test(inputs,outputs,loss))
          .sum();
        return (sum-genome.getGrowthScore(growth))/amount;
      });
    }
    options.setTemplate(this); // set this network as template
    final NEAT neat=new NEAT(this.inputSize,this.outputSize,options); // create NEAT instance
    float error=-Float.MAX_VALUE;
    float bestScore=-Float.MAX_VALUE;
    Network bestGenome=null;
    // start evolution loop
    while(error<-targetError&&
      (neat.generation<options.getIterations()||options.getIterations()<0)) {
      final Network fittest=neat.evolve(); // run one evolution step
      error=fittest.score+fittest.getGrowthScore(growth); // calculate error of the fittest genome
      if(fittest.score>bestScore) {
        // if fittest is fitter than the best genome so far
        // set fittest to new global best genome
        bestScore=fittest.score;
        bestGenome=fittest;
      }
      // Log
      printLog(options,neat,error,fittest);
    }
    if(bestGenome!=null) {
      // set this network equal to fittest network of evolution
      this.nodes=bestGenome.nodes;
      this.connections=bestGenome.connections;
      this.selfConnections=bestGenome.selfConnections;
      this.gates=bestGenome.gates;
      if(options.isClear()) {
        this.clear();
      }
    }
    return -error;
  }
  public void printLog(final EvolveOptions options,final NEAT neat,float error,final Network fittest) {
    if(options.getLog()>0&&neat.generation%options.getLog()==0) {
      System.out.println(
        "Iteration: "+
          neat.generation+
          "; Fitness: "+
          fittest.score+
          "; Error: "+
          -error+
          "; Population: "+
          neat.population.size());
    }
  }
  /**
   * Activate the network with given input data.
   *
   * @param input the input data
   * @return the activation values of the output nodes
   */
  public float[] activate(final float[] input) {
    return activate(input,new float[outputSize]);
  }
  public float[] activate(final float[] input,final float[] output) {
    return activate(0,input,0,output);
  }
  public float[] activate(
    final int inputOffset,final float[] input,
    final int outputOffset,final float[] output) {
    int inputIndex=inputOffset;
    int outputIndex=outputOffset;
    for(final Node node:this.nodes) {
      if(node.type==Node.NodeType.INPUT&&this.inputSize>inputIndex) node.activate(input[inputIndex++]); // input node
      else if(node.type==Node.NodeType.OUTPUT) output[outputIndex++]=node.activate(); // output node
      else node.activate(); // hidden node
    }
    return output;
  }
  public float[] activate(
    final int inputOffset,final int inputSize,final float[] input,
    final int outputOffset,final int outputSize,final float[] output) {
    if(inputSize>=this.inputSize||outputSize>=this.outputSize) return activate(
      inputOffset,input,outputOffset,output);
    int inputIndex=inputOffset;
    int outputIndex=outputOffset;
    for(final Node node:this.nodes) {
      if(node.type==Node.NodeType.INPUT&&this.inputSize>inputIndex) node.activate(
        inputIndex<inputSize?input[inputIndex++]:0); // input node
      else if(node.type==Node.NodeType.OUTPUT) {
        if(outputIndex<outputSize) output[outputIndex++]=node.activate();
        else node.activate();
      }// output node
      else node.activate(); // hidden node
    }
    return output;
  }
  public FloatBlock activate(final FloatBlock input,final FloatBlock output) {
    activate(input.offset,input.size,input.data(),output.offset,output.size,output.data());
    return output;
  }
  /**
   * Removes a node from a network. All its connections will be redirected. If it gates a
   * connection, the gate will be removed.
   *
   * @param node Node to remove from the network
   */
  public void remove(final Node node) {
    if(!this.nodes.contains(node)) {
      throw new IllegalArgumentException("This node does not exist in the network!");
    }
    this.disconnect(node,node); // remove self connection
    // nodes which are directly connected into this node
    final List<Node> inputs=node.in.stream()
      .map(connection->connection.from)
      .collect(Collectors.toList());
    // nodes which are directly connected out of this node
    final List<Node> outputs=node.out.stream()
      .map(connection->connection.to)
      .collect(Collectors.toList());
    // disconnect all inputs and outputs
    inputs.forEach(input->this.disconnect(input,node));
    outputs.forEach(output->this.disconnect(node,output));
    // create new connections from input nodes to output nodes
    final List<Connection> connections=new ArrayList<>();
    for(final Node input:inputs) {
      for(final Node output:outputs) {
        if(input.isNotProjectingTo(output)) {
          // if input and output aren't connected
          // connect them
          connections.add(this.connect(input,output,0));
        }
      }
    }
    Stream.concat(node.in.stream(),node.out.stream())
      .filter(connection->SUB_NODE.keepGates) // Should keep gates?
      .map(connection->connection.gateNode)
      .filter(Objects::nonNull)
      .filter(gateNode->!gateNode.equals(node)) // should be unequal to currently removing node
      .takeWhile(gateNode->!connections.isEmpty()) // as long as connections are available
      .forEach(gateNode-> {
        // gate connection and remove it
        final Connection connection=pickRandom(connections); // pick random connection
        this.gate(gateNode,connection); // gate this connection
        connections.remove(connection); // remove connection from list
      });
    // remove all gates
    for(int i=node.gated.size()-1;i>=0;i--) {
      this.removeGate(node.gated.get(i));
    }
    this.nodes.remove(node); // remove node from list
    this.setNodeIndices();
  }
  /**
   * Removes the connection between two nodes
   *
   * @param from the incoming node
   * @param to   the outgoing node
   */
  public void disconnect(final Node from,final Node to) {
    final Connection connection;
    if(from.equals(to)) {
      connection=from.self;
      this.selfConnections.remove(connection); // remove connection from list
    }else {
      connection=this.connections.stream()
        .filter(conn->conn.from.equals(from))
        .filter(conn->conn.to.equals(to))
        .findAny()
        .orElse(null);
      this.connections.remove(connection); // remove connection from list
    }
    if(connection!=null&&connection.gateNode!=null) {
      // if connection has gate
      // remove it
      this.removeGate(connection);
    }
    from.disconnect(to); // run node-level disconnect
  }
  /**
   * Remove the gate of a connection.
   *
   * @param connection the connection to remove the gate from
   */
  public void removeGate(final Connection connection) {
    if(connection!=null&&connection.gateNode!=null&&this.gates.remove(connection)) {
      connection.gateNode.removeGate(connection);
    }
  }
  /**
   * Resets every node in this network.
   */
  public void clear() {
    this.nodes.forEach(Node::clear);
  }
  @Override
  public int hashCode() {
    return Objects.hash(this.inputSize,this.outputSize,this.nodes,this.connections,this.gates,this.selfConnections,this.dropout);
  }
  @Override
  public boolean equals(final Object o) {
    if(this==o) {
      return true;
    }
    if(o==null||this.getClass()!=o.getClass()) {
      return false;
    }
    final Network network=(Network)o;
    return this.inputSize==network.inputSize&&
      this.outputSize==network.outputSize&&
      Float.compare(network.dropout,this.dropout)==0&&
      Objects.equals(this.nodes,network.nodes)&&
      Objects.equals(this.connections,network.connections)&&
      Objects.equals(this.gates,network.gates)&&
      Objects.equals(this.selfConnections,network.selfConnections);
  }
  @Override
  public String toString() {
    return "Network{"+
      "input="+
      this.inputSize+
      ", output="+
      this.outputSize+
      ", gates="+
      this.gates+
      ", nodes="+
      this.nodes+
      ", connections="+
      this.connections+
      ", selfConnections="+
      this.selfConnections+
      '}';
  }
  /**
   * Copies a network.
   *
   * @return the copied network
   */
  public Network copy() {
    return Network.fromJSON(this.toJSON()); // simply convert to json and back
  }
  /**
   * Convert a network to JsonObject.
   *
   * @return the resulting json object
   */
  public JsonObject toJSON() {
    // add no self connections from self connections list to connections list
    this.connections.addAll(this.selfConnections.stream().filter(conn->!conn.isSelfConnection()).collect(Collectors.toSet()));
    // add self connections from connections list to self connections list
    this.selfConnections.addAll(this.connections.stream().filter(Connection::isSelfConnection).collect(Collectors.toSet()));
    // remove self connections from connections list
    this.connections.removeIf(Connection::isSelfConnection);
    // remove no self connections from self connections list
    this.selfConnections.removeIf(conn->!conn.isSelfConnection());
    // remove gates without a representing connection in connections list or self connections list
    this.gates.removeIf(gate->!this.connections.contains(gate)&&!this.selfConnections.contains(gate));
    // set gateNode to null for all connections except the ones in gates list
    Stream.concat(this.connections.stream(),this.selfConnections.stream())
      .filter(conn->!this.gates.contains(conn))
      .forEach(conn->conn.gateNode=null);
    final JsonObject json=new JsonObject();
    json.addProperty("input",this.inputSize); // add input property
    json.addProperty("output",this.outputSize); // add output property
    json.addProperty("dropout",this.dropout); // add dropout property
    json.addProperty("score",this.score); // add score property
    final JsonArray jsonNodes=new JsonArray();
    final JsonArray jsonConnections=new JsonArray();
    this.setNodeIndices(); // set node indices
    this.nodes.stream().map(Node::toJSON).forEach(jsonNodes::add); // add nodes to json array
    this.gates.removeIf(gate->!gate.isGated()); // remove connection from gates list, if it isn't gated
    this.connections.stream().map(Connection::toJSON).forEach(jsonConnections::add); // add connections to json array
    this.selfConnections.stream().map(Connection::toJSON).forEach(jsonConnections::add); // add self connections to json array
    json.add("nodes",jsonNodes); // add nodes json array
    json.add("connections",jsonConnections); /// add connections json array
    return json;
  }
}