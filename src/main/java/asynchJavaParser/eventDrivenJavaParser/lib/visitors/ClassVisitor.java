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

public class ClassVisitor extends VoidVisitorAdapter<IClassReport> implements Visitor<IClassReport> {
    private IClassReport classReport;
    private IMethodInfo methodInfo;
    private IFieldInfo fieldInfo;

    public ClassVisitor() {
        this.classReport = new ClassReport();
    }

    public IClassReport getClassReport() {
        return this.classReport;
    }

    @Override
    public void visit(PackageDeclaration fd, IClassReport collector) {
        super.visit(fd, collector);
        this.classReport = collector;
        this.classReport.setSrcFullFileName(fd.getNameAsString());
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, IClassReport collector) {
        super.visit(cd, collector);
        this.classReport = collector;
        this.classReport.setFullClassName(cd.getNameAsString());
        System.out.println(cd.getFullyQualifiedName());
    }

    @Override
    public void visit(MethodDeclaration md, IClassReport collector) {
        super.visit(md, collector);
        this.classReport = collector;
        methodInfo = new MethodInfo(md.getNameAsString(), md.getBegin(), md.getEnd());
        methodInfo.setReturnType(md.getType());
        md.getParameters().forEach(x -> methodInfo.addParameter(new ParameterInfo(x.getNameAsString(), x.getType())));
        this.classReport.addMethodsInfo(methodInfo);
    }

    @Override
    public void visit(FieldDeclaration fd, IClassReport collector) {
        super.visit(fd, collector);
        this.classReport = collector;
        fd.getVariables().forEach(x -> {
            fieldInfo = new FieldInfo(x.getNameAsString(), x.getTypeAsString());
            this.classReport.addFieldsInfo(fieldInfo);
        } );
    }
}
