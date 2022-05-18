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

    private ProjectStructure ps;

    private int countElem;

    private int totalElem;

    public AnalyzeProject(final VisualizerFrame frame) {
        this.view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.ps = new ProjectStructure();
        this.countElem = 0;
        this.totalElem = -1;
        this.view.resetTree();
        this.view.getMethodButtons().get("analyzeProject").setEnabled(false);
        this.view.getStopButton().setEnabled(true);
        this.view.getStatus().setText("Running ...");
        AnalyzeProjectConfig conf = new AnalyzeProjectConfig("master", "master.complete", this.view.getNameDirectory().getText());
        MessageConsumer<String> consumer = this.view.getEventBus().consumer(conf.getStatusNotifAddress());
        consumer.handler(message -> {
            if(message.headers().get("type") == "count"){
                // total number of elements to process
                this.totalElem = Integer.parseInt(message.body());
            }else if(message.headers().get("type") == "status"){
                // error messages
                this.view.getStatus().setText(message.body());
            }
        });
        this.view.getLib().analyzeProject(conf, message -> {
            this.countElem++;
            String elemType = message.headers().get("type");
            this.view.getTotalAnalyzed().setText(String.valueOf(this.countElem + "/" + this.totalElem));
            if(this.totalElem == this.countElem){
                this.view.getStatus().setText("Complete");
            }
            switch (elemType) {
                case "Class":
                    ClassOrInterfaceDeclaration cd = (ClassOrInterfaceDeclaration) message.body();
                    this.ps.putClassOrInterface(cd);
                    this.view.getTreePanel().dynamicUpdate(this.ps);
                    break;
                case "Interface":
                    ClassOrInterfaceDeclaration id = (ClassOrInterfaceDeclaration) message.body();
                    this.ps.putClassOrInterface(id);
                    this.view.getTreePanel().dynamicUpdate(ps);
                    break;
                case "Field":
                    FieldDeclaration fd = (FieldDeclaration) message.body();
                    this.ps.putField(fd);
                    this.view.getTreePanel().dynamicUpdate(this.ps);;
                    break;
                case "Method":
                    MethodDeclaration md = (MethodDeclaration) message.body();
                    this.ps.putMethod(md);
                    this.view.getTreePanel().dynamicUpdate(this.ps);
                    break;
                case "Package":
                    PackageDeclaration pd = (PackageDeclaration) message.body();
                    this.ps.putPackage(pd);
                    this.view.getTreePanel().dynamicUpdate(this.ps);
                    break;
                default:
                    this.view.errorMessage("Error!!! Select a project");
            }

        });
    }
}
