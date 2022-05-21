package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.common.reports.interfaces.IProjectReport;
import io.vertx.core.Future;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetProjectReport implements ActionListener {

    private final VisualizerFrame view;

    public GetProjectReport(final VisualizerFrame frame) {
        this.view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.view.resetTree();
        this.view.getStopButton().setEnabled(true);
        this.view.getMethodButtons().get("getProjectReport").setEnabled(false);
        Future<IProjectReport> future = this.view.getLib().getProjectReport(this.view.getNameDirectory().getText());
        future.onSuccess(res -> {
            IProjectReport report = res;
            this.view.getTreePanel().updateProjectReport(report, this.view.getTreePanel().getRoot());
        });
        future.onFailure(res -> {
            this.view.errorMessage("Error!!! Select a project");
        });
    }
}
