package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.ClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.ProjectReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.*;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TreePanel extends JPanel implements TreeSelectionListener {

    private JTree tree;
    private IProjectReport projectInfos;
    private List<IPackageReport> packageInfos;
    private List<IClassReport> classInfos;
    private List<IInterfaceReport> interfaceInfos;
    private List<IMethodInfo> methodInfos;
    private List<IFieldInfo> fieldInfos;
    private DefaultMutableTreeNode root;

    public TreePanel() {
        packageInfos = new ArrayList<>();
        classInfos = new ArrayList<>();
        interfaceInfos = new ArrayList<>();
        fieldInfos = new ArrayList<>();
        methodInfos = new ArrayList<>();
        createTree();
    }

    private void createTree(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        root = new DefaultMutableTreeNode(" ");
        DefaultTreeCellRenderer render = new DefaultTreeCellRenderer();
        render.setLeafIcon(null);
        render.setOpenIcon(null);
        tree = new JTree(root);
        tree.setCellRenderer(render);
    }

    public JTree getTree() {
        return this.tree;
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }

    public void reset(){
        System.out.println("ResetTree");
        removeAll();
        if(projectInfos!=null){
            projectInfos = null;
        }
        if(!packageInfos.isEmpty()){
            packageInfos.clear();
        }
        if(!classInfos.isEmpty()){
            classInfos.clear();
        }
        if(!interfaceInfos.isEmpty()){
            interfaceInfos.clear();
        }
        if(!methodInfos.isEmpty()){
            methodInfos.clear();
        }
        if(!fieldInfos.isEmpty()){
            fieldInfos.clear();
        }
        createTree();
        repaint();
        //update();
    }

    public void update(IClassReport report, DefaultMutableTreeNode addNode){
        IClassReport res = report;
        List<IMethodInfo> methodInfo = new ArrayList<>();
        List<IFieldInfo> fieldInfo = new ArrayList<>();
        methodInfo.addAll(res.getMethodsInfo());
        fieldInfo.addAll(res.getFieldsInfo());

        DefaultMutableTreeNode className;
        List<DefaultMutableTreeNode> fieldName = new ArrayList<>();
        List<DefaultMutableTreeNode> methodName = new ArrayList<>();
        DefaultMutableTreeNode fieldNode = new DefaultMutableTreeNode("FIELDS");
        DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode("METHODS");

        className = new DefaultMutableTreeNode(res.getFullClassName()+" - Class");
        className.add(new DefaultMutableTreeNode(res.getSrcFullFileName()+" - SrcFullFileName"));
        addNode.add(className);

        className.add(fieldNode);
        className.add(methodNode);

        fieldInfo.forEach(f -> {
            DefaultMutableTreeNode nameNode = new DefaultMutableTreeNode(f.getName());
            DefaultMutableTreeNode typeNode = new DefaultMutableTreeNode(f.getFieldTypeFullName()+" - Type");
            fieldName.add(nameNode);
            nameNode.add(typeNode);
        });
        fieldName.forEach(fn -> fieldNode.add(fn));

        methodInfo.forEach(m -> {
            DefaultMutableTreeNode nameNode = new DefaultMutableTreeNode(m.getName());
            DefaultMutableTreeNode retTypeNode = new DefaultMutableTreeNode(m.getReturnType()+" - ReturnType");
            methodName.add(nameNode);
            nameNode.add(retTypeNode);
            m.getParameters().forEach(p -> {
                DefaultMutableTreeNode parameter = new DefaultMutableTreeNode(p.getName()+" - Parameter");
                nameNode.add(parameter);
                parameter.add(new DefaultMutableTreeNode(p.getType()+" - ParamType"));
            });
        });
        methodName.forEach(m -> methodNode.add(m));
    }

    public void update(IInterfaceReport report, DefaultMutableTreeNode addNode){
        IInterfaceReport res = report;
        List<IMethodInfo> methodInfo = new ArrayList<>();
        methodInfo.addAll(res.getMethodsInfo());

        DefaultMutableTreeNode interfaceName;
        List<DefaultMutableTreeNode> methodName = new ArrayList<>();
        DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode("METHODS");

        interfaceName = new DefaultMutableTreeNode(res.getFullInterfaceName()+" - Interface");
        interfaceName.add(new DefaultMutableTreeNode(res.getSrcFullFileName()+" - SrcFullFileName"));
        addNode.add(interfaceName);

        interfaceName.add(methodNode);

        methodInfo.forEach(m -> {
            DefaultMutableTreeNode nameNode = new DefaultMutableTreeNode(m.getName());
            DefaultMutableTreeNode retTypeNode = new DefaultMutableTreeNode(m.getReturnType()+" - ReturnType");
            methodName.add(nameNode);
            nameNode.add(retTypeNode);
            m.getParameters().forEach(p -> {
                DefaultMutableTreeNode parameter = new DefaultMutableTreeNode(p.getName()+" - Parameter");
                nameNode.add(parameter);
                parameter.add(new DefaultMutableTreeNode(p.getType()+" - ParamType"));
            });
        });
        methodName.forEach(m -> methodNode.add(m));
    }

    public void update(IPackageReport report, DefaultMutableTreeNode addNode) {
        IPackageReport res = report;
        List<IClassReport> classInfo = new ArrayList<>();
        classInfo.addAll(res.getClassReports());
        List<IInterfaceReport> interfaceInfo = new ArrayList<>();
        interfaceInfo.addAll(res.getInterfaceReports());

        DefaultMutableTreeNode packageName;
        DefaultMutableTreeNode classNode = new DefaultMutableTreeNode("CLASSES");
        DefaultMutableTreeNode interfaceNode = new DefaultMutableTreeNode("INTERFACES");

        packageName = new DefaultMutableTreeNode(res.getSrcFullName()+" - Package");
        addNode.add(packageName);

        packageName.add(classNode);
        packageName.add(interfaceNode);

        classInfo.forEach(c -> update(c, classNode));

        interfaceInfo.forEach(i -> update(i, interfaceNode));
    }

    public void update(IProjectReport report, DefaultMutableTreeNode addNode) {
        IProjectReport res = report;
        List<IPackageReport> packageInfo = new ArrayList<>();
        packageInfo.addAll(res.getPackageReports());

        DefaultMutableTreeNode projectName;
        DefaultMutableTreeNode packageNode = new DefaultMutableTreeNode("PACKAGES");

        projectName = new DefaultMutableTreeNode(res.getMainClass()+" - MainClass");
        addNode.add(projectName);

        projectName.add(packageNode);

        packageInfo.forEach(p -> update(p, packageNode));
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        System.out.println("CHANGES");
        //update();
    }
}
