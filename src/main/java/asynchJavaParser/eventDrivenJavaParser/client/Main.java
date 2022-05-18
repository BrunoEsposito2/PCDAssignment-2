package asynchJavaParser.eventDrivenJavaParser.client;

import io.vertx.core.Vertx;

import javax.swing.*;

public class Main extends JPanel {

    public static void main(String[] argv) {
        Vertx vertx = Vertx.vertx();
        VisualizerFrame view = new VisualizerFrame(vertx);
        view.display();
    }
}

