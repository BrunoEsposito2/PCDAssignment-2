package asynchJavaParser.reactiveJavaParser.client;

import asynchJavaParser.common.reports.interfaces.IClassReport;
import asynchJavaParser.common.reports.interfaces.IPackageReport;
import io.reactivex.rxjava3.core.Observable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetPackageReport implements ActionListener {

    private final VisualizerFrame view;

    public GetPackageReport(final VisualizerFrame frame) {
        this.view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String path = this.view.getNameDirectory().getText();
        this.view.getTreeView().setVisible(true);
        this.view.resetTree();
        this.view.getMethodButtons().get("getPackageReport").setEnabled(false);

        if(!path.contains(".java")){
            Observable<IPackageReport> ob = this.view.getLib().getPackageReport(path);
            ob.subscribe(p -> {
                this.view.getTreePanel().updatePackageReport(p, this.view.getTreePanel().getRoot());
            });
        }else{
            this.view.errorMessage("Error!!! Select a package");
        }
    }
}
