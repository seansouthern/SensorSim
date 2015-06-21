package sim.app.sensors;

import sim.portrayal.SimplePortrayal2D;
import sim.portrayal.network.*;
import sim.portrayal.continuous.*;
import sim.engine.*;
import sim.app.networktest.NetworkTest;
import sim.display.*;
import sim.portrayal.simple.*;

import javax.swing.*;

import java.awt.Color;

public class NodesWithUI extends GUIState
{
	public Display2D display;
	public JFrame displayFrame;
	ContinuousPortrayal2D nodesPortrayal = new ContinuousPortrayal2D();
	ContinuousPortrayal2D packetsPortrayal = new ContinuousPortrayal2D();
	NetworkPortrayal2D edgesPortrayal = new NetworkPortrayal2D();


	public static void main(String[] args)
	{
		NodesWithUI vid = new NodesWithUI();
		Console c = new Console(vid);
		c.setVisible(true);
	}
	public NodesWithUI()
	{ 
		super(new Nodes(System.currentTimeMillis())); 
	}
	public NodesWithUI(SimState state) 
	{ 
		super(state); 
	}
	public static String getName() 
	{ 
		return "Ocean Sensor Network"; 
	}

	public void start()
	{
		super.start();
		setupPortrayals();
	}
	public void load(SimState state)
	{
		super.load(state);
		setupPortrayals();
	}
	public void setupPortrayals() 
	{
		Nodes nodes = (Nodes) state;


		nodesPortrayal.setField(nodes.ocean);
		
		
		edgesPortrayal.setField( new SpatialNetwork2D( nodes.ocean, nodes.neighbors ) );
		SimpleEdgePortrayal2D e = new SimpleEdgePortrayal2D(Color.lightGray, Color.lightGray, Color.black);
		e.setBaseWidth(1);
		edgesPortrayal.setPortrayalForAll(e);

		packetsPortrayal.setField(nodes.packets);
		SimplePortrayal2D p = new RectanglePortrayal2D(Color.red);
		packetsPortrayal.setPortrayalForAll(p);

		// reschedule the displayer
		display.reset();
		display.setBackdrop(Color.white);
		// redraw the display
		display.repaint();
	}
	public void init(Controller c)
	{
		super.init(c);
		display = new Display2D(600,600,this);
		display.setClipping(false);
		displayFrame = display.createFrame();
		displayFrame.setTitle("Sensor Display");
		c.registerFrame(displayFrame);
		// so the frame appears in the "Display" list
		displayFrame.setVisible(true);
		display.attach( edgesPortrayal, "Neighbors");
		display.attach( nodesPortrayal, "Ocean" );
		display.attach( packetsPortrayal, "Packets");
	}
	public void quit()
	{
		super.quit();
		if (displayFrame!=null) displayFrame.dispose();
		displayFrame = null;
		display = null;
	}




}