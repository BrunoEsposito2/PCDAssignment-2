package asynchJavaParser.eventDrivenJavaParser.lib.visitors;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.*;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IFieldInfo;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IMethodInfo;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassVisitor extends VoidVisitorAdapter<Void> implements Visitor {
    private final IClassReport classReport;
    private IMethodInfo methodInfo;
    private IFieldInfo fieldInfo;

    public ClassVisitor(IClassReport classReport) {
        this.classReport = classReport;
    }

    public IClassReport getClassReport() {
        return this.classReport;
    }

    @Override
    public void visit(PackageDeclaration fd, Void collector) {
        super.visit(fd, collector);
        this.classReport.setSrcFullFileName(fd.getNameAsString());
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, Void collector) {
        super.visit(cd, collector);
        this.classReport.setFullClassName(cd.getNameAsString());
        System.out.println(cd.getFullyQualifiedName());
    }

    @Override
    public void visit(MethodDeclaration md, Void collector) {
        super.visit(md, collector);
        methodInfo = new MethodInfo(md.getNameAsString(), md.getBegin(), md.getEnd());
        methodInfo.setReturnType(md.getType());
        md.getParameters().forEach(x -> methodInfo.addParameter(new ParameterInfo(x.getNameAsString(), x.getType()).toString()));
        this.classReport.addMethodsInfo(methodInfo);
    }

    @Override
    public void visit(FieldDeclaration fd, Void collector) {
        super.visit(fd, collector);
        fd.getVariables().forEach(x -> {
            fieldInfo = new FieldInfo(x.getNameAsString(), x.getTypeAsString());
            this.classReport.addFieldsInfo(fieldInfo);
        } );
    }
}
