package pama1234.gdx.game.neat.util.raimannma.architecture;

import static pama1234.gdx.game.neat.util.raimannma.methods.Utils.randDouble;

// import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import pama1234.gdx.game.neat.util.raimannma.methods.Activation;
// import org.jetbrains.annotations.NotNull;

/**
 * The type Node.
 *
 * @author Manuel Raimann
 */
public class Node{
	/**
	 * Incoming connections.
	 */
	public final Set<Connection> in;
	/**
	 * Outgoing connections.
	 */
	public final Set<Connection> out;
	/**
	 * Gated connections.
	 */
	public final List<Connection> gated;
	/**
	 * Connection to the node itself.
	 */
	public Connection self;
	/**
	 * The Activation type.
	 */
	public Activation activationType;
	/**
	 * Neuron's bias.
	 *
	 * @see <a href="https://becominghuman.ai/what-is-an-artificial-neuron-8b2e421ce42e">Neuron's
	 *      bias</a>
	 */
	public double bias;
	/**
	 * The Index. -used for crossover
	 */
	public int index;
	/**
	 * The NodeType.
	 */
	public NodeType type;
	/**
	 * Used for dropout. This is either 0 (ignored) or 1 (included) during training and is used to
	 * avoid overfit.
	 */
	public double mask;
	/**
	 * The state of this node.
	 */
	private double state;
	/**
	 * The output value of this node.
	 */
	private double activation;
	/**
	 * Instantiates a new Node.
	 */
	Node() {
		this(NodeType.HIDDEN);
	}
	/**
	 * Instantiates a new Node.
	 *
	 * @param type the type
	 */
	public Node(final NodeType type) {
		this.bias=type==NodeType.INPUT?0:randDouble(-1,1);
		this.activationType=Activation.LOGISTIC;
		this.type=type;
		this.activation=0;
		this.state=0;
		this.mask=1;
		this.index=-1;
		this.in=new HashSet<>();
		this.out=new HashSet<>();
		this.gated=new ArrayList<>();
		this.self=new Connection(this,this,0);
	}
	// /**
	//  * Convert a JsonObject to a node.
	//  *
	//  * @param jsonObject the json object which holds the information
	//  * @return the node created from the information of the json object
	//  */
	// public static  Node fromJSON(final  JsonObject jsonObject) {
	// 	final Node node=new Node();
	// 	node.bias=jsonObject.get("bias").getAsDouble();
	// 	node.type=NodeType.valueOf(jsonObject.get("type").getAsString());
	// 	node.activationType=Activation.valueOf(jsonObject.get("activationType").getAsString());
	// 	node.mask=jsonObject.get("mask").getAsDouble();
	// 	node.index=jsonObject.get("index").getAsInt();
	// 	return node;
	// }
	/**
	 * Activates the node.
	 * <p>
	 * When a neuron activates, it computes its state from all its input connections and 'squashes'
	 * it using its activation function, and returns the output (activation).
	 *
	 * @return A neuron's squashed output value.
	 */
	public double activate() {
		this.state=this.self.gain*this.self.weight*this.state+this.bias;
		this.in.forEach(connection->this.state+=connection.from.activation*connection.weight*connection.gain);
		this.activation=this.activationType.calc(this.state);
		this.gated.forEach(conn->conn.gain=this.activation);
		return this.activation;
	}
	/**
	 * Activates the node with input value.
	 * <p>
	 * When a neuron activates, it computes its state from all its input connections and 'squashes'
	 * it using its activation function, and returns the output (activation).
	 * <p>
	 * You can also provide the activation (a float between 0 and 1) as a parameter, which is useful
	 * for neurons in the input layer.
	 *
	 * @param input the input to this node
	 */
	public void activate(final double input) {
		this.activation=input; // just copy
	}
	/**
	 * Connect this node to another node.
	 *
	 * @param target the target node
	 * @param weight the weight value of the connection between this and the target node
	 * @return the created connection pointing from this node to the target node
	 */
	public Connection connect(final Node target,final double weight) {
		final Connection out;
		if(target==this) {
			if(this.self.weight==0) {
				this.self.weight=weight;
			}
			out=this.self;
		}else {
			Connection connection=this.out.stream()
				.filter(conn->conn.to.equals(target))
				.findAny()
				.orElse(null);
			if(connection==null) {
				connection=new Connection(this,target,weight);
				target.in.add(connection);
				this.out.add(connection);
			}
			out=connection;
		}
		return out;
	}
	/**
	 * Checks if there is no connection between this node and the target node.
	 *
	 * @param target the target node
	 * @return is there no connection
	 */
	public boolean isNotProjectingTo(final Node target) {
		return (!this.equals(target)||this.self.weight==0)
			&&this.out.stream().noneMatch(connection->connection.to.equals(target));
	}
	/**
	 * Resets this node.
	 */
	public void clear() {
		this.gated.forEach(connection->connection.gain=0);
		this.state=0;
		this.activation=0;
	}
	/**
	 * Disconnects this node from the target node.
	 *
	 * @param target the target node
	 */
	public void disconnect(final Node target) {
		if(this.equals(target)) {
			// disconnecting a self connection
			this.self.weight=0; // just set weight of self connection to 0
			return;
		}
		new ArrayList<>(this.out)
			.stream()
			// get connections that points to the target node
			.filter(connection->connection.to.equals(target))
			.forEach(connection-> {
				this.out.remove(connection); // remove connection from outgoing connections list
				target.in.remove(connection); // remove connection from the input of the target node
				if(connection.gateNode!=null) {
					// if connection has gate node -> remove gate
					connection.gateNode.removeGate(connection);
				}
			});
	}
	/**
	 * Stops this node from gating (manipulating) the given connection.
	 *
	 * @param connection Connection to ungate
	 */
	public void removeGate(final Connection connection) {
		this.gated.remove(connection); // remove connection from gated list
		connection.gateNode=null; // set gate node to null
		connection.gain=1; // set connection gain to 1
	}
	/**
	 * Set this node to gate (influences) the given connection.
	 *
	 * @param connection Connection to be gated (influenced) by a node.
	 */
	public void gate(final Connection connection) {
		connection.gateNode=this;
		this.gated.add(connection);
	}
	// /**
	//  * Converts the node to a JsonObject that can later be converted back.
	//  *
	//  * @return the created JsonObject
	//  */
	// public JsonObject toJSON() {
	// 	final JsonObject jsonObject=new JsonObject();
	// 	jsonObject.addProperty("bias",this.bias);
	// 	jsonObject.addProperty("type",this.type.name());
	// 	jsonObject.addProperty("activationType",this.activationType.name());
	// 	jsonObject.addProperty("mask",this.mask);
	// 	jsonObject.addProperty("index",this.index);
	// 	return jsonObject;
	// }
	/**
	 * 警告：AI生成，未验证
	 * </p>
	 * Creates a copy of this node.
	 *
	 * @return the copy of this node
	 */
	public Node copy() {
		Node copy=new Node(this.type);
		copy.bias=this.bias;
		copy.activationType=this.activationType;
		copy.index=this.index;
		copy.mask=this.mask;
		copy.state=this.state;
		copy.activation=this.activation;
		for(Connection connection:this.in) {
			Node from=connection.from.copy();
			Connection newConnection=from.connect(copy,connection.weight);
			newConnection.gain=connection.gain;
		}
		return copy;
	}
	@Override
	public int hashCode() {
		return Objects.hash(this.activationType,this.bias,this.type);
	}
	@Override
	public boolean equals(final Object o) {
		if(this==o) {
			return true;
		}
		if(o==null||this.getClass()!=o.getClass()) {
			return false;
		}
		final Node that=(Node)o;
		return this.index!=-1&&this.index==that.index
			||that.bias==this.bias
				&&this.activationType==that.activationType
				&&this.mask==that.mask;
	}
	@Override
	public String toString() {
		return "Node{"+
			"type="+
			this.type+
			'}';
	}
	/**
	 * Node types.
	 * <p>
	 * Stores all possible types of nodes ("Input", "Hidden", "Output")
	 */
	public enum NodeType{
		/**
		 * Hidden node type.
		 * <p>
		 * Indicates that this node is part of the hidden layer(s).
		 */
		HIDDEN,
		/**
		 * Input node type.
		 * <p>
		 * Indicates that this node is part of the input layer.
		 */
		INPUT,
		/**
		 * Output node type.
		 * <p>
		 * Indicates that this node is part of the output layer.
		 */
		OUTPUT
	}
}
