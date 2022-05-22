package asynchJavaParser.reactiveJavaParser.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class to handle the action on stopEvents button
 * */
public class StopEvents implements ActionListener {

    private final VisualizerFrame view;

    public StopEvents(final VisualizerFrame frame) {
        this.view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.view.getStopButton().setEnabled(false);
        this.view.getAnalyzeProject().getDisposable().dispose();
    }
}
