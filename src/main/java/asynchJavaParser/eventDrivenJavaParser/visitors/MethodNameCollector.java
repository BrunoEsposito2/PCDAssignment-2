package asynchJavaParser.eventDrivenJavaParser.visitors;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class MethodNameCollector extends VoidVisitorAdapter<List<String>> {
  public void visit(MethodDeclaration md, List<String> collector) {
    super.visit(md, collector);
    collector.add(md.getNameAsString());
  }
}
