package asynchJavaParser.eventDrivenJavaParser.prototype;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class User extends JPanel {

    private static final long serialVersionUID = 1L;
    private File directory;
    private static JLabel nameDirectory;
    private EventBus eventBus;

    private JLabel infoTitle;
    private FakeLibrary fl;
    public User(Vertx v) {
        super(new BorderLayout());
        createPanel();
        eventBus = v.eventBus();
        fl = new FakeLibrary(v);
    }

    private void createPanel() {
        JButton openFileChooser = new JButton("FERMA TUTTO!!!");
        openFileChooser.addActionListener(new StopEvents());
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
        /**/
    }

    private class StopEvents implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            eventBus.publish("worker", "STOP");
        }
    }

    public static void main(String[] argv) {
        JFrame frame = new JFrame("ProjectChooserDemo");
        Vertx vertx = Vertx.vertx();
        User view = new User(vertx);
        frame.setMinimumSize(new Dimension(1000,800));
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().add(view);
        frame.pack();
        frame.setVisible(true);
        view.fl.method(String -> { nameDirectory.setText(String);
        });
    }
}

