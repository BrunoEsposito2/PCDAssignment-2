import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import taskFrameworks.asynch.AsynchSimulator;
import taskFrameworks.utils.Body;
import taskFrameworks.utils.BodyGenerator;
import taskFrameworks.utils.Boundary;

import java.time.Duration;
import java.util.ArrayList;

public class AsynchronousBodySimulationTest {

    @Test
    public void test100bodies1000steps() {
        Boundary bounds =  new Boundary(-6.0, -6.0, 6.0, 6.0);
        BodyGenerator bg = new BodyGenerator();
        ArrayList<Body> bodies = bg.generateBodies(100, bounds);

        int poolSize = Runtime.getRuntime().availableProcessors() + 1;

        AsynchSimulator sim = new AsynchSimulator(bodies, bounds, poolSize);

        Assertions.assertTimeout(Duration.ofMillis(500), () -> sim.execute(1000));
    }

}
