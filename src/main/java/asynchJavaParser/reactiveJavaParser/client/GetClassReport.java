package asynchJavaParser.reactiveJavaParser.client;

import asynchJavaParser.common.reports.interfaces.IClassReport;
import asynchJavaParser.reactiveJavaParser.ProjectStructure;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
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
        this.view.getMethodButtons().get("getClassReport").setEnabled(false);

        Observable<IClassReport> ob = this.view.getLib().getClassReport(this.view.getNameDirectory().getText());
        ob.subscribe(p -> {
            this.view.getTreePanel().update(p, this.view.getTreePanel().getRoot());
        });
    }
}
