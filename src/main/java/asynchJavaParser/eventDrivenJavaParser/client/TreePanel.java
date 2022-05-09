package asynchJavaParser.eventDrivenJavaParser.client;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreePanel extends JPanel {

    private JTree tree;

    public TreePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        createNodes(root);
        tree = new JTree(root);
    }

    public JTree getTree() {
        return this.tree;
    }

    public void update(){

    }

    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode book = null;

        category = new DefaultMutableTreeNode("Books for Java Programmers");
        top.add(category);

        category = new DefaultMutableTreeNode("Books for Java Implementers");
        top.add(category);
    }
}
