package asynchJavaParser.reactiveJavaParser;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class CountVisitor extends VoidVisitorAdapter<ProjectStructure> {

    private List<String> packages;
    private String currentPackage;

    public CountVisitor(final List<String> files) {
        this.packages = files;
        this.currentPackage = "";
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, ProjectStructure collector){
        super.visit(cd, collector);
        if(cd.isInterface()) collector.incrementInterfaces();
        else collector.incrementClasses();
    }

    @Override
    public void visit(PackageDeclaration pd, ProjectStructure collector){
        String current = this.getPackage(pd.getNameAsString());
        if (!this.currentPackage.equals(current)) {
            super.visit(pd, collector);
            collector.incrementPackages();
            this.currentPackage = current;
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

    private String getPackage(String path) {
        for (final String p : this.packages) {
            if (p.contains(path))
                return path;
        }
        return null;
    }
}
