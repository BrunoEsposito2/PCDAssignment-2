package asynchJavaParser.eventDrivenJavaParser.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopEvents implements ActionListener {

    private final VisualizerFrame view;

    public StopEvents(final VisualizerFrame frame) {
        this.view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.view.getStopButton().setEnabled(false);
        this.view.resetView();
        this.view.getLib().stopAnalyzeProject();
    }
}
