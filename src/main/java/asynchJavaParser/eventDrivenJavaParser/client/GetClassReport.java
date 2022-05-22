package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.common.reports.interfaces.IClassReport;
import io.vertx.core.Future;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class to handle the action on getClassReport button
 * calling the getClassReport library function
 * */
public class GetClassReport implements ActionListener {

    private final VisualizerFrame view;

    public GetClassReport(final VisualizerFrame frame) {
        this.view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.view.resetTree();
        this.view.getStopButton().setEnabled(false);
        this.view.getMethodButtons().get("getClassReport").setEnabled(false);
        Future<IClassReport> future = this.view.getLib().getClassReport(this.view.getNameDirectory().getText());
        future.onSuccess(res -> {
            IClassReport report = res;
            this.view.getTreePanel().updateClassReport(report, this.view.getTreePanel().getRoot());
        });
        future.onFailure(res -> {
            this.view.errorMessage("Error!!! Select a Java file");
        });
    }
}
