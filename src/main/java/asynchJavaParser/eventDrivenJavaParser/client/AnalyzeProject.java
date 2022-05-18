package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.eventDrivenJavaParser.client.projectAnalysis.ProjectStructure;
import asynchJavaParser.eventDrivenJavaParser.lib.projectAnalysis.AnalyzeProjectConfig;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import io.vertx.core.eventbus.MessageConsumer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnalyzeProject implements ActionListener {

    private final VisualizerFrame view;

    private final ProjectStructure ps;

    public AnalyzeProject(final VisualizerFrame frame) {
        this.view = frame;
        this.ps = new ProjectStructure();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.view.resetTree();
        this.view.getMethodButtons().get("analyzeProject").setEnabled(false);
        this.view.getStopButton().setEnabled(true);
        this.view.getStatus().setText("Running ...");
        AnalyzeProjectConfig conf = new AnalyzeProjectConfig("master", "master.complete", this.view.getNameDirectory().getText());
        MessageConsumer<String> consumer = this.view.getEventBus().consumer(conf.getCompletitionNotifAddress());
        consumer.handler(message -> {
            this.view.getStatus().setText("Complete");
        });
        this.view.getLib().analyzeProject(conf, message -> {
            String elemType = message.headers().get("type");
            switch (elemType) {
                case "Class":
                    ClassOrInterfaceDeclaration cd = (ClassOrInterfaceDeclaration) message.body();
                    this.ps.putClassOrInterface(cd);
                    this.view.getTreePanel().dynamicUpdate(this.ps);
                    System.out.println("class " + cd.getNameAsString());
                    break;
                case "Interface":
                    ClassOrInterfaceDeclaration id = (ClassOrInterfaceDeclaration) message.body();
                    this.ps.putClassOrInterface(id);
                    this.view.getTreePanel().dynamicUpdate(ps);
                    System.out.println("interface " + id.getNameAsString());
                    break;
                case "Field":
                    FieldDeclaration fd = (FieldDeclaration) message.body();
                    this.ps.putField(fd);
                    this.view.getTreePanel().dynamicUpdate(this.ps);
                    fd.getVariables().forEach(f -> System.out.println("field " + f.getNameAsString()));
                    break;
                case "Method":
                    MethodDeclaration md = (MethodDeclaration) message.body();
                    this.ps.putMethod(md);
                    this.view.getTreePanel().dynamicUpdate(this.ps);
                    System.out.println("method " + md.getNameAsString());
                    break;
                case "Package":
                    PackageDeclaration pd = (PackageDeclaration) message.body();
                    this.ps.putPackage(pd);
                    this.view.getTreePanel().dynamicUpdate(this.ps);
                    System.out.println("package " + pd.getNameAsString());
                    break;
                default:
                    System.out.println("not a project element " + elemType);
                    this.view.errorMessage("Error!!! Select a project");
            }
        });
    }
}
