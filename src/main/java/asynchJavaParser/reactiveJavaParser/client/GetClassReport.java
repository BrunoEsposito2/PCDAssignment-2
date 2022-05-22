package asynchJavaParser.reactiveJavaParser.client;

import asynchJavaParser.common.reports.interfaces.IClassReport;
import io.reactivex.rxjava3.core.Observable;

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
        String path = this.view.getNameDirectory().getText();
        this.view.getTreeView().setVisible(true);
        this.view.resetTree();
        this.view.getMethodButtons().get("getClassReport").setEnabled(false);

        if(path.contains(".java")){
            Observable<IClassReport> ob = this.view.getLib().getClassReport(path);
            ob.subscribe(p -> {
                this.view.getTreePanel().updateClassReport(p, this.view.getTreePanel().getRoot());
            });
        }else{
            this.view.errorMessage("Error!!! Select a Java file");
        }
    }
}
