package asynchJavaParser.eventDrivenJavaParser.reports.projectElements;

import com.github.javaparser.ast.body.FieldDeclaration;

public class FieldProjectElem implements IProjectElem {
    private final ElemType type;
    private final FieldDeclaration fd;

    public FieldProjectElem(ElemType type, FieldDeclaration fd) {
        this.type = type;
        this.fd = fd;
    }

    @Override
    public ElemType getType() {
        return this.type;
    }
}
