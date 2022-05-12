package asynchJavaParser.eventDrivenJavaParser.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopEvents implements ActionListener {

    private VisualizerFrame view;

    public StopEvents(VisualizerFrame frame){
        view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.getStopButton().setEnabled(false);
        view.resetView();
        view.getLib().stopAnalyzeProject();
    }
}
