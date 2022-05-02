package ass01.asynch;

import ass01.utils.AbstractSimulator;
import ass01.utils.Body;
import ass01.utils.Boundary;
import ass01.utils.SimulationView;
import ass01.view.Controller;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsynchSimulator extends AbstractSimulator{

	private ExecutorService executor;
	private final int nTask;
	private ArrayList<Future<Void>> taskList;
	private final int poolSize;
	
	/*specify how many iterations must pass before update GUI*/
	private final static int UPDATE_FREQUENCY = 2;
	
	/*the optional controller field*/
	private Optional<Controller> controller;
	
	/*the optional viewer*/
	private Optional<SimulationView> viewer;
	
	public AsynchSimulator(ArrayList<Body> bodies, Boundary bounds, int poolSize, Controller c, SimulationView v) {
		super(bodies, bounds);
		this.poolSize = poolSize;
		executor = Executors.newFixedThreadPool(this.poolSize);
		this.nTask = bodies.size();
		this.taskList = new ArrayList<>();
		this.controller = Optional.of(c);
		this.viewer = Optional.of(v);
	}

	public AsynchSimulator(ArrayList<Body> bodies, Boundary bounds, int poolSize) {
		super(bodies, bounds);
		this.poolSize = poolSize;
		executor = Executors.newFixedThreadPool(this.poolSize);
		this.nTask = bodies.size();
		this.taskList = new ArrayList<>();
		this.controller = Optional.empty();
		this.viewer = Optional.empty();
	}

	@Override
	public void execute(long nSteps) {
		/* if there's a controller then wait start */
		if(this.controller.isPresent()) {
			try {
				this.controller.get().m.waitStart();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//reset executor
		executor.shutdown();
		executor = Executors.newFixedThreadPool(this.poolSize);
		
		/* simulation loop */
		while (super.iter < nSteps) {
			if(this.controller.isPresent()) {
			    if(this.controller.get().m.evaluateReset()) {
			    	super.reset();
			    	try {
						this.controller.get().m.waitStart();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			    }
			}

			
			/* update readOnlyList with iter-1 result */
			ArrayList<Body> readOnlyList = new ArrayList<>();
			super.copyAndReplace(super.bodies, readOnlyList);
			
			this.taskList.clear();
			for(Body b : super.bodies) {
				ComputeBody cb = new ComputeBody(readOnlyList, b, dt, bounds);
				this.taskList.add(executor.submit(cb));
			}
			
			for(Future<Void> task : taskList) {
				try {
					task.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
			
			
		    /* update virtual time */
			vt = vt + dt;
			super.iter++;
			
			/* display current stage */
			if(viewer.isPresent() && (iter % UPDATE_FREQUENCY == 0))
				viewer.get().updateView(bodies, vt, iter, bounds);
		}
		
		/* change of GUI and button states when simulation ends without user interaction on the GUI */
		if(viewer.isPresent()) {
			viewer.get().updateState("Terminated");
			super.reset();
			this.execute(nSteps);
		}
		
	}
	
	public ArrayList<Body> getBodies() {
		return super.bodies;
	}

}
