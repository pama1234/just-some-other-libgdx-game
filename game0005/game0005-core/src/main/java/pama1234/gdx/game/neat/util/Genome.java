package pama1234.gdx.game.neat.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pama1234.gdx.game.neat.NEATConfig;
import pama1234.gdx.game.neat.util.Node.NodeType;

/**
 * 类似于 https://github.com/raimannma/NEAT4J 中的Network类
 */
public class Genome implements Comparable<Genome>{
  private int numInputs;
  private int numOutputs;
  private List<Node> nodes;
  private List<Connection> connections;
  /**
   * 别名：fitness
   */
  private float score;
  public Genome(int numInputs,int numOutputs) {
    this.numInputs=numInputs;
    this.numOutputs=numOutputs;
    this.nodes=new ArrayList<>();
    this.connections=new ArrayList<>();
    this.score=0.0f;
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
  public void mutate(float mutationRate) {
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
      // System.out.println("Checking connection: "+connection.getInNode()+" -> "+connection.getOutNode());
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
  public Node getHiddenNode() {
    return getHiddenNodes().get(0);
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
    return Float.compare(score,other.score);
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
    clone.score=score;
    return clone;
  }
  public boolean isCompatibleWith(Genome other,NEATConfig config) {
    int numExcess=getNumExcessGenes(other);
    int numDisjoint=getNumDisjointGenes(other);
    float weightDifference=getWeightDifference(other);
    int maxGenes=Math.max(connections.size(),other.connections.size());
    float compatibilityDistance=(config.getExcessCoefficient()*numExcess/maxGenes)
      +(config.getDisjointCoefficient()*numDisjoint/maxGenes)
      +(config.getWeightDifferenceCoefficient()*weightDifference);
    return compatibilityDistance<=config.getCompatibilityThreshold();
  }
  private int getNumExcessGenes(Genome other) {
    int numExcess=0;
    int maxInnovation=Math.max(getMaxInnovation(),other.getMaxInnovation());
    for(Connection connection:connections) {
      if(connection.getInnovationNumber()>maxInnovation) {
        numExcess++;
      }
    }
    for(Connection connection:other.connections) {
      if(connection.getInnovationNumber()>maxInnovation) {
        numExcess++;
      }
    }
    return numExcess;
  }
  private int getNumDisjointGenes(Genome other) {
    int numDisjoint=0;
    Set<Integer> thisInnovations=new HashSet<>();
    for(Connection connection:connections) {
      thisInnovations.add(connection.getInnovationNumber());
    }
    Set<Integer> otherInnovations=new HashSet<>();
    for(Connection connection:other.connections) {
      otherInnovations.add(connection.getInnovationNumber());
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
  private float getWeightDifference(Genome other) {
    float weightDifference=0.0f;
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
      if(connection.getInnovationNumber()>maxInnovation) {
        maxInnovation=connection.getInnovationNumber();
      }
    }
    return maxInnovation;
  }
  public float[] evaluate(float[] inputs) {
    // Create an array to store the outputs
    float[] outputs=new float[numOutputs];
    // Feed the inputs to the input nodes
    for(int i=0;i<numInputs;i++) {
      nodes.get(i).setValue(inputs[i]);
    }
    // Calculate the values of the hidden nodes
    for(Node hiddenNode:getHiddenNodes()) {
      float sum=0;
      for(Connection connection:connections) {
        if(connection.getOutNode().equals(hiddenNode)&&connection.isEnabled()) {
          sum+=connection.getInNode().apply(connection.getOutNode().getValue())*connection.getWeight();
        }
      }
      hiddenNode.setValue(hiddenNode.getActivationFunction().apply(sum));
    }
    // Calculate the values of the output nodes
    for(int i=0;i<numOutputs;i++) {
      Node outputNode=nodes.get(numInputs+i);
      float sum=0;
      for(Connection connection:connections) {
        if(connection.getOutNode().equals(outputNode)&&connection.isEnabled()) {
          sum+=connection.getInNode().apply(connection.getOutNode().getValue())*connection.getWeight();
        }
      }
      // outputNode.setValue(outputNode.getActivationFunction().apply(sum));
      // outputs[i]=outputNode.getValue(connection.getOutNode().getValue());
      outputs[i]=outputNode.apply(sum);
    }
    // Return the outputs
    return outputs;
  }
  public float getScore() {
    return score;
  }
  public void setScore(float fitness) {
    this.score=fitness;
  }
}