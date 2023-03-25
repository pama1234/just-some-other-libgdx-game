package pama1234.gdx.game.neat;

import java.util.Objects;

import pama1234.gdx.game.neat.util.ActivationFunction;

public class Node implements Cloneable{
  public enum NodeType{
    INPUT,
    HIDDEN,
    OUTPUT
  }
  private NodeType type;
  private int id;
  private ActivationFunction activationFunction;
  private float cache;
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
  public void setValue(float in) {
    // Set the value of the node based on the activation function
    cache=this.activationFunction.apply(in);
    // this.activationFunction.setValue(in);
  }
  public float getValue() {
    // 根据缓存获取浮点数
    return cache;
  }
  public float apply(float in) {
    // Get the value of the node based on the activation function
    return cache=this.activationFunction.apply(in);
  }
}