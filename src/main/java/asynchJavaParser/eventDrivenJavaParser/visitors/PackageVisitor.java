package asynchJavaParser.eventDrivenJavaParser.visitors;

import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IPackageReport;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class PackageVisitor extends VoidVisitorAdapter<Void> implements Visitor {
    private IPackageReport packageReport;

    public PackageVisitor(IPackageReport pr){
        this.packageReport = pr;
    }

    @Override
    public void visit(PackageDeclaration fd, Void collector) {

    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, Void collector) {

    }

    @Override
    public void visit(MethodDeclaration md, Void collector) {

    }
}
