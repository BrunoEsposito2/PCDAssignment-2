package asynchJavaParser.eventDrivenJavaParser.lib.visitors;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.PackageReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IPackageReport;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class PackageVisitor extends VoidVisitorAdapter<IPackageReport> implements Visitor<IPackageReport> {
    private IPackageReport packageReport;
    private final ClassVisitor classVisitor;
    private final InterfaceVisitor interfaceVisitor;

    public PackageVisitor() {
        this.packageReport = new PackageReport();
        this.classVisitor = new ClassVisitor();
        this.interfaceVisitor = new InterfaceVisitor();
    }

    public IPackageReport getPackageReport() {
        return this.packageReport;
    }

    @Override
    public void visit(PackageDeclaration pd, IPackageReport collector) {
        super.visit(pd, collector);
        this.packageReport = collector;
        this.packageReport.setSrcFullName(pd.getNameAsString());
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, IPackageReport collector) {
        if (cd.isInterface()) {
            this.interfaceVisitor.visit(cd, this.interfaceVisitor.getInterfaceReport());
            this.packageReport = collector;
            this.packageReport.addInterfaceReport(this.interfaceVisitor.getInterfaceReport());
        } else {
            this.classVisitor.visit(cd, this.classVisitor.getClassReport());
            this.packageReport = collector;
            this.packageReport.addClassReport(this.classVisitor.getClassReport());
        }
    }

}
