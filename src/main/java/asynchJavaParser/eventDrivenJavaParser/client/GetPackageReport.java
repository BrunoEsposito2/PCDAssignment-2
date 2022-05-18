package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.common.reports.interfaces.IPackageReport;
import io.vertx.core.Future;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetPackageReport implements ActionListener {

    private final VisualizerFrame view;

    public GetPackageReport(final VisualizerFrame frame) {
        this.view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.view.resetTree();
        this.view.getStopButton().setEnabled(true);
        this.view.getMethodButtons().get("getPackageReport").setEnabled(false);
        Future<IPackageReport> future = this.view.getLib().getPackageReport(this.view.getNameDirectory().getText());
        future.onSuccess(res -> {
            IPackageReport report = res;
            this.view.getTreePanel().update(report, this.view.getTreePanel().getRoot());
        });
        future.onFailure(res -> {
            System.out.println("ERROR");
            this.view.errorMessage("Error!!! Select a package");
        });
    }
}
