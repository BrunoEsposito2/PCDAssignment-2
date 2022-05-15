package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.common.reports.interfaces.IProjectReport;
import io.vertx.core.Future;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetProjectReport implements ActionListener {

    private VisualizerFrame view;

    public GetProjectReport(VisualizerFrame frame){
        view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.resetTree();
        view.getStopButton().setEnabled(true);
        view.getMethodButtons().get("getProjectReport").setEnabled(false);
        Future<IProjectReport> future = view.getLib().getProjectReport(view.getNameDirectory().getText());
        future.onSuccess(res -> {
            IProjectReport report = res;
            view.getTreePanel().update(report, view.getTreePanel().getRoot());
        });
        future.onFailure(res -> {
            System.out.println("ERROR");
            view.errorMessage();
        });
    }
}
