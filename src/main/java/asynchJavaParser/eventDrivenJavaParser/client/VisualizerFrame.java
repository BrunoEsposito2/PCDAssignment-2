package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.common.utils.TreePanelController;
import asynchJavaParser.eventDrivenJavaParser.lib.EDProjectAnalyzer;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to create and modify the entire GUI
 * */
public class VisualizerFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final EDProjectAnalyzer lib;

    private final EventBus eventBus;

    private JTextField nameDirectory;

    private JLabel status;

    private JLabel totalAnalyzed;

    private JPanel viewPanel;

    private TreePanelController treePanelController;

    private JScrollPane treeView;

    private JButton stop;

    private Map<String, JButton> methodButtons;

    public VisualizerFrame(final Vertx v) {
        setTitle("ProjectChooserDemo");
        setMinimumSize(new Dimension(1000, 800));

        createView();
        this.lib = new EDProjectAnalyzer(v);
        this.eventBus = v.eventBus();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public JTextField getNameDirectory() {
        return this.nameDirectory;
    }

    public JLabel getStatus() {
        return this.status;
    }

    public JLabel getTotalAnalyzed(){
        return  this.totalAnalyzed;
    }

    public Map<String, JButton> getMethodButtons() {
        return this.methodButtons;
    }

    public EDProjectAnalyzer getLib() {
        return this.lib;
    }

    public TreePanelController getTreePanel() {
        return this.treePanelController;
    }

    public JButton getStopButton() {
        return this.stop;
    }

    public EventBus getEventBus() {
        return this.eventBus;
    }

    public void changeView(final String path) {
        getMethodButtons().forEach((k, v) -> v.setEnabled(true));
        this.nameDirectory.setText(path);
    }

    public void resetView() {
        this.methodButtons.forEach((k, v) -> v.setEnabled(false));
        this.nameDirectory.setText("...");
        this.status.setText("");
        this.totalAnalyzed.setText("");
        this.stop.setEnabled(false);
    }

    public void resetTree() {
        this.treePanelController.resetTree();
    }

    public void errorMessage(final String error) {
        int ret = JOptionPane.showOptionDialog(null, error, "ERROR", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, null, null);

        if(ret == 0){
            getStopButton().setEnabled(false);
            resetView();
            getLib().stopAnalyzeProject();
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
        // NORTH Panel
        JPanel managementPanel = new JPanel();
        managementPanel.setLayout(new BoxLayout(managementPanel, BoxLayout.Y_AXIS));
        managementPanel.setBorder(new EmptyBorder(30, 15, 30, 15));

        JButton openFileChooser = new JButton("SELECT TO ANALYZE");
        openFileChooser.addActionListener(new OpenFolderChooser(this));
        openFileChooser.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.stop = new JButton("STOP");
        this.stop.setEnabled(false);
        this.stop.addActionListener(new StopEvents(this));
        this.stop.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.nameDirectory = new JTextField();
        this.nameDirectory.setAlignmentX(Component.CENTER_ALIGNMENT);

        managementPanel.add(openFileChooser);
        managementPanel.add(this.stop);
        managementPanel.add(this.nameDirectory);

        // WEST Panel
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 40));
        buttonPanel.setBorder(new EmptyBorder(40, 15, 40, 15));

        JButton getClassReport = new JButton("getClassReport");
        getClassReport.addActionListener(new GetClassReport(this));

        JButton getPackageReport = new JButton("getPackageReport");
        getPackageReport.addActionListener(new GetPackageReport(this));

        JButton getProjectReport = new JButton("getProjectReport");
        getProjectReport.addActionListener(new GetProjectReport(this));

        JButton analyzeProject = new JButton("analyzeProject");
        analyzeProject.addActionListener(new AnalyzeProject(this));

        this.totalAnalyzed = new JLabel();
        this.totalAnalyzed.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.status = new JLabel();
        this.status.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.methodButtons = new HashMap<>();
        this.methodButtons.put(getClassReport.getText(), getClassReport);
        this.methodButtons.put(getPackageReport.getText(), getPackageReport);
        this.methodButtons.put(getProjectReport.getText(), getProjectReport);
        this.methodButtons.put(analyzeProject.getText(), analyzeProject);

        buttonPanel.add(getClassReport);
        buttonPanel.add(getPackageReport);
        buttonPanel.add(getProjectReport);
        buttonPanel.add(analyzeProject);
        buttonPanel.add(this.totalAnalyzed);
        buttonPanel.add(this.status);

        // CENTER Panel
        this.treePanelController = new TreePanelController();
        this.treeView = new JScrollPane(this.treePanelController.getTree());
        this.treePanelController.add(this.treeView);

        resetView();

        this.viewPanel.add(managementPanel, BorderLayout.NORTH);
        this.viewPanel.add(buttonPanel, BorderLayout.WEST);
        this.viewPanel.add(this.treePanelController, BorderLayout.CENTER);
        setContentPane(this.viewPanel);
    }
}

