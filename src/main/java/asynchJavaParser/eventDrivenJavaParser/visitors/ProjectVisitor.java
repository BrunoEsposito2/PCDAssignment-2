package asynchJavaParser.eventDrivenJavaParser.visitors;

import asynchJavaParser.eventDrivenJavaParser.reports.IProjectReport;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ProjectVisitor extends VoidVisitorAdapter<Void> implements Visitor{
    IProjectReport projectReport;

    public ProjectVisitor(IProjectReport projectReport) {
        this.projectReport = projectReport;
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
