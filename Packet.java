package sim.app.sensors;
import ec.util.MersenneTwisterFast;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.network.Edge;
import sim.util.Bag;
import sim.util.Double2D;


public class Packet implements Steppable
{
	Double2D loc = new Double2D(0,0);

	public void step(SimState state)
	{
		Nodes nodes = (Nodes) state;
		loc = nodes.packets.getObjectLocation(this);

		MersenneTwisterFast random = new MersenneTwisterFast();
		Bag presentSensors = nodes.ocean.getObjectsAtLocation(loc);

		//Movement mode for now is random walk
		if(!presentSensors.isEmpty())
		{
			int nextIndex = random.nextInt(presentSensors.size());
			Bag immediateNeighbors = nodes.neighbors.getEdges(presentSensors.get(nextIndex), null);

			int nextEdge = random.nextInt(immediateNeighbors.size());
			Edge newEdge = (Edge) immediateNeighbors.get(nextEdge);
			Node newNode = (Node) newEdge.getOtherNode(presentSensors.get(nextIndex));
			
			loc = nodes.ocean.getObjectLocation(newNode);

		}


		nodes.packets.setObjectLocation(this, loc);

	}



}