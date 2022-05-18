package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.common.reports.interfaces.IClassReport;
import io.vertx.core.Future;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetClassReport implements ActionListener {

    private final VisualizerFrame view;

    public GetClassReport(final VisualizerFrame frame) {
        this.view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.view.resetTree();
        this.view.getStopButton().setEnabled(true);
        this.view.getMethodButtons().get("getClassReport").setEnabled(false);
        Future<IClassReport> future = this.view.getLib().getClassReport(this.view.getNameDirectory().getText());
        future.onSuccess(res -> {
            IClassReport report = res;
            this.view.getTreePanel().update(report, this.view.getTreePanel().getRoot());
        });
        future.onFailure(res -> {
            System.out.println("ERROR");
            this.view.errorMessage("Error!!! Select a Java file");
        });
    }
}
