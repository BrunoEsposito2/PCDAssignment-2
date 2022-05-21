package asynchJavaParser.common.visitors;

import asynchJavaParser.common.reports.ClassReport;
import asynchJavaParser.common.reports.InterfaceReport;
import asynchJavaParser.common.reports.interfaces.IPackageReport;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class PackageVisitor extends VoidVisitorAdapter<IPackageReport> implements Visitor<IPackageReport> {
    private ClassReport classReport;
    private InterfaceReport interfaceReport;
    private final ClassVisitor classVisitor;
    private final InterfaceVisitor interfaceVisitor;

    public PackageVisitor() {
        this.classReport = new ClassReport();
        this.interfaceReport = new InterfaceReport();
        this.classVisitor = new ClassVisitor();
        this.interfaceVisitor = new InterfaceVisitor();
    }

    @Override
    public void visit(PackageDeclaration pd, IPackageReport packageReport) {
        super.visit(pd, packageReport);
        packageReport.setSrcFullFileName(pd.getNameAsString());
        this.classReport.setSrcFullFileName(pd.getNameAsString());
        this.interfaceReport.setSrcFullFileName(pd.getNameAsString());
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, IPackageReport packageReport) {
        if (cd.isInterface()) {
            this.interfaceVisitor.visit(cd, this.interfaceReport);
            packageReport.addInterfaceReport(this.interfaceReport);
        } else {
            this.classVisitor.visit(cd, this.classReport);
            packageReport.addClassReport(this.classReport);
        }
    }

}
