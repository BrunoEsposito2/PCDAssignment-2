package asynchJavaParser.EventDrivenJavaParser.Visitors;

import com.github.javaparser.ast.body.FieldDeclaration;

public interface IClassVisitor extends Visitor {

    void visit(FieldDeclaration fd, Void collector);

}
