package pama1234.gdx.game.neat.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Mutation methods.
 *
 * @author Manuel Raimann
 */
public enum Mutation{
  /**
   * ADD NODE.
   * <p>
   * Adds a node to the network
   */
  ADD_NODE(false) {
    @Override
    public void mutate(final Genome network) {
      if(network.connections.isEmpty()) {
        return; // there must be some connections
      }
      //disconnect a random connection to put the node in
      final Connection connection=Utils.pickRandom(network.connections);
      network.disconnect(connection.from,connection.to);
      // create a new hidden node and add it to network.nodes
      final Node node=new Node(Node.NodeType.HIDDEN);
      node.activationType=Utils.pickRandom(MOD_ACTIVATION.allowed);
      network.nodes.add(Math.max(0,Math.min(network.nodes.indexOf(connection.to),network.nodes.size()-network.output)),node);
      network.setNodeIndices();
      if(connection.gateNode!=null) {
        // if connection had gate node
        // gate with the new node
        network.gate(connection.gateNode,Utils.randBoolean()
          ?network.connect(connection.from,node)
          :network.connect(node,connection.to));
      }
    }
  },
  /**
   * SUB NODE.
   * <p>
   * Removes a node from the network
   */
  SUB_NODE(false) {
    @Override
    public void mutate(final Genome network) {
      // get all hidden nodes
      final List<Node> hiddenNodes=network.nodes
        .stream()
        .filter(node->node.type==Node.NodeType.HIDDEN)
        .collect(Collectors.toList());
      if(!hiddenNodes.isEmpty()) {
        // if there are hidden nodes
        // remove a random node
        network.remove(Utils.pickRandom(hiddenNodes));
      }
    }
  },
  /**
   * ADD CONN.
   * <p>
   * Adds a connection to the network
   */
  ADD_CONN(false) {
    @Override
    public void mutate(final Genome network) {
      final List<Node[]> availableNodes=new ArrayList<>();
      for(int i=0;i<network.nodes.size()-network.output;i++) { // iterate over input and hidden nodes
        final Node from=network.nodes.get(i);
        for(int j=Math.max(i+1,network.input);j<network.nodes.size();j++) { // iterate over hidden and output nodes starting from index one more than outer loop (forwarding)
          final Node to=network.nodes.get(j);
          if(from.isNotProjectingTo(to)) {
            availableNodes.add(new Node[] {from,to}); // add as an pair
          }
        }
      }
      if(!availableNodes.isEmpty()) {
        final Node[] pair=Utils.pickRandom(availableNodes);
        network.connect(pair[0],pair[1]);
      }
    }
  },
  /**
   * SUB CONN.
   * <p>
   * Removes a connection from the network
   */
  SUB_CONN(false) {
    @Override
    public void mutate(final Genome network) {
      final List<Connection> availableConnections=network.connections.stream()
        .filter(conn->!conn.from.in.isEmpty()) // from should have incoming connections
        .filter(conn->!conn.to.out.isEmpty()) // to should have outgoing connections
        .filter(conn->network.nodes.indexOf(conn.to)>network.nodes.indexOf(conn.from)) // should be forwarding
        .collect(Collectors.toList());
      if(!availableConnections.isEmpty()) {
        // if there is a connection
        // disconnect it
        final Connection randomConn=Utils.pickRandom(availableConnections);
        network.disconnect(randomConn.from,randomConn.to);
      }
    }
  },
  /**
   * MOD WEIGHT.
   * <p>
   * Modifies the weight of a connection
   */
  MOD_WEIGHT(false) {
    @Override
    public void mutate(final Genome network) {
      // Create a list with all connections
      final ArrayList<Connection> allConnections=new ArrayList<>(network.connections);
      allConnections.addAll(network.selfConnections);
      if(!allConnections.isEmpty()) {
        // if there are connections
        // modify the weight of a random one
        Utils.pickRandom(allConnections).weight+=Utils.randDouble(this.min,this.max);
      }
    }
  },
  /**
   * MOD BIAS.
   * <p>
   * Modifies the bias of a node
   */
  MOD_BIAS(false) {
    @Override
    public void mutate(final Genome network) {
      // pick a random node hidden or output node and modify it's bias
      network.nodes.get(Utils.randInt(network.input,network.nodes.size())).bias+=Utils.randDouble(this.min,this.max);
    }
  },
  /**
   * MOD ACTIVATION.
   * <p>
   * Modifies the activation function of a node
   */
  MOD_ACTIVATION(Activation.values()) {
    @Override
    public void mutate(final Genome network) {
      if(this.mutateOutput) {
        // if output nodes can also be mutated
        // pick random hidden or output node and mutate activation
        network.nodes.get(Utils.randInt(network.input,network.nodes.size())).activationType=Utils.pickRandom(MOD_ACTIVATION.allowed);
      }else if(network.nodes.size()-network.output-network.input>0) {
        // if there were hidden nodes
        // pick random hidden node and mutate activation
        network.nodes.get(Utils.randInt(network.input,network.nodes.size()-network.output)).activationType=Utils.pickRandom(MOD_ACTIVATION.allowed);
      }
    }
  },
  /**
   * ADD SELF CONN.
   * <p>
   * Adds a connection to a node itself
   */
  ADD_SELF_CONN(false) {
    @Override
    public void mutate(final Genome network) {
      final List<Node> availableNodes=network.nodes.stream()
        .filter(node->node.type!=Node.NodeType.INPUT) // no input nodes allowed
        .filter(node->node.self.weight==0) // only nodes without a self connection allowed
        .collect(Collectors.toList());
      if(!availableNodes.isEmpty()) {
        // if there are available nodes
        // pick a random and connect it to itself
        final Node node=Utils.pickRandom(availableNodes);
        network.connect(node,node);
      }
    }
  },
  /**
   * SUB SELF CONNECTION.
   * <p>
   * Removes a connection to a node itself
   */
  SUB_SELF_CONN(false) {
    @Override
    public void mutate(final Genome network) {
      if(!network.selfConnections.isEmpty()) {
        // if there are self connections
        // pick a random and disconnect it
        final Connection connection=Utils.pickRandom(network.selfConnections);
        network.disconnect(connection.from,connection.to);
      }
    }
  },
  /**
   * ADD GATE.
   * <p>
   * Add a gate to the network
   */
  ADD_GATE(false) {
    @Override
    public void mutate(final Genome network) {
      final List<Connection> availableConnections=Stream.concat(network.connections.stream(),network.selfConnections.stream()) // all connections
        .filter(connection->connection.gateNode==null) // only connections without gate node are allowed
        .collect(Collectors.toList());
      if(!availableConnections.isEmpty()) {
        // if there are connections
        // pick a random connection and gate it with a random hidden or output node
        network.gate(network.nodes.get(Utils.randInt(network.input,network.nodes.size())),Utils.pickRandom(availableConnections));
      }
    }
  },
  /**
   * SUB GATE.
   * <p>
   * Removes a gate from the network
   */
  SUB_GATE(false) {
    @Override
    public void mutate(final Genome network) {
      if(!network.gates.isEmpty()) {
        // if there are gates in the network
        // pick a random and remove it
        network.removeGate(Utils.pickRandom(network.gates));
      }
    }
  },
  /**
   * ADD BACK CONN.
   * <p>
   * Adds a backward pointing connection
   */
  ADD_BACK_CONN(false) {
    @Override
    public void mutate(final Genome network) {
      final ArrayList<Node[]> availableNodes=new ArrayList<>();
      for(int i=network.input;i<network.nodes.size();i++) { // iterate over all hidden and output nodes
        final Node from=network.nodes.get(i);
        for(int j=network.input;j<i;j++) { // iterate over all hidden and output nodes with index less than outer loop (backwarding)
          final Node to=network.nodes.get(j);
          if(from.isNotProjectingTo(to)) {
            availableNodes.add(new Node[] {from,to}); // store the pair of nodes
          }
        }
      }
      if(!availableNodes.isEmpty()) {
        // if there are pair of nodes
        // pick a random one and connect them
        final Node[] pair=Utils.pickRandom(availableNodes);
        network.connect(pair[0],pair[1]);
      }
    }
  },
  /**
   * SUB BACK CONN
   * <p>
   * Removes a backward pointing connection
   */
  SUB_BACK_CONN(false) {
    @Override
    public void mutate(final Genome network) {
      final List<Connection> availableConnections=network.connections.stream()
        .filter(connection->!connection.from.out.isEmpty()) // from should have outgoing connections
        .filter(connection->!connection.to.in.isEmpty()) // to should have incoming connections
        .filter(connection->network.nodes.indexOf(connection.from)>network.nodes.indexOf(connection.to)) // should be backwarding
        .collect(Collectors.toList());
      if(!availableConnections.isEmpty()) {
        // if there are available connections
        // pick a random and disconnect it
        final Connection randomConn=Utils.pickRandom(availableConnections);
        network.disconnect(randomConn.from,randomConn.to);
      }
    }
  },
  /**
   * SWAP NODES
   * <p>
   * Swaps to nodes inside a network
   */
  SWAP_NODES(true) {
    @Override
    public void mutate(final Genome network) {
      if(this.mutateOutput&&network.nodes.size()-network.input<2) {
        // if mutating output
        // there should be at least 2 hidden and output nodes
        return;
      }else if(!this.mutateOutput&&network.nodes.size()-network.input-network.output<2) {
        // if not mutating output
        // there should be at least 2 hidden nodes
        return;
      }
      final int index;
      final int index2;
      if(this.mutateOutput) {
        // pick random hidden or output node indices
        index=Utils.randInt(network.input,network.nodes.size());
        index2=Utils.randInt(network.input,network.nodes.size());
      }else {
        // pick random hidden node indices
        index=Utils.randInt(network.input,network.nodes.size()-network.output);
        index2=Utils.randInt(network.input,network.nodes.size()-network.output);
      }
      // get nodes from indices
      final Node node=network.nodes.get(index);
      final Node node2=network.nodes.get(index2);
      // Swap bias and activationType of the nodes
      final float biasTemp=node.bias;
      final Activation activationTypeTemp=node.activationType;
      node.bias=node2.bias;
      node.activationType=node2.activationType;
      node2.bias=biasTemp;
      node2.activationType=activationTypeTemp;
    }
  };
  /**
   * Contains all possible mutations.
   */
  public static final Mutation[] ALL=Mutation.values();
  /**
   * Contains all feedforward mutations.
   */
  public static final Mutation[] FFW=new Mutation[] {
    ADD_CONN,
    ADD_NODE,
    MOD_ACTIVATION,
    MOD_BIAS,
    MOD_WEIGHT,
    SUB_CONN,
    SUB_NODE,
    SWAP_NODES
  };
  /**
   * Should the output be mutated?
   */
  public final boolean mutateOutput;
  /**
   * All allowed ActivationTypes.
   */
  public Activation[] allowed;
  /**
   * The min bias/weight.
   */
  public int min;
  /**
   * The max bias/weight.
   */
  public int max;
  /**
   * Should gates be kept?
   */
  public boolean keepGates;
  /**
   * Instantiates a new Mutation.
   *
   * @param mutateOutput Should the output be mutated?
   */
  Mutation(final boolean mutateOutput) {
    this.min=-1;
    this.max=1;
    this.mutateOutput=mutateOutput;
    this.keepGates=!mutateOutput;
  }
  /**
   * Instantiates a new Mutation.
   *
   * @param allowed All allowed ActivationTypes.
   */
  Mutation(final Activation[] allowed) {
    this.mutateOutput=true;
    this.allowed=allowed;
  }
  /**
   * Mutate a network with the enum method.
   *
   * @param network the network
   */
  public abstract void mutate(Genome network);
  /**
   * Sets allowed activations.
   *
   * @param allowedActivations the allowed activations
   */
  public void setAllowed(final Activation[] allowedActivations) {
    this.allowed=allowedActivations;
  }
  /**
   * Sets min bias/weight.
   *
   * @param min the min bias/weight
   */
  public void setMin(final int min) {
    this.min=min;
  }
  /**
   * Sets max bias/weight.
   *
   * @param max the max bias/weight
   */
  public void setMax(final int max) {
    this.max=max;
  }
  /**
   * Sets keep gates.
   *
   * @param keepGates the keep gates
   */
  public void setKeepGates(final boolean keepGates) {
    this.keepGates=keepGates;
  }
}