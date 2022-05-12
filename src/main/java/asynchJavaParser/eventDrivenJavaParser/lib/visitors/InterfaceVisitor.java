package asynchJavaParser.eventDrivenJavaParser.lib.visitors;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.*;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IInterfaceReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IMethodInfo;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class InterfaceVisitor extends VoidVisitorAdapter<IInterfaceReport> implements Visitor<IInterfaceReport> {
    private IInterfaceReport interfaceReport;
    private IMethodInfo methodInfo;

    public InterfaceVisitor() {
        this.interfaceReport = new InterfaceReport();
    }

    public IInterfaceReport getInterfaceReport() {
        return this.interfaceReport;
    }

    @Override
    public void visit(PackageDeclaration fd, IInterfaceReport collector) {
        super.visit(fd, collector);
        this.interfaceReport = collector;
        this.interfaceReport.setSrcFullFileName(fd.getNameAsString());
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, IInterfaceReport collector) {
        super.visit(cd, collector);
        this.interfaceReport = collector;
        this.interfaceReport.setFullInterfaceName(cd.getNameAsString());
    }

    @Override
    public void visit(MethodDeclaration md, IInterfaceReport collector) {
        super.visit(md, collector);
        this.interfaceReport = collector;
        this.methodInfo = new MethodInfo(md.getNameAsString(), md.getBegin(), md.getEnd());
        this.methodInfo.setReturnType(md.getType());
        md.getParameters().forEach(x -> methodInfo.addParameter(new ParameterInfo(x.getNameAsString(), x.getType())));
        this.interfaceReport.addMethodsInfo(methodInfo);
    }
}
