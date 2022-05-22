package asynchJavaParser.reactiveJavaParser.client;

import asynchJavaParser.common.reports.interfaces.IProjectReport;
import io.reactivex.rxjava3.core.Observable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class to handle the action on getProjectReport button
 * calling the getProjectReport library function
 * */
public class GetProjectReport implements ActionListener {

    private final VisualizerFrame view;

    public GetProjectReport(final VisualizerFrame frame) {
        this.view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String path = this.view.getNameDirectory().getText();
        this.view.getTreeView().setVisible(true);
        this.view.resetTree();
        this.view.getMethodButtons().get("getProjectReport").setEnabled(false);

        if(!path.contains(".java")){
            Observable<IProjectReport> ob = this.view.getLib().getProjectReport(path);
            ob.subscribe(p -> {
                this.view.getTreePanel().updateProjectReport(p, this.view.getTreePanel().getRoot());
            });
        }else{
            this.view.errorMessage("Error!!! Select a project");
        }
    }
}
