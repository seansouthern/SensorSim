package sim.app.sensors;

import sim.engine.*;
import sim.util.*;

public class Node implements Steppable
{
	public Double2D gridCoord = new Double2D(0,0);
	public Double2D loc = new Double2D(0,0);

	public void step(SimState state)
	{
		
	}

	public Double2D getGridCoord()
	{
		return gridCoord;
	}
	public void setGridCoord(double x, double y)
	{
		gridCoord = new Double2D(x,y);
	}

}