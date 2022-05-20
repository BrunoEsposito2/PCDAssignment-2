package asynchJavaParser.reactiveJavaParser.client;

import asynchJavaParser.common.reports.interfaces.IClassReport;
import asynchJavaParser.common.reports.interfaces.IProjectReport;
import io.reactivex.rxjava3.core.Observable;
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
        this.view.getMethodButtons().get("getProjectReport").setEnabled(false);

        Observable<IProjectReport> ob = this.view.getLib().getProjectReport(this.view.getNameDirectory().getText());
        ob.subscribe(p -> {
            this.view.getTreePanel().update(p, this.view.getTreePanel().getRoot());
        });
    }
}
