package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.eventDrivenJavaParser.lib.projectAnalyzer.AnalyzeProjectConfig;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnalyzeProject implements ActionListener {

    private VisualizerFrame view;

    public AnalyzeProject(VisualizerFrame frame){
        view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.resetTree();
        view.getMethodButtons().get("analyzeProject").setEnabled(false);
        view.getStopButton().setEnabled(true);
        AnalyzeProjectConfig conf = new AnalyzeProjectConfig("master", "master.complete", view.getNameDirectory().getText());
        view.getLib().analyzeProject(conf, message -> {
            //System.out.println("IN");
            String elemType = message.headers().get("type");
            switch(elemType){
                case "Class":
                    ClassOrInterfaceDeclaration cd = (ClassOrInterfaceDeclaration)message.body();
                    System.out.println("class "+ cd.getNameAsString());
                    break;
                case "Interface":
                    ClassOrInterfaceDeclaration id = (ClassOrInterfaceDeclaration)message.body();
                    System.out.println("interface "+ id.getNameAsString());
                    break;
                case "Field":
                    FieldDeclaration fd = (FieldDeclaration)message.body();
                    fd.getVariables().forEach(f -> System.out.println("field "+ f.getNameAsString()));
                    break;
                case "Method":
                    MethodDeclaration md = (MethodDeclaration)message.body();
                    System.out.println("method "+ md.getNameAsString());
                    break;
                case "Package":
                    PackageDeclaration pd = (PackageDeclaration)message.body();
                    System.out.println("method "+ pd.getNameAsString());
                    break;
                default:
                    System.out.println("not a project element " + elemType);
            }
        });
    }
}
