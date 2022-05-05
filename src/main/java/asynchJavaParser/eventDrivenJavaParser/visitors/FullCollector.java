package asynchJavaParser.eventDrivenJavaParser.visitors;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class FullCollector extends VoidVisitorAdapter<Void> {

  public void visit(PackageDeclaration fd, Void collector) {
    super.visit(fd, collector);
    System.out.println(fd);
  }

  public void visit(ClassOrInterfaceDeclaration cd, Void collector) {
    super.visit(cd, collector);
    System.out.println(cd.getNameAsString());
  }

  public void visit(FieldDeclaration fd, Void collector) {
    super.visit(fd, collector);
    System.out.println(fd);
  }

  public void visit(MethodDeclaration md, Void collector) {
    super.visit(md, collector);
    System.out.println(md.getName());
  }
}
