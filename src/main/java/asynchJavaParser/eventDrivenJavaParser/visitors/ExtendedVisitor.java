package asynchJavaParser.eventDrivenJavaParser.visitors;

import com.github.javaparser.ast.body.FieldDeclaration;

public interface ExtendedVisitor extends Visitor {

    void visit(FieldDeclaration fd, Void collector);

}
