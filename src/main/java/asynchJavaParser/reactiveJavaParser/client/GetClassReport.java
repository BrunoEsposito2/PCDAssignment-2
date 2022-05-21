package asynchJavaParser.reactiveJavaParser.client;

import asynchJavaParser.common.reports.interfaces.IClassReport;
import io.reactivex.rxjava3.core.Observable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetClassReport implements ActionListener {

    private final VisualizerFrame view;

    public GetClassReport(final VisualizerFrame frame) {
        this.view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.view.getTreeView().setVisible(true);
        this.view.resetTree();
        this.view.getMethodButtons().get("getClassReport").setEnabled(false);

        Observable<IClassReport> ob = this.view.getLib().getClassReport(this.view.getNameDirectory().getText());
        ob.subscribe(p -> {
            this.view.getTreePanel().updateClassReport(p, this.view.getTreePanel().getRoot());
        });
    }
}
