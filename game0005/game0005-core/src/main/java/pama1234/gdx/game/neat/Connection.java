package pama1234.gdx.game.neat;

public class Connection{
  private Node inputNode;
  private Node outputNode;
  private double weight;
  private boolean isEnabled;
  private int innovationNumber;
  public Connection(Node inputNode,Node outputNode,double weight,boolean isEnabled,int innovationNumber) {
    this.inputNode=inputNode;
    this.outputNode=outputNode;
    this.weight=weight;
    this.isEnabled=isEnabled;
    this.innovationNumber=innovationNumber;
  }
  public Node getInNode() {
    return inputNode;
  }
  public void setInputNode(Node inputNode) {
    this.inputNode=inputNode;
  }
  public Node getOutNode() {
    return outputNode;
  }
  public void setOutputNode(Node outputNode) {
    this.outputNode=outputNode;
  }
  public double getWeight() {
    return weight;
  }
  public void setWeight(double weight) {
    this.weight=weight;
  }
  public boolean isEnabled() {
    return isEnabled;
  }
  public void setEnabled(boolean isEnabled) {
    this.isEnabled=isEnabled;
  }
  public int getInnovationNumber() {
    return innovationNumber;
  }
  public void setInnovationNumber(int innovationNumber) {
    this.innovationNumber=innovationNumber;
  }
  @Override
  public Connection clone() {
    return new Connection(inputNode,outputNode,weight,isEnabled,innovationNumber);
  }
}