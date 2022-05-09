package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IFieldInfo;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IMethodInfo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;

public class TreePanel extends JPanel {

    private JTree tree;
    private List<IClassReport> classInfos;
    private List<IMethodInfo> methodInfos;
    private List<IFieldInfo> fieldInfos;
    private DefaultMutableTreeNode root;

    public TreePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        root = new DefaultMutableTreeNode();
        classInfos = new ArrayList<>();
        fieldInfos = new ArrayList<>();
        methodInfos = new ArrayList<>();
        createNodes();
        tree = new JTree(root);
    }

    public JTree getTree() {
        return this.tree;
    }

    public void update(IClassReport report){
        classInfos.add(report);
        report.getMethodsInfo().forEach(m -> methodInfos.add(m));
        report.getFieldsInfo().forEach(f -> fieldInfos.add(f));
        createNodes();
    }

    private void createNodes() {
        List<DefaultMutableTreeNode> className = new ArrayList<>();
        List<DefaultMutableTreeNode> fieldName = new ArrayList<>();
        List<DefaultMutableTreeNode> fieldType = new ArrayList<>();
        List<DefaultMutableTreeNode> methodName = new ArrayList<>();

        classInfos.forEach(c -> className.add(new DefaultMutableTreeNode(c.getFullClassName())));
        className.forEach(n -> root.add(n));

        fieldInfos.forEach(f -> {
            fieldName.add(new DefaultMutableTreeNode(f.getName()));
            fieldType.add(new DefaultMutableTreeNode(f.getFieldTypeFullName()));
        });
        className.forEach(c -> fieldName.forEach(f -> c.add(f)));
        fieldName.forEach(f -> fieldType.forEach(t -> f.add(t))); // da rivedere

        methodInfos.forEach(m -> methodName.add(new DefaultMutableTreeNode(m.getName())));
        className.forEach(c -> methodName.forEach(m -> c.add(m)));

        /*
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode book = null;

        category = new DefaultMutableTreeNode("Books for Java Programmers");
        top.add(category);

        //original Tutorial
        book = new DefaultMutableTreeNode("");
        category.add(book);
        className = new DefaultMutableTreeNode("Books for Java Implementers");
        root.add(className);*/
    }
}
