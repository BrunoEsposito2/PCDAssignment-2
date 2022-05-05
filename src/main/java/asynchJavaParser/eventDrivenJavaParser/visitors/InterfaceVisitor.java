package asynchJavaParser.eventDrivenJavaParser.visitors;

import asynchJavaParser.eventDrivenJavaParser.reports.IMethodInfo;
import asynchJavaParser.eventDrivenJavaParser.reports.InterfaceReport;
import asynchJavaParser.eventDrivenJavaParser.reports.MethodInfo;
import asynchJavaParser.eventDrivenJavaParser.reports.ParameterInfo;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class InterfaceVisitor extends VoidVisitorAdapter<Void> implements Visitor {
    private InterfaceReport interfaceReport;
    private IMethodInfo methodInfo;

    public InterfaceVisitor(InterfaceReport interfaceReport) {
        this.interfaceReport = new InterfaceReport();
    }

    @Override
    public void visit(PackageDeclaration fd, Void collector) {
        super.visit(fd, collector);
        this.interfaceReport.setSrcFullFileName(fd.getNameAsString());
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, Void collector) {
        super.visit(cd, collector);
        this.interfaceReport.setFullInterfaceName(cd.getNameAsString());
    }

    @Override
    public void visit(MethodDeclaration md, Void collector) {
        super.visit(md, collector);
        methodInfo = new MethodInfo(md.getNameAsString(), md.getBegin(), md.getEnd());
        methodInfo.setReturnType(md.getType());
        md.getParameters().forEach(x -> methodInfo.addParameter(new ParameterInfo(x.getNameAsString(), x.getType()).toString()));
        this.interfaceReport.addMethodsInfo(methodInfo);
    }
}
