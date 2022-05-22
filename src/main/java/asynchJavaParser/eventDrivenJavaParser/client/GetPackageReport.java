package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.common.reports.interfaces.IPackageReport;
import io.vertx.core.Future;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class to handle the action on getPackageReport button
 * calling the getPackageReport library function
 * */
public class GetPackageReport implements ActionListener {

    private final VisualizerFrame view;

    public GetPackageReport(final VisualizerFrame frame) {
        this.view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.view.resetTree();
        this.view.getStopButton().setEnabled(false);
        this.view.getMethodButtons().get("getPackageReport").setEnabled(false);
        Future<IPackageReport> future = this.view.getLib().getPackageReport(this.view.getNameDirectory().getText());
        future.onSuccess(res -> {
            IPackageReport report = res;
            this.view.getTreePanel().updatePackageReport(report, this.view.getTreePanel().getRoot());
        });
        future.onFailure(res -> {
            this.view.errorMessage("Error!!! Select a package");
        });
    }
}
