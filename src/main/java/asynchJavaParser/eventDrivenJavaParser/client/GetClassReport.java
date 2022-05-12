package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IClassReport;
import io.vertx.core.Future;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetClassReport implements ActionListener {

    private VisualizerFrame view;

    public GetClassReport(VisualizerFrame frame){
        view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.resetTree();
        view.getStopButton().setEnabled(true);
        view.getMethodButtons().get("getClassReport").setEnabled(false);
        Future<IClassReport> future = view.getLib().getClassReport(view.getNameDirectory().getText());
        future.onComplete(res -> {
            IClassReport report = res.result();
            view.getTreePanel().update(report, view.getTreePanel().getRoot());
        });
        future.onFailure(res -> {
            System.out.println("ERROR");
            view.errorMessage();
        });
    }
}
