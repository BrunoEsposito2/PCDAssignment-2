package asynchJavaParser.eventDrivenJavaParser.lib.visitors;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

/**
 *
 */
public interface Visitor {

    void visit(PackageDeclaration fd, Void collector);

    void visit(ClassOrInterfaceDeclaration cd, Void collector);

    void visit(MethodDeclaration md, Void collector);

    void visit(FieldDeclaration fd, Void collector);
}
