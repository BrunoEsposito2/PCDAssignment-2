package asynchJavaParser.common.visitors;

import asynchJavaParser.common.reports.*;
import asynchJavaParser.common.reports.interfaces.IClassReport;
import asynchJavaParser.common.reports.interfaces.IFieldInfo;
import asynchJavaParser.common.reports.interfaces.IMethodInfo;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassVisitor extends VoidVisitorAdapter<IClassReport> implements Visitor<IClassReport> {
    private IMethodInfo methodInfo;
    private IFieldInfo fieldInfo;

    public ClassVisitor() {
    }

    @Override
    public void visit(PackageDeclaration fd, IClassReport classReport) {
        super.visit(fd, classReport);
        classReport.setSrcFullFileName(fd.getNameAsString());
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, IClassReport classReport) {
        super.visit(cd, classReport);
        classReport.setFullClassName(cd.getNameAsString());
        System.out.println(cd.getFullyQualifiedName());
    }

    @Override
    public void visit(MethodDeclaration md, IClassReport classReport) {
        super.visit(md, classReport);
        methodInfo = new MethodInfo(md.getNameAsString(), md.getBegin(), md.getEnd());
        methodInfo.setReturnType(md.getType());
        md.getParameters().forEach(x -> methodInfo.addParameter(new ParameterInfo(x.getNameAsString(), x.getType())));
        classReport.addMethodsInfo(methodInfo);
    }

    @Override
    public void visit(FieldDeclaration fd, IClassReport classReport) {
        super.visit(fd, classReport);
        fd.getVariables().forEach(x -> {
            fieldInfo = new FieldInfo(x.getNameAsString(), x.getTypeAsString());
            classReport.addFieldsInfo(fieldInfo);
        } );
    }
}
