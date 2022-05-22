package asynchJavaParser.reactiveJavaParser.client;

import asynchJavaParser.common.utils.Formatter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class to handle the action on fileChooser button
 * */
public class OpenFolderChooser implements ActionListener {

    private final VisualizerFrame view;

    public OpenFolderChooser(final VisualizerFrame frame) {
        this.view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.view.getTreeView().setVisible(true);
            this.view.resetView();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File("./src/"));
            fileChooser.setDialogTitle("Project chooser");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.setAcceptAllFileFilterUsed(false);
            int n = fileChooser.showOpenDialog(this.view);
            if (n == JFileChooser.APPROVE_OPTION) {
                this.view.changeView(Formatter.formatPath(fileChooser.getSelectedFile().getPath()));
            }
        } catch (Exception ex) {
        }
    }
}
