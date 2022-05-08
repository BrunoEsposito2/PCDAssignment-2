package asynchJavaParser.eventDrivenJavaParser.lib.visitors;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IInterfaceReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IPackageReport;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class PackageVisitor extends VoidVisitorAdapter<Void> implements Visitor {
    private final IPackageReport packageReport;
    private final ClassVisitor classVisitor;
    private final InterfaceVisitor interfaceVisitor;

    public PackageVisitor(IPackageReport pr, IClassReport cr, IInterfaceReport ir) {
        this.packageReport = pr;
        this.classVisitor = new ClassVisitor(cr);
        this.interfaceVisitor = new InterfaceVisitor(ir);
    }

    public IPackageReport getPackageReport() {
        return this.packageReport;
    }

    @Override
    public void visit(PackageDeclaration pd, Void collector) {
        super.visit(pd, collector);
        this.packageReport.setSrcFullName(pd.getNameAsString());
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, Void collector) {
        if (cd.isInterface()) {
            this.interfaceVisitor.visit(cd, collector);
            this.packageReport.addInterfaceReport(this.interfaceVisitor.getInterfaceReport());
        } else {
            this.classVisitor.visit(cd, collector);
            this.packageReport.addClassReport(this.classVisitor.getClassReport());
        }
    }

}
