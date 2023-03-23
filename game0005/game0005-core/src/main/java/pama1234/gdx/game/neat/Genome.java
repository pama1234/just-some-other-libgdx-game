package pama1234.gdx.game.neat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pama1234.gdx.game.neat.Node.NodeType;

public class Genome implements Comparable<Genome>{
  private int numInputs;
  private int numOutputs;
  private List<Node> nodes;
  private List<Connection> connections;
  private double fitness;
  public Genome(int numInputs,int numOutputs) {
    this.numInputs=numInputs;
    this.numOutputs=numOutputs;
    this.nodes=new ArrayList<>();
    this.connections=new ArrayList<>();
    this.fitness=0.0;
  }
  public void initialize() {
    // 初始化输入和输出节点
    for(int i=0;i<numInputs;i++) {
      nodes.add(new Node(NodeType.INPUT,i+1));
    }
    for(int i=0;i<numOutputs;i++) {
      nodes.add(new Node(NodeType.OUTPUT,numInputs+i+1));
    }
    // 添加一个隐藏节点
    nodes.add(new Node(NodeType.HIDDEN,numInputs+numOutputs+1));
    // 初始化连接
    for(Node inputNode:getInputNodes()) {
      for(Node outputNode:getOutputNodes()) {
        connections.add(new Connection(inputNode,outputNode,Math.random()*2-1,true,connections.size()+1));
      }
      connections.add(new Connection(inputNode,getHiddenNode(),Math.random()*2-1,true,connections.size()+1));
    }
    for(Node hiddenNode:getHiddenNodes()) {
      for(Node outputNode:getOutputNodes()) {
        connections.add(new Connection(hiddenNode,outputNode,Math.random()*2-1,true,connections.size()+1));
      }
    }
  }
  public Genome crossover(Genome other) {
    Genome child=new Genome(numInputs,numOutputs);
    child.nodes.addAll(getInputNodes());
    child.nodes.addAll(getOutputNodes());
    Map<Integer,Node> nodeMap=new HashMap<>();
    for(Node node:nodes) {
      nodeMap.put(node.getId(),node);
    }
    for(Node otherNode:other.nodes) {
      if(!nodeMap.containsKey(otherNode.getId())) {
        child.nodes.add(otherNode.clone());
      }
    }
    for(Connection connection:getEnabledConnections()) {
      Connection matchingConnection=other.getConnection(connection.getInNode(),connection.getOutNode());
      if(matchingConnection!=null&&Math.random()<0.5) {
        child.connections.add(matchingConnection.clone());
      }else {
        child.connections.add(connection.clone());
      }
    }
    return child;
  }
  public void mutate(double mutationRate) {
    for(Node node:nodes) {
      if(node.getType()==NodeType.HIDDEN&&Math.random()<mutationRate) {
        node.setActivationFunction(ActivationFunction.getRandomFunction());
      }
    }
    for(Connection connection:connections) {
      if(Math.random()<mutationRate) {
        connection.setWeight(Math.random()*2-1);
      }
      if(Math.random()<mutationRate) {
        connection.setEnabled(!connection.isEnabled());
      }
    }
  }
  public Connection getConnection(Node inNode,Node outNode) {
    for(Connection connection:connections) {
      if(connection.getInNode().equals(inNode)&&connection.getOutNode().equals(outNode)) {
        return connection;
      }
    }
    return null;
  }
  public List<Connection> getEnabledConnections() {
    List<Connection> enabledConnections=new ArrayList<>();
    for(Connection connection:connections) {
      if(connection.isEnabled()) {
        enabledConnections.add(connection);
      }
    }
    return enabledConnections;
  }
  public List<Node> getInputNodes() {
    List<Node> inputNodes=new ArrayList<>();
    for(Node node:nodes) {
      if(node.getType()==NodeType.INPUT) {
        inputNodes.add(node);
      }
    }
    return inputNodes;
  }
  public List<Node> getOutputNodes() {
    List<Node> outputNodes=new ArrayList<>();
    for(Node node:nodes) {
      if(node.getType()==NodeType.OUTPUT) {
        outputNodes.add(node);
      }
    }
    return outputNodes;
  }
  public Node getHiddenNode() {//TODO
    return null;
  }
  public List<Node> getHiddenNodes() {
    List<Node> hiddenNodes=new ArrayList<>();
    for(Node node:nodes) {
      if(node.getType()==NodeType.HIDDEN) {
        hiddenNodes.add(node);
      }
    }
    return hiddenNodes;
  }
  @Override
  public int compareTo(Genome other) {
    return Double.compare(fitness,other.fitness);
  }
  @Override
  public Genome clone() {
    Genome clone=new Genome(numInputs,numOutputs);
    for(Node node:nodes) {
      clone.nodes.add(node.clone());
    }
    for(Connection connection:connections) {
      clone.connections.add(connection.clone());
    }
    clone.fitness=fitness;
    return clone;
  }
  public boolean isCompatibleWith(Genome other,NEATConfig config) {
    int numExcess=getNumExcessGenes(other);
    int numDisjoint=getNumDisjointGenes(other);
    double weightDifference=getWeightDifference(other);
    int maxGenes=Math.max(connections.size(),other.connections.size());
    double compatibilityDistance=(config.getExcessCoefficient()*numExcess/maxGenes)
      +(config.getDisjointCoefficient()*numDisjoint/maxGenes)
      +(config.getWeightDifferenceCoefficient()*weightDifference);
    return compatibilityDistance<=config.getCompatibilityThreshold();
  }
  private int getNumExcessGenes(Genome other) {
    int numExcess=0;
    int maxInnovation=Math.max(getMaxInnovation(),other.getMaxInnovation());
    for(Connection connection:connections) {
      if(connection.getInnovation()>maxInnovation) {
        numExcess++;
      }
    }
    for(Connection connection:other.connections) {
      if(connection.getInnovation()>maxInnovation) {
        numExcess++;
      }
    }
    return numExcess;
  }
  private int getNumDisjointGenes(Genome other) {
    int numDisjoint=0;
    Set<Integer> thisInnovations=new HashSet<>();
    for(Connection connection:connections) {
      thisInnovations.add(connection.getInnovation());
    }
    Set<Integer> otherInnovations=new HashSet<>();
    for(Connection connection:other.connections) {
      otherInnovations.add(connection.getInnovation());
    }
    Set<Integer> allInnovations=new HashSet<>();
    allInnovations.addAll(thisInnovations);
    allInnovations.addAll(otherInnovations);
    for(int innovation:allInnovations) {
      if(!thisInnovations.contains(innovation)||!otherInnovations.contains(innovation)) {
        numDisjoint++;
      }
    }
    return numDisjoint;
  }
  private double getWeightDifference(Genome other) {
    double weightDifference=0.0;
    for(Connection connection:connections) {
      Connection matchingConnection=other.getConnection(connection.getInNode(),connection.getOutNode());
      if(matchingConnection!=null) {
        weightDifference+=Math.abs(connection.getWeight()-matchingConnection.getWeight());
      }
    }
    return weightDifference;
  }
  private int getMaxInnovation() {
    int maxInnovation=0;
    for(Connection connection:connections) {
      if(connection.getInnovation()>maxInnovation) {
        maxInnovation=connection.getInnovation();
      }
    }
    return maxInnovation;
  }
  public double[] evaluate(double[] inputs) {
    // Feed the inputs to the neural network and get the outputs
    double[] outputs=neuralNetwork.feedForward(inputs);
    // Return the outputs
    return outputs;
  }
}