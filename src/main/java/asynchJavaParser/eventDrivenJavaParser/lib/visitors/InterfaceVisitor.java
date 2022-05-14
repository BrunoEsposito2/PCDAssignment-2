package asynchJavaParser.eventDrivenJavaParser.lib.visitors;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.*;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IInterfaceReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IMethodInfo;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class InterfaceVisitor extends VoidVisitorAdapter<IInterfaceReport> implements Visitor<IInterfaceReport> {
    private IMethodInfo methodInfo;

    public InterfaceVisitor() {
    }

    @Override
    public void visit(PackageDeclaration fd, IInterfaceReport interfaceReport) {
        super.visit(fd, interfaceReport);
        interfaceReport.setSrcFullFileName(fd.getNameAsString());
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, IInterfaceReport interfaceReport) {
        super.visit(cd, interfaceReport);
        interfaceReport.setFullInterfaceName(cd.getNameAsString());
    }

    @Override
    public void visit(MethodDeclaration md, IInterfaceReport interfaceReport) {
        super.visit(md, interfaceReport);
        this.methodInfo = new MethodInfo(md.getNameAsString(), md.getBegin(), md.getEnd());
        this.methodInfo.setReturnType(md.getType());
        md.getParameters().forEach(x -> methodInfo.addParameter(new ParameterInfo(x.getNameAsString(), x.getType())));
        interfaceReport.addMethodsInfo(methodInfo);
    }
}
