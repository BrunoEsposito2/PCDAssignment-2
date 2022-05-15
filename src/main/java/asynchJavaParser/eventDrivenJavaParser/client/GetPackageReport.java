package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.common.reports.interfaces.IPackageReport;
import io.vertx.core.Future;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetPackageReport implements ActionListener {

    private VisualizerFrame view;

    public GetPackageReport(VisualizerFrame frame){
        view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.resetTree();
        view.getStopButton().setEnabled(true);
        view.getMethodButtons().get("getPackageReport").setEnabled(false);
        Future<IPackageReport> future = view.getLib().getPackageReport(view.getNameDirectory().getText());
        future.onSuccess(res -> {
            IPackageReport report = res;
            view.getTreePanel().update(report, view.getTreePanel().getRoot());
        });
        future.onFailure(res -> {
            System.out.println("ERROR");
            view.errorMessage();
        });
    }
}
