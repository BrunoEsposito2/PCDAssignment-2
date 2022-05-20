package asynchJavaParser.eventDrivenJavaParser.client;

import asynchJavaParser.common.reports.interfaces.*;
import asynchJavaParser.eventDrivenJavaParser.client.projectAnalysis.ClassElem;
import asynchJavaParser.eventDrivenJavaParser.client.projectAnalysis.InterfaceElem;
import asynchJavaParser.eventDrivenJavaParser.client.projectAnalysis.PackageElem;
import asynchJavaParser.eventDrivenJavaParser.client.projectAnalysis.ProjectStructure;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        return this.root;
    }

    public void reset() {
        DefaultTreeModel model = (DefaultTreeModel) this.tree.getModel();
        DefaultMutableTreeNode treeRoot = (DefaultMutableTreeNode) model.getRoot();
        treeRoot.removeAllChildren();
        model.reload();
    }

    public void update(final IClassReport report, final DefaultMutableTreeNode addNode) {
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

        className = new DefaultMutableTreeNode(res.getFullClassName() + " - Class");
        className.add(new DefaultMutableTreeNode(res.getSrcFullFileName() + " - SrcFullFileName"));
        addNode.add(className);

        className.add(fieldNode);
        className.add(methodNode);

        fieldInfo.forEach(f -> {
            DefaultMutableTreeNode nameNode = new DefaultMutableTreeNode(f.getName());
            DefaultMutableTreeNode typeNode = new DefaultMutableTreeNode(f.getFieldTypeFullName() + " - Type");
            fieldName.add(nameNode);
            nameNode.add(typeNode);
        });
        fieldName.forEach(fn -> fieldNode.add(fn));

        methodInfo.forEach(m -> {
            DefaultMutableTreeNode nameNode = new DefaultMutableTreeNode(m.getName());
            DefaultMutableTreeNode retTypeNode = new DefaultMutableTreeNode(m.getReturnType() + " - ReturnType");
            methodName.add(nameNode);
            nameNode.add(retTypeNode);
            m.getParameters().forEach(p -> {
                DefaultMutableTreeNode parameter = new DefaultMutableTreeNode(p.getName() + " - Parameter");
                nameNode.add(parameter);
                parameter.add(new DefaultMutableTreeNode(p.getType() + " - ParamType"));
            });
        });
        methodName.forEach(m -> methodNode.add(m));
    }

    public void update(final IInterfaceReport report, final DefaultMutableTreeNode addNode) {
        IInterfaceReport res = report;
        List<IMethodInfo> methodInfo = new ArrayList<>();
        methodInfo.addAll(res.getMethodsInfo());

        DefaultMutableTreeNode interfaceName;
        List<DefaultMutableTreeNode> methodName = new ArrayList<>();
        DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode("METHODS");

        interfaceName = new DefaultMutableTreeNode(res.getFullInterfaceName() + " - Interface");
        interfaceName.add(new DefaultMutableTreeNode(res.getSrcFullFileName() + " - SrcFullFileName"));
        addNode.add(interfaceName);

        interfaceName.add(methodNode);

        methodInfo.forEach(m -> {
            DefaultMutableTreeNode nameNode = new DefaultMutableTreeNode(m.getName());
            DefaultMutableTreeNode retTypeNode = new DefaultMutableTreeNode(m.getReturnType() + " - ReturnType");
            methodName.add(nameNode);
            nameNode.add(retTypeNode);
            m.getParameters().forEach(p -> {
                DefaultMutableTreeNode parameter = new DefaultMutableTreeNode(p.getName() + " - Parameter");
                nameNode.add(parameter);
                parameter.add(new DefaultMutableTreeNode(p.getType() + " - ParamType"));
            });
        });
        methodName.forEach(m -> methodNode.add(m));
    }

    public void update(final IPackageReport report, final DefaultMutableTreeNode addNode) {
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

        packageName = new DefaultMutableTreeNode(res.getSrcFullName() + " - Package");
        addNode.add(packageName);

        packageName.add(classNode);
        packageName.add(interfaceNode);

        classInfo.forEach(c -> update(c, classNode));

        interfaceInfo.forEach(i -> update(i, interfaceNode));
    }

    public void update(final IProjectReport report, final DefaultMutableTreeNode addNode) {
        IProjectReport res = report;
        List<IPackageReport> packageInfo = new ArrayList<>();
        packageInfo.addAll(res.getPackageReports());

        DefaultMutableTreeNode projectName;
        DefaultMutableTreeNode packageNode = new DefaultMutableTreeNode("PACKAGES");

        projectName = new DefaultMutableTreeNode(res.getMainClass() + " - MainClass");
        addNode.add(projectName);

        projectName.add(packageNode);

        packageInfo.forEach(p -> update(p, packageNode));
    }

    public void dynamicUpdate(final ProjectStructure ps) {
        PackageElem psRoot = ps.getRoot();
        reset();

        DefaultMutableTreeNode packageNode = new DefaultMutableTreeNode("PACKAGES");
        DefaultMutableTreeNode classNode = new DefaultMutableTreeNode("CLASSES");
        DefaultMutableTreeNode interfaceNode = new DefaultMutableTreeNode("INTERFACES");

        Map<String, PackageElem> innerPackages;

        if(!psRoot.getInnerPackages().isEmpty()){
            this.root.add(packageNode);
            innerPackages = psRoot.getInnerPackages();
            innerPackages.forEach((k, v) -> {
                DefaultMutableTreeNode packageName = new DefaultMutableTreeNode(k+ " - Package");
                packageNode.add(packageName);
                packageUpdate(v, packageName);
            });
        }

        classUpdate(psRoot, classNode);
        interfaceUpdate(psRoot, interfaceNode);
    }

    private void createTree() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.root = new DefaultMutableTreeNode("Tree");
        DefaultTreeCellRenderer render = new DefaultTreeCellRenderer();
        render.setLeafIcon(null);
        render.setOpenIcon(null);
        this.tree = new JTree(this.root);
        this.tree.setCellRenderer(render);
    }

    private void classUpdate(final PackageElem packageElem, final DefaultMutableTreeNode addNode){
        if (!packageElem.getInnerClasses().isEmpty()) {
            Map<String, ClassElem> innerClasses = packageElem.getInnerClasses();
            DefaultMutableTreeNode classNode = new DefaultMutableTreeNode("CLASSES");

            addNode.add(classNode);

            innerClasses.forEach((k, v) -> {
                if(Optional.of(v.getElem()).get().isPresent()){
                    DefaultMutableTreeNode name = new DefaultMutableTreeNode(k+ " - Class");

                    classNode.add(name);

                    ClassOrInterfaceDeclaration c = Optional.of(v.getElem()).get().get();

                    name.add(new DefaultMutableTreeNode(c.getFullyQualifiedName().get()  + " - SrcFullFileName"));

                    if(!Optional.of(v).get().getFields().isEmpty()){
                        List<DefaultMutableTreeNode> fieldsName = new ArrayList<>();
                        DefaultMutableTreeNode fieldNode = new DefaultMutableTreeNode("FIELDS");

                        name.add(fieldNode);

                        v.getFields().forEach(f -> {
                            f.forEach(x -> {
                                DefaultMutableTreeNode nameNode = new DefaultMutableTreeNode(x.getNameAsString());
                                DefaultMutableTreeNode typeNode = new DefaultMutableTreeNode(x.getTypeAsString() + " - Type");
                                fieldsName.add(nameNode);
                                nameNode.add(typeNode);
                            });
                        });
                        fieldsName.forEach(fn -> fieldNode.add(fn));
                    }

                    if (!v.getMethods().isEmpty()){
                        List<DefaultMutableTreeNode> methodsName = new ArrayList<>();
                        DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode("METHODS");

                        name.add(methodNode);

                        v.getMethods().forEach((x, y) -> {
                            DefaultMutableTreeNode mName = new DefaultMutableTreeNode(x);
                            DefaultMutableTreeNode retTypeNode = new DefaultMutableTreeNode(y.getType() + " - ReturnType");
                            methodsName.add(mName);
                            mName.add(retTypeNode);

                            y.getParameters().forEach(p -> {
                                DefaultMutableTreeNode parameter = new DefaultMutableTreeNode(p.getNameAsString() + " - Parameter");
                                mName.add(parameter);
                                parameter.add(new DefaultMutableTreeNode(p.getType() + " - ParamType"));
                            });
                        });
                        methodsName.forEach(m -> methodNode.add(m));
                    }
                }
            });
        }
    }

    private void interfaceUpdate(final PackageElem packageElem, final DefaultMutableTreeNode addNode){
        if (!packageElem.getInnerInterfaces().isEmpty()) {
            Map<String, InterfaceElem> innerInterfaces = packageElem.getInnerInterfaces();
            DefaultMutableTreeNode interfaceNode = new DefaultMutableTreeNode("INTERFACES");

            addNode.add(interfaceNode);

            innerInterfaces.forEach((k, v) -> {
                if(Optional.of(v.getElem()).get().isPresent()){
                    DefaultMutableTreeNode interfaceName = new DefaultMutableTreeNode(k+ " - Interface");

                    interfaceNode.add(interfaceName);

                    if (!v.getMethods().isEmpty()){
                        List<DefaultMutableTreeNode> methodsName = new ArrayList<>();
                        DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode("METHODS");

                        interfaceName.add(methodNode);
                        v.getMethods().forEach((x, y) -> {
                            DefaultMutableTreeNode mName = new DefaultMutableTreeNode(y.getNameAsString());
                            DefaultMutableTreeNode retTypeNode = new DefaultMutableTreeNode(y.getType() + " - ReturnType");
                            methodsName.add(mName);
                            mName.add(retTypeNode);

                            y.getParameters().forEach(p -> {
                                DefaultMutableTreeNode parameter = new DefaultMutableTreeNode(p.getNameAsString() + " - Parameter");
                                mName.add(parameter);
                                parameter.add(new DefaultMutableTreeNode(p.getType() + " - ParamType"));
                            });
                        });
                        methodsName.forEach(m -> methodNode.add(m));
                    }
                }
            });
        }
    }

    private void packageUpdate(final PackageElem packageElem, final DefaultMutableTreeNode addNode){
        if (!packageElem.getInnerPackages().isEmpty()) {
            Map<String, PackageElem> packages = packageElem.getInnerPackages();
            DefaultMutableTreeNode packageNode = new DefaultMutableTreeNode("PACKAGES");

            addNode.add(packageNode);

            packages.forEach((k, v) -> {
                DefaultMutableTreeNode packName = new DefaultMutableTreeNode(k+ " - Package");
                packageNode.add(packName);
                packageUpdate(v, packName);
            });
        }

        classUpdate(packageElem, addNode);
        interfaceUpdate(packageElem, addNode);
    }
}
