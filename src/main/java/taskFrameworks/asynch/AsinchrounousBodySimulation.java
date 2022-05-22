package taskFrameworks.asynch;

import taskFrameworks.utils.Body;
import taskFrameworks.utils.BodyGenerator;
import taskFrameworks.utils.Boundary;
import taskFrameworks.utils.SimulationView;
import taskFrameworks.view.Controller;

import java.util.ArrayList;

/**
 * Bodies simulation - legacy code: concurrent
 * 
 */
public class AsinchrounousBodySimulation {
	
	public static void main(String[] args) {
		
		Boundary bounds =  new Boundary(-6.0, -6.0, 6.0, 6.0);
		BodyGenerator bg = new BodyGenerator();
		ArrayList<Body> bodies = bg.generateBodies(1000, bounds);
		
		SimulationView viewer = new SimulationView(620,620);
		
		Controller controller = new Controller();
		int poolSize = Runtime.getRuntime().availableProcessors() + 1;
		//ArrayList<Body> bodies, Boundary bounds, int poolSize, Controller c, SimulationView v) 
		
    	AsynchSimulator sim = new AsynchSimulator( bodies, bounds, poolSize, controller, viewer);

        viewer.addListener(controller);
        viewer.display();
        sim.execute(10000);
    }
}
