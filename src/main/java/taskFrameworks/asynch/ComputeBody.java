package taskFrameworks.asynch;

import taskFrameworks.utils.Body;
import taskFrameworks.utils.Boundary;
import taskFrameworks.utils.V2d;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class ComputeBody implements Callable<Void>  {

	private final ArrayList<Body> bodiesView;
	private final Body toCompute;
	private final Boundary bounds;
	private final double dt;
	
	public ComputeBody(ArrayList<Body> bodiesView, Body toCompute, double dt, Boundary bounds) {
		this.bodiesView = bodiesView;
		this.toCompute = toCompute;
		this.bounds = bounds;
		this.dt = dt;
	}
	
	@Override
	public Void call() throws Exception {
		/* compute total force on bodies */
        V2d totalForce = computeTotalForceOnBody(toCompute);
        
        /* compute instant acceleration */
        V2d acc = new V2d(totalForce).scalarMul(1.0 / toCompute.getMass());

        /* update velocity */
        toCompute.updateVelocity(acc, dt);
		
		/* compute bodies new pos */
	    toCompute.updatePos(dt);
       
        /* check collisions with boundaries */
        toCompute.checkAndSolveBoundaryCollision(bounds);
		return null;
	}
	
	 private V2d computeTotalForceOnBody(Body b) {
	        V2d totalForce = new V2d(0, 0);
	     
	        /* compute total repulsive force */
	        for (int j = 0; j < this.bodiesView.size(); j++) {
	        	Body otherBody = this.bodiesView.get(j);
	            if (!b.equals(otherBody)) {
	            	try {
	            		V2d forceByOtherBody = b.computeRepulsiveForceBy(otherBody);
	                    totalForce.sum(forceByOtherBody);
	                } catch (Exception ex) { 
	                	
	                }
	            }
	        }
	        /* add friction force */
	        totalForce.sum(b.getCurrentFrictionForce());
	        return totalForce;
	 }

}
