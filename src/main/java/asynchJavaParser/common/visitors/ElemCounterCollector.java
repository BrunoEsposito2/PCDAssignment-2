package asynchJavaParser.common.visitors;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ElemCounterCollector extends VoidVisitorAdapter<Void> {
    private Integer count;
    public ElemCounterCollector(Integer count){
        this.count = count;
    }

    @Override
    public void visit(PackageDeclaration fd, Void collector) {
        super.visit(fd, collector);
        this.count++;
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, Void collector) {
        super.visit(cd, collector);
        this.count++;
    }

    @Override
    public void visit(MethodDeclaration md, Void collector) {
        super.visit(md, collector);
        this.count++;
    }

    @Override
    public void visit(FieldDeclaration fd, Void collector) {
        super.visit(fd, collector);
        this.count++;
    }

    public Integer getCount(){
        return this.count;
    }
}
