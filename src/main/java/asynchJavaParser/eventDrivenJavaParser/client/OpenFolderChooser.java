package asynchJavaParser.eventDrivenJavaParser.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenFolderChooser implements ActionListener {

    private VisualizerFrame view;

    public OpenFolderChooser(VisualizerFrame frame){
        view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File("./src/"));
            fileChooser.setDialogTitle("Project chooser");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.setAcceptAllFileFilterUsed(false);
            int n = fileChooser.showOpenDialog(view);
            if (n == JFileChooser.APPROVE_OPTION) {
                Formatter formatter = new Formatter();
                view.changeView(formatter.formatPath(fileChooser.getSelectedFile().getPath()));
            }
        } catch (Exception ex) {}
    }
}
