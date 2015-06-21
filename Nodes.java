package sim.app.sensors;
import sim.engine.*;
import sim.util.*;
import sim.field.continuous.*;
import sim.field.network.*;

public class Nodes extends SimState
{
	public Continuous2D ocean = new Continuous2D(1.0,150,150);
	public Continuous2D packets = new Continuous2D(1.0,150,150);
	public Network neighbors = new Network(false);
	public int gridSide = 15;


	public Nodes(long seed)
	{
		super(seed);
	}
	public void start()
	{
		super.start();
		ocean.clear();
		neighbors.clear();
		packets.clear();

		Packet packet = new Packet();
		packets.setObjectLocation(packet, new Double2D((15 + ocean.width * -0.05 ), 
				(15 + ocean.height * -0.05)));
		schedule.scheduleRepeating(packet);

		// Create and Set nodes initial position, and add them to the network
		for(int i = 0; i< gridSide; i++)
		{
			for(int j = 0; j < gridSide; j++)
			{
				Node node = new Node();
				node.setGridCoord(i,j);

				ocean.setObjectLocation(node, new Double2D((15 + ocean.width * -0.05 + (i*5)), 
						(15 + ocean.height * -0.05 + (j*5))) );
				schedule.scheduleRepeating(node);
				neighbors.addNode(node);
			}
		}

		Bag nodes = neighbors.getAllNodes();
		for(int i = 0; i < nodes.size(); i++)
		{
			Node nodeA = (Node) nodes.get(i);
			double nodeAX = nodeA.getGridCoord().getX();
			double nodeAY = nodeA.getGridCoord().getY();

			Bag neighborNodes = ocean.getNeighborsExactlyWithinDistance(ocean.getObjectLocation(nodeA), 10, false);
			for(int j = 0; j < neighborNodes.size(); j++)
			{
				if(!neighborNodes.get(j).equals(packet)){
					Node nodeB = ((Node) neighborNodes.get(j));

					double nodeBX = nodeB.getGridCoord().getX();
					double nodeBY = nodeB.getGridCoord().getY();


					if((nodeAX + 1 == nodeBX || nodeAX - 1 == nodeBX) && (nodeAY + 1 == nodeBY || nodeAY - 1 == nodeBY)
							|| ((nodeAX + 1 == nodeBX)|| (nodeAX - 1 == nodeBX)) && nodeAY == nodeBY
							|| ((nodeAY + 1 == nodeBY)|| (nodeAY - 1 == nodeBY)) && nodeAX == nodeBX)
					{
						if(neighbors.getEdge(nodeA, nodeB) == null)
						{
							neighbors.addEdge(nodeA, nodeB, null);
						}
					}
				}
			}

		}



	}


	public static void main(String[] args)
	{
		doLoop(Nodes.class, args);
		System.exit(0);
	}
}