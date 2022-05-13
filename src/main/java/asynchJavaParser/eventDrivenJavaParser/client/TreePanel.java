package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.eventDrivenJavaParser.client.projectAnalysis.InterfaceElem;
import asynchJavaParser.eventDrivenJavaParser.client.projectAnalysis.PackageElem;
import asynchJavaParser.eventDrivenJavaParser.client.projectAnalysis.ProjectStructure;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TreePanel extends JPanel {

    private JTree tree;

    private DefaultMutableTreeNode root;

    public TreePanel() {
        createTree();
    }

    public JTree getTree() {
        return this.tree;
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }

    public void reset(){
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode treeRoot = (DefaultMutableTreeNode) model.getRoot();
        treeRoot.removeAllChildren();
        model.reload();
    }

    public void update(IClassReport report, DefaultMutableTreeNode addNode){
        IClassReport res = report;
        List<IMethodInfo> methodInfo = new ArrayList<>();
        List<IFieldInfo> fieldInfo = new ArrayList<>();
        res.getMethodsInfo().forEach(m -> methodInfo.add(m));
        res.getFieldsInfo().forEach(f -> fieldInfo.add(f));

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
        List<IInterfaceReport> interfaceInfo = new ArrayList<>();
        res.getClassReports().forEach(c -> {
            classInfo.add(c);
            System.out.println(c);
        });
        res.getInterfaceReports().forEach(i -> interfaceInfo.add(i));

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

    public void update(ProjectStructure ps){
        reset();
        PackageElem psRoot = ps.getRoot();
        ps.getRoot().getInnerInterfaces().forEach((k,v) -> System.out.println(k+" KEY"));
        Map<String, PackageElem> innerPackages = psRoot.getInnerPackages();
        Map<String, InterfaceElem> innerInterfaces = psRoot.getInnerInterfaces();

        DefaultMutableTreeNode packageDeclaration = new DefaultMutableTreeNode(ps.getRoot().getElem().isEmpty() ? "**" : ps.getRoot().getElem().get().getName());
        root.add(packageDeclaration);

        if(!ps.getRoot().getInnerPackages().isEmpty()){

        }

        innerInterfaces.forEach((k, v) -> {
            DefaultMutableTreeNode name = new DefaultMutableTreeNode(k);
            packageDeclaration.add(name);
        });
        //getMethodButtons().forEach((k, v) -> v.setEnabled(true));
    }

    private void createTree(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        root = new DefaultMutableTreeNode("Tree");
        DefaultTreeCellRenderer render = new DefaultTreeCellRenderer();
        render.setLeafIcon(null);
        render.setOpenIcon(null);
        tree = new JTree(root);
        tree.setCellRenderer(render);
    }
}
