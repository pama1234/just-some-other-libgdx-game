package pama1234.gdx.game.neat.util.raimannma.architecture;

import java.util.List;
import java.util.Objects;

import com.google.gson.JsonObject;

/**
 * The type Connection.
 *
 * @author Manuel Raimann
 */
public class Connection{
	/**
	 * The output node of this connection.
	 */
	public final Node to;
	/**
	 * The input node of this connection.
	 */
	public final Node from;
	/**
	 * The node which gates this connection.
	 */
	public Node gateNode;
	/**
	 * The weight of this connection.
	 */
	public float weight;
	/**
	 * The connection gain.
	 * <p>
	 * Used for gating, gets multiplied with weight
	 */
	public float gain;
	/**
	 * Instantiates a new Connection.
	 *
	 * @param from   The input node of this connection.
	 * @param to     The output node of this connection.
	 * @param weight The weight of this connection.
	 */
	Connection(final Node from,final Node to,final float weight) {
		this.from=from;
		this.to=to;
		this.gain=1;
		this.weight=weight;
		this.gateNode=null;
	}
	/**
	 * Get innovation ID. Encode two integers into a single integer.
	 *
	 * @param a the first integer
	 * @param b the second integer
	 * @return An Integer that uniquely represents a pair of Integers
	 * @see <a href="https://en.wikipedia.org/wiki/Pairing_function">(Cantor pairing
	 *      function)|Pairing function (Cantor pairing function)}</a>
	 */
	public static int getInnovationID(final int a,final int b) {
		return (int)Math.floor(0.5*(a+b)*(a+b+1)+b);
	}
	/**
	 * Convert a JsonObject to a connection.
	 *
	 * @param jsonObject the json object which holds the information
	 * @param nodes      will be used to get the from, to and gate node from
	 * @return the connection created from the information of the json object
	 */
	public static Connection fromJSON(final JsonObject jsonObject,final List<Node> nodes) {
		final int fromIndex=jsonObject.get("from").getAsInt(); // get from index from JSON
		final int toIndex=jsonObject.get("to").getAsInt(); // get to index from JSON
		final Node from=nodes.stream().filter(node->node.index==fromIndex).findAny().orElseThrow(); // get from node by searching for the index in nodes list
		final Node to=nodes.stream().filter(node->node.index==toIndex).findAny().orElseThrow(); // get to node by searching for the index in nodes list
		final float weight=jsonObject.get("weight").getAsFloat(); // get weight from JSON
		final Connection connection=new Connection(from,to,weight); // create new connection with from, to and weight
		if(jsonObject.has("gateNode")) {
			final int gateNodeIndex=jsonObject.get("gateNode").getAsInt(); // get gate node index from JSON
			connection.gateNode=nodes.stream().filter(node->node.index==gateNodeIndex).findAny().orElseThrow(); // get gate node by searching for the index in the nodes list
			connection.gain=jsonObject.get("gain").getAsFloat(); // get gain from JSON
		}
		return connection;
	}
	/**
	 * Stores connection data in an array. Used for crossover.
	 * <p>
	 * ConnectionData [0] weight value [1] node from index [2] node to index [3] gate node index
	 *
	 * @return array containing information about the connection
	 */
	protected float[] getConnectionData() {
		final float[] data=new float[4];
		data[0]=this.weight;
		data[1]=(float)this.from.index;
		data[2]=(float)this.to.index;
		data[3]=this.gateNode!=null?this.gateNode.index:Float.NaN;
		return data;
	}
	/**
	 * Converts the connection to a JsonObject that can later be converted back.
	 *
	 * @return the created JsonObject
	 */
	public JsonObject toJSON() {
		//assume node.index been set
		final JsonObject jsonObject=new JsonObject();
		jsonObject.addProperty("from",this.from.index); // add index of from node
		jsonObject.addProperty("to",this.to.index); // add index of to index
		jsonObject.addProperty("weight",this.weight); // add weight
		if(this.isGated()) {
			jsonObject.addProperty("gateNode",this.gateNode.index); // add index of gate node
			jsonObject.addProperty("gain",this.gain); // add gain
		}
		return jsonObject;
	}
	@Override
	public boolean equals(final Object o) {
		if(this==o) {
			return true;
		}
		if(o==null||this.getClass()!=o.getClass()) {
			return false;
		}
		final Connection that=(Connection)o;
		return this.from.index==that.from.index&&this.to.index==that.to.index;
	}
	@Override
	public int hashCode() {
		return Objects.hash(this.to.index,this.from.index);
	}
	@Override
	public String toString() {
		return "architecture.Connection{"+
			"from="+
			this.from.index+
			", to="+
			this.to.index+
			", weight="+
			this.weight+
			", isGated()="+
			this.isGated()+
			'}';
	}
	public boolean isSelfConnection() {
		return this.from.equals(this.to);
	}
	public boolean isGated() {
		return this.gateNode!=null;
	}
}