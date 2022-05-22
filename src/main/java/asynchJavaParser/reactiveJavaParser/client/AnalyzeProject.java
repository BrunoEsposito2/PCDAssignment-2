package asynchJavaParser.reactiveJavaParser.client;

import asynchJavaParser.common.reports.interfaces.IPackageReport;
import asynchJavaParser.reactiveJavaParser.ProjectStructure;
import io.reactivex.rxjava3.core.Observable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnalyzeProject implements ActionListener {

    private final VisualizerFrame view;

    private ProjectStructure ps;

    public AnalyzeProject(final VisualizerFrame frame) {
        this.view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String path = this.view.getNameDirectory().getText();
        this.ps = new ProjectStructure();

        this.view.getTreeView().setVisible(false);
        this.view.getInfoArea().setVisible(true);
        this.view.resetTree();
        this.view.getMethodButtons().get("analyzeProject").setEnabled(false);

        if(!path.contains(".java")){
            Observable<ProjectStructure> ob = this.view.getLib().analyzeProject(path);
            ob.subscribe(p -> {
                ps.addTo(p);
                this.view.getInfoArea().setText("DIRECTORY ->   " + this.view.getNameDirectory().getText() + "\n");
                this.view.getInfoArea().append("PACKAGES NR:    " + ps.getPackagesNr() + "\n");
                this.view.getInfoArea().append("INTERFACES NR:  " + ps.getInterfacesNr() + "\n");
                this.view.getInfoArea().append("CLASSES NR:     " + ps.getClassesNr() + "\n");
                this.view.getInfoArea().append("FIELDS NR:      " + ps.getFieldsNr() + "\n");
                this.view.getInfoArea().append("METHODS NR:     " + ps.getMethodsNr());
            });
        }else{
            this.view.errorMessage("Error!!! Select a project");
        }
    }
}