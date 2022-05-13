package asynchJavaParser.eventDrivenJavaParser.lib.visitors;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.ClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.InterfaceReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IPackageReport;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class PackageVisitor extends VoidVisitorAdapter<IPackageReport> implements Visitor<IPackageReport> {
    private IPackageReport packageReport;
    private ClassReport classReport;
    private InterfaceReport interfaceReport;
    private final ClassVisitor classVisitor;
    private final InterfaceVisitor interfaceVisitor;

    public PackageVisitor(IPackageReport packageReport, ClassReport classReport, InterfaceReport interfaceReport) {
        this.packageReport = packageReport;
        this.classReport = classReport;
        this.interfaceReport = interfaceReport;
        this.classVisitor = new ClassVisitor(this.classReport);
        this.interfaceVisitor = new InterfaceVisitor(this.interfaceReport);
    }

    @Override
    public void visit(PackageDeclaration pd, IPackageReport collector) {
        super.visit(pd, collector);
        this.packageReport.setSrcFullName(pd.getNameAsString());
        this.classReport.setSrcFullFileName(pd.getNameAsString());
        this.interfaceReport.setSrcFullFileName(pd.getNameAsString());
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, IPackageReport collector) {
        if (cd.isInterface()) {
            this.interfaceVisitor.visit(cd, this.interfaceReport);
            this.packageReport.addInterfaceReport(this.interfaceReport);
        } else {
            this.classVisitor.visit(cd, this.classReport);
            this.packageReport.addClassReport(this.classReport);
        }
    }

}
