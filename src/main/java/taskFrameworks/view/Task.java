package taskFrameworks.view;

import java.util.ArrayList;

import taskFrameworks.utils.Body;

public class Task {

	private ArrayList<Body> bodies;
	
	public Task(final ArrayList<Body> bodies) {
		this.bodies = bodies;
	}
	
	public ArrayList<Body> getBodies() {
		return this.bodies;
	}
	
}
