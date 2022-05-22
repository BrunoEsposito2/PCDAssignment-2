package asynchJavaParser.reactiveJavaParser;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;

public class CountVisitor extends VoidVisitorAdapter<ProjectStructure> {

    private final List<PackageDeclaration> packages;

    public CountVisitor() {
        this.packages = new ArrayList<>();
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, ProjectStructure collector){
        super.visit(cd, collector);
        if(cd.isInterface()) collector.incrementInterfaces();
        else collector.incrementClasses();
    }

    @Override
    public void visit(PackageDeclaration pd, ProjectStructure collector){
        if (!packages.contains(pd)) {
            super.visit(pd, collector);
            collector.incrementPackages();
            packages.add(pd);
        }
    }

    @Override
    public void visit(MethodDeclaration md, ProjectStructure collector){
        super.visit(md, collector);
        collector.incrementMethods();
    }

    @Override
    public void visit(FieldDeclaration fd, ProjectStructure collector){
        super.visit(fd, collector);
        for(Object field : fd.getVariables()){
            collector.incrementFields();
        }
    }

}
