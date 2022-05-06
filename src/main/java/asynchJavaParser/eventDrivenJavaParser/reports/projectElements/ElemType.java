package asynchJavaParser.eventDrivenJavaParser.reports.projectElements;

import com.github.javaparser.ast.body.FieldDeclaration;

public enum ElemType {
    FIELD ("FieldDeclaration"),
    METHOD,
    CLASS,
    INTERFACE,
    PACKAGE;

    ElemType(String fieldDeclaration) {
    }
}
