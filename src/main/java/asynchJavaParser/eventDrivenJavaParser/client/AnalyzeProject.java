package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.eventDrivenJavaParser.client.projectAnalysis.ProjectStructure;
import asynchJavaParser.eventDrivenJavaParser.lib.projectAnalyzer.AnalyzeProjectConfig;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnalyzeProject implements ActionListener {

    private VisualizerFrame view;
    private ProjectStructure ps;

    public AnalyzeProject(VisualizerFrame frame){
        view = frame;
        ps = new ProjectStructure();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.resetTree();
        view.getMethodButtons().get("analyzeProject").setEnabled(false);
        view.getStopButton().setEnabled(true);
        view.getStatus().setText("Running ...");
        AnalyzeProjectConfig conf = new AnalyzeProjectConfig("master", "master.complete", view.getNameDirectory().getText());
        view.getLib().analyzeProject(conf, message -> {
            String elemType = message.headers().get("type");
            switch(elemType){
                case "Class":
                    ClassOrInterfaceDeclaration cd = (ClassOrInterfaceDeclaration)message.body();
                    ps.putClassOrInterface(cd);
                    view.getTreePanel().update(ps);
                    System.out.println("class "+ cd.getNameAsString());
                    break;
                case "Interface":
                    ClassOrInterfaceDeclaration id = (ClassOrInterfaceDeclaration)message.body();
                    ps.putClassOrInterface(id);
                    view.getTreePanel().update(ps);
                    System.out.println("interface "+ id.getNameAsString());
                    break;
                case "Field":
                    FieldDeclaration fd = (FieldDeclaration)message.body();
                    ps.putField(fd);
                    view.getTreePanel().update(ps);
                    fd.getVariables().forEach(f -> System.out.println("field "+ f.getNameAsString()));
                    break;
                case "Method":
                    MethodDeclaration md = (MethodDeclaration)message.body();
                    ps.putMethod(md);
                    view.getTreePanel().update(ps);
                    System.out.println("method "+ md.getNameAsString());
                    break;
                case "Package":
                    PackageDeclaration pd = (PackageDeclaration)message.body();
                    ps.putPackage(pd);
                    view.getTreePanel().update(ps);
                    System.out.println("method "+ pd.getNameAsString());
                    break;
                default:
                    System.out.println("not a project element " + elemType);
            }
        });
    }
}
