package asynchJavaParser.reactiveJavaParser.client;

import asynchJavaParser.reactiveJavaParser.ReactiveProjectAnalyzer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import asynchJavaParser.common.utils.TreePanelController;

/**
 * Class to create and modify the entire GUI
 * */
public class VisualizerFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final ReactiveProjectAnalyzer lib;

    private JTextField nameDirectory;

    private JTextArea infoArea;

    private JPanel viewPanel;

    private TreePanelController treePanelController;

    private JScrollPane treeView;

    private JButton stop;

    private Map<String, JButton> methodButtons;

    private AnalyzeProject analyzeProject;

    public VisualizerFrame() {
        setTitle("ProjectChooserDemo");
        setMinimumSize(new Dimension(1000, 800));

        createView();
        this.lib = new ReactiveProjectAnalyzer();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public AnalyzeProject getAnalyzeProject(){
        return this.analyzeProject;
    }

    public JButton getStopButton(){
        return this.stop;
    }

    public JTextField getNameDirectory() {
        return this.nameDirectory;
    }

    public JTextArea getInfoArea(){
        return this.infoArea;
    }

    public Map<String, JButton> getMethodButtons() {
        return this.methodButtons;
    }

    public ReactiveProjectAnalyzer getLib() {
        return this.lib;
    }

    public TreePanelController getTreePanel() {
        return this.treePanelController;
    }

    public JScrollPane getTreeView(){
        return this.treeView;
    }

    public void changeView(final String path) {
        getMethodButtons().forEach((k, v) -> v.setEnabled(true));
        this.nameDirectory.setText(path);
    }

    public void resetView() {
        this.methodButtons.forEach((k, v) -> v.setEnabled(false));
        this.nameDirectory.setText("...");
        this.stop.setEnabled(false);
        this.infoArea.setVisible(false);
        this.treeView.setVisible(true);
    }

    public void resetTree() {
        this.treePanelController.resetTree();
    }

    public void errorMessage(final String error) {
        int ret = JOptionPane.showOptionDialog(null, error, "ERROR", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, null, null);

        if(ret == 0){
            resetView();
        }
    }

    public void display() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                this.setVisible(true);
            });
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void createView() {
        this.viewPanel = new JPanel(new BorderLayout());
        // NORTH
        JPanel managementPanel = new JPanel();
        managementPanel.setLayout(new BoxLayout(managementPanel, BoxLayout.Y_AXIS));
        managementPanel.setBorder(new EmptyBorder(30, 15, 30, 15));

        JButton openFileChooser = new JButton("SELECT TO ANALYZE");
        openFileChooser.addActionListener(new OpenFolderChooser(this));
        openFileChooser.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.stop = new JButton("STOP");
        this.stop.addActionListener(new StopEvents(this));
        this.stop.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.nameDirectory = new JTextField();
        this.nameDirectory.setAlignmentX(Component.CENTER_ALIGNMENT);

        managementPanel.add(openFileChooser);
        managementPanel.add(this.stop);
        managementPanel.add(this.nameDirectory);

        // WEST
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 40));
        buttonPanel.setBorder(new EmptyBorder(40, 15, 40, 15));

        JButton getClassReport = new JButton("getClassReport");
        getClassReport.addActionListener(new GetClassReport(this));

        JButton getPackageReport = new JButton("getPackageReport");
        getPackageReport.addActionListener(new GetPackageReport(this));

        JButton getProjectReport = new JButton("getProjectReport");
        getProjectReport.addActionListener(new GetProjectReport(this));

        JButton analyzeProjectButton = new JButton("analyzeProject");
        this.analyzeProject = new AnalyzeProject(this);
        analyzeProjectButton.addActionListener(this.analyzeProject);

        this.methodButtons = new HashMap<>();
        this.methodButtons.put(getClassReport.getText(), getClassReport);
        this.methodButtons.put(getPackageReport.getText(), getPackageReport);
        this.methodButtons.put(getProjectReport.getText(), getProjectReport);
        this.methodButtons.put(analyzeProjectButton.getText(), analyzeProjectButton);

        buttonPanel.add(getClassReport);
        buttonPanel.add(getPackageReport);
        buttonPanel.add(getProjectReport);
        buttonPanel.add(analyzeProjectButton);

        // CENTER
        this.treePanelController = new TreePanelController();
        this.treeView = new JScrollPane(this.treePanelController.getTree());
        this.treePanelController.add(this.treeView);

        this.infoArea = new JTextArea();

        this.treePanelController.add(this.infoArea);

        resetView();

        this.viewPanel.add(managementPanel, BorderLayout.NORTH);
        this.viewPanel.add(buttonPanel, BorderLayout.WEST);
        this.viewPanel.add(this.treePanelController, BorderLayout.CENTER);
        setContentPane(this.viewPanel);
    }
}

