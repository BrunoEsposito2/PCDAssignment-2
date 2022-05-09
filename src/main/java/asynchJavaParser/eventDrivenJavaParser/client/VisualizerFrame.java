package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.eventDrivenJavaParser.lib.EDProjectAnalyzer;
import asynchJavaParser.eventDrivenJavaParser.lib.projectAnalyzer.AnalyzeProjectConfig;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.PackageReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IPackageReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IProjectReport;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class VisualizerFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField nameDirectory;
    private JPanel viewPanel;
    private TreePanel treePanel;
    private EventBus eventBus;
    private List<JButton> methodButton;
    private EDProjectAnalyzer lib;

    public VisualizerFrame(Vertx v) {
        setTitle("ProjectChooserDemo");
        setMinimumSize(new Dimension(1000,800));

        createView();
        eventBus = v.eventBus();
        lib = new EDProjectAnalyzer(v);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public List<JButton> getMethodButton() {
        return methodButton;
    }

    public void change(String path){
        getMethodButton().forEach(b -> b.setEnabled(true));
        //nameDirectory.setText(path);
        nameDirectory.setText("src/main/java/asynchJavaParser/eventDrivenJavaParser/lib/projectAnalyzer/Courier.java");
    }

    public void display(){
        try {
            SwingUtilities.invokeAndWait(() -> {
                this.setVisible(true);
            });
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void createView() {
        viewPanel = new JPanel(new BorderLayout());
        // NORTH
        JPanel managementPanel = new JPanel();
        managementPanel.setLayout(new BoxLayout(managementPanel, BoxLayout.Y_AXIS));
        managementPanel.setBorder(new EmptyBorder(30,15,30,15));

        JButton openFileChooser = new JButton("Select project");
        openFileChooser.addActionListener(new OpenFolderChooser());
        openFileChooser.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton stop = new JButton("STOP");
        stop.addActionListener(new StopEvents());
        stop.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameDirectory = new JTextField();
        nameDirectory.setText("...");
        nameDirectory.setAlignmentX(Component.CENTER_ALIGNMENT);

        managementPanel.add(openFileChooser);
        managementPanel.add(stop);
        managementPanel.add(nameDirectory);

        // WEST
        JPanel buttonPanel = new JPanel(new GridLayout(0,1,10,40));
        buttonPanel.setBorder(new EmptyBorder(40,15,40,15));

        JButton getClassReport = new JButton("getClassReport");
        getClassReport.addActionListener(new GetClassReport());
        JButton getPackageReport = new JButton("getPackageReport");
        getPackageReport.addActionListener(new GetPackageReport());
        JButton getProjectReport = new JButton("getProjectReport");
        getProjectReport.addActionListener(new GetProjectReport());
        JButton analyzeProject = new JButton("analyzeProject");
        analyzeProject.addActionListener(new AnalyzeProject());

        methodButton = new ArrayList<>();
        methodButton.add(getClassReport);
        methodButton.add(getPackageReport);
        methodButton.add(getProjectReport);
        methodButton.add(analyzeProject);

        methodButton.forEach(b -> b.setEnabled(false));

        buttonPanel.add(getClassReport);
        buttonPanel.add(getPackageReport);
        buttonPanel.add(getProjectReport);
        buttonPanel.add(analyzeProject);

        // CENTER
        treePanel = new TreePanel();
        JScrollPane treeView = new JScrollPane(treePanel.getTree());
        treePanel.add(treeView);

        viewPanel.add(managementPanel, BorderLayout.NORTH);
        viewPanel.add(buttonPanel, BorderLayout.WEST);
        viewPanel.add(treePanel, BorderLayout.CENTER);
        setContentPane(viewPanel);
    }

    private class OpenFolderChooser implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new java.io.File("./src/"));
                fileChooser.setDialogTitle("Project chooser");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int n = fileChooser.showOpenDialog(VisualizerFrame.this);
                if (n == JFileChooser.APPROVE_OPTION) {
                    Formatter formatter = new Formatter();
                    change(formatter.formatPath(fileChooser.getSelectedFile().getPath()));
                }
            } catch (Exception ex) {}
        }
    }

    private class StopEvents implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            eventBus.publish("worker", "STOP");
        }
    }

    private class GetClassReport implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Future<IClassReport> future = lib.getClassReport(nameDirectory.getText());
            future.onComplete(res -> {
                IClassReport report = res.result();
                //System.out.println("BUILD \n" + report.toString());
                treePanel.update(report);
            });
        }
    }

    private class GetPackageReport implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Future<IPackageReport> future = lib.getPackageReport(nameDirectory.getText());
            future.onComplete(res -> {
                IPackageReport report = res.result();
                String result = res.result().toString();
                //Formatter formatter = new Formatter();
                //formatter.formatResult(result, "PackageReport");
                System.out.println("BUILD \n" + res.result().toString());
            });
        }
    }

    private class GetProjectReport implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Future<IProjectReport> future = lib.getProjectReport(nameDirectory.getText());
            future.onComplete(res -> {
                IProjectReport report = res.result();
                //Formatter formatter = new Formatter();
                //formatter.formatResult(result, "ProjectReport");
                System.out.println("BUILD \n" + res.result().toString());
            });
        }
    }

    private class AnalyzeProject implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            AnalyzeProjectConfig conf = new AnalyzeProjectConfig("master", "master.complete", nameDirectory.getText());
            lib.analyzeProject(conf, message -> {
                String elemType = message.headers().get("type");
                switch(elemType){
                    case "class":
                        ClassOrInterfaceDeclaration cd = (ClassOrInterfaceDeclaration)message.body();
                        System.out.println("class "+ cd.getNameAsString());
                        break;

                    case "interface":
                        ClassOrInterfaceDeclaration id = (ClassOrInterfaceDeclaration)message.body();
                        System.out.println("interface "+ id.getNameAsString());
                        break;
                    case "field":
                        FieldDeclaration fd = (FieldDeclaration)message.body();
                        fd.getVariables().forEach(f -> System.out.println("field "+ f.getNameAsString()));
                        break;

                    case "method":
                        MethodDeclaration md = (MethodDeclaration)message.body();
                        System.out.println("method "+ md.getNameAsString());
                        break;

                    case "package":
                        PackageDeclaration pd = (PackageDeclaration)message.body();
                        System.out.println("method "+ pd.getNameAsString());
                        break;

                    default:
                        System.out.println("not a project element");
                }

            });
        }
    }
}

