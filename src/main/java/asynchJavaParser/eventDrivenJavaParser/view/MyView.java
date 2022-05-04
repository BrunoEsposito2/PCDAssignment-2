package asynchJavaParser.eventDrivenJavaParser.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class MyView extends JPanel {

    private static final long serialVersionUID = 1L;
    private File directory;
    private JLabel nameDirectory;

    private JLabel infoTitle;

    public MyView() {
        super(new BorderLayout());
        createPanel();
    }

    private void createPanel() {
        JButton openFileChooser = new JButton("Select project");
        openFileChooser.addActionListener(new OpenFolderChooser());
        openFileChooser.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel panelButton = new JPanel();
        panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.Y_AXIS));
        panelButton.add(openFileChooser);

        nameDirectory = new JLabel();
        nameDirectory.setText("");
        nameDirectory.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelButton.add(nameDirectory);

        JPanel infoPanel = new JPanel();
        infoTitle = new JLabel("");
        infoTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(infoTitle);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        add(panelButton, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
    }

    private class OpenFolderChooser implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new java.io.File("."));
                fileChooser.setDialogTitle("Project chooser");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int n = fileChooser.showOpenDialog(MyView.this);
                if (n == JFileChooser.APPROVE_OPTION) {
                    directory = fileChooser.getCurrentDirectory();
                    System.out.println("getCurrentDirectory().getName(): " + directory.getName());
                    System.out.println("getCurrentDirectory().getPath(): " + directory.getPath());
                    System.out.println("getSelectedFile(): " + fileChooser.getSelectedFile().getName());	//correct path of the directory selected
                    nameDirectory.setText(fileChooser.getSelectedFile().getPath());
                    infoTitle.setText("INFORMATIONS");
//					File f = fileChooser.getSelectedFile();
//					BufferedReader read = new BufferedReader(new FileReader(f));
//					String line = read.readLine();
//					while(line != null) {
//						textArea.append(line);
//						line = read.readLine();
//					}
//					read.close();
                }
            } catch (Exception ex) {}
        }
    }

    public static void main(String[] argv) {
        JFrame frame = new JFrame("ProjectChooserDemo");
        MyView view = new MyView();
        frame.setMinimumSize(new Dimension(1000,800));
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().add(view);
        frame.pack();
        frame.setVisible(true);
    }
}

