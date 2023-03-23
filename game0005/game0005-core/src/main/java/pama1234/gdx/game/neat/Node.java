package pama1234.gdx.game.neat;

import java.util.Objects;

public class Node implements Cloneable{
  public enum NodeType{
    INPUT,
    HIDDEN,
    OUTPUT
  }
  private NodeType type;
  private int id;
  private ActivationFunction activationFunction;
  public Node(NodeType type,int id) {
    this.type=type;
    this.id=id;
    this.activationFunction=ActivationFunction.getRandomFunction();
  }
  public NodeType getType() {
    return type;
  }
  public int getId() {
    return id;
  }
  public ActivationFunction getActivationFunction() {
    return activationFunction;
  }
  public void setActivationFunction(ActivationFunction activationFunction) {
    this.activationFunction=activationFunction;
  }
  @Override
  public Node clone() {
    Node clone=new Node(type,id);
    clone.activationFunction=activationFunction;
    return clone;
  }
  @Override
  public boolean equals(Object obj) {
    if(obj==this) {
      return true;
    }
    if(!(obj instanceof Node)) {
      return false;
    }
    Node other=(Node)obj;
    return id==other.id;
  }
  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
  public void setValue(float value) {
    // Set the value of the node based on the activation function
    this.activationFunction.setValue(value);
  }
  @Deprecated
  public float getValue(float in) {
    // Get the value of the node based on the activation function
    return this.activationFunction.apply(in);
  }
}