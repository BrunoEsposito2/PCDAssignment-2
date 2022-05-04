package asynchJavaParser.EventDrivenJavaParser.Visitors;

import asynchJavaParser.EventDrivenJavaParser.Reports.*;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassVisitor extends VoidVisitorAdapter<Void> implements IClassVisitor {
    private ClassReport classReport;
    private IMethodInfo methodInfo;

    private IFieldInfo fieldInfo;

    public ClassVisitor(ClassReport classReport) {
        this.classReport = new ClassReport();
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
        fieldInfo = new FieldInfo();
        fd.getVariables().forEach(x -> fieldInfo.addField(new FieldInfo(x.getNameAsString(), x.getTypeAsString()).toString()));
        this.classReport.addFieldsInfo(fieldInfo);
    }
}
