package AsynchJavaParser.EventDrivenJavaParser.Visitors;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class CLassVisitor extends VoidVisitorAdapter<Void> implements Visitor {

    @Override
    public void visit(PackageDeclaration fd, Void collector) {
        super.visit(fd, collector);
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, Void collector) {
        super.visit(cd, collector);
    }

    @Override
    public void visit(MethodDeclaration md, Void collector) {
        super.visit(md, collector);
    }

    public void visit(FieldDeclaration fd, Void collector) {
        super.visit(fd, collector);
    }
}
