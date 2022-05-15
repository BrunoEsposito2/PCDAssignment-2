package asynchJavaParser.common.visitors;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

/**
 *
 */
public interface Visitor<Reporter> {

    void visit(PackageDeclaration fd, Reporter collector);

    void visit(ClassOrInterfaceDeclaration cd, Reporter collector);

    void visit(MethodDeclaration md, Reporter collector);

    void visit(FieldDeclaration fd, Reporter collector);
}
