package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.eventDrivenJavaParser.lib.EDProjectAnalyzer;
import io.vertx.core.Vertx;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class VisualizerFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField nameDirectory;
    private JLabel status;
    private JPanel viewPanel;
    private TreePanel treePanel;
    private JScrollPane treeView;
    private JButton stop;
    private Map<String, JButton> methodButtons;
    private EDProjectAnalyzer lib;

    public VisualizerFrame(Vertx v) {
        setTitle("ProjectChooserDemo");
        setMinimumSize(new Dimension(1000,800));

        createView();
        lib = new EDProjectAnalyzer(v);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public JTextField getNameDirectory() {
        return nameDirectory;
    }

    public JLabel getStatus(){
        return status;
    }

    public Map<String, JButton> getMethodButtons(){
        return methodButtons;
    }

    public EDProjectAnalyzer getLib() {
        return lib;
    }

    public TreePanel getTreePanel(){
        return treePanel;
    }

    public JButton getStopButton(){
        return this.stop;
    }

    public void changeView(String path){
        getMethodButtons().forEach((k, v) -> v.setEnabled(true));
        nameDirectory.setText(path);
        //nameDirectory.setText("src/main/java/asynchJavaParser/eventDrivenJavaParser/lib/");
    }

    public void resetView(){
        methodButtons.forEach((k, v) -> v.setEnabled(false));
        nameDirectory.setText("...");
        status.setText("");
    }

    public void resetTree(){
        treePanel.reset();
    }

    public void errorMessage(){
        JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
        resetView();
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
        openFileChooser.addActionListener(new OpenFolderChooser(this));
        openFileChooser.setAlignmentX(Component.CENTER_ALIGNMENT);

        stop = new JButton("STOP");
        stop.setEnabled(false);
        stop.addActionListener(new StopEvents(this));
        stop.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameDirectory = new JTextField();
        nameDirectory.setAlignmentX(Component.CENTER_ALIGNMENT);

        managementPanel.add(openFileChooser);
        managementPanel.add(stop);
        managementPanel.add(nameDirectory);

        // WEST
        JPanel buttonPanel = new JPanel(new GridLayout(0,1,10,40));
        buttonPanel.setBorder(new EmptyBorder(40,15,40,15));

        JButton getClassReport = new JButton("getClassReport");
        getClassReport.addActionListener(new GetClassReport(this));

        JButton getPackageReport = new JButton("getPackageReport");
        getPackageReport.addActionListener(new GetPackageReport(this));

        JButton getProjectReport = new JButton("getProjectReport");
        getProjectReport.addActionListener(new GetProjectReport(this));

        JButton analyzeProject = new JButton("analyzeProject");
        analyzeProject.addActionListener(new AnalyzeProject(this));

        status = new JLabel();
        status.setAlignmentX(Component.CENTER_ALIGNMENT);

        methodButtons = new HashMap<>();
        methodButtons.put(getClassReport.getText(), getClassReport);
        methodButtons.put(getPackageReport.getText(), getPackageReport);
        methodButtons.put(getProjectReport.getText(), getProjectReport);
        methodButtons.put(analyzeProject.getText(), analyzeProject);

        buttonPanel.add(getClassReport);
        buttonPanel.add(getPackageReport);
        buttonPanel.add(getProjectReport);
        buttonPanel.add(analyzeProject);
        buttonPanel.add(status);

        // CENTER
        treePanel = new TreePanel();
        treeView = new JScrollPane(treePanel.getTree());
        treePanel.add(treeView);

        resetView();

        viewPanel.add(managementPanel, BorderLayout.NORTH);
        viewPanel.add(buttonPanel, BorderLayout.WEST);
        viewPanel.add(treePanel, BorderLayout.CENTER);
        setContentPane(viewPanel);
    }
}

