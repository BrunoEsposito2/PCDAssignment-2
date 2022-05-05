package asynchJavaParser.eventDrivenJavaParser.reports;

import com.github.javaparser.Position;
import com.github.javaparser.ast.type.Type;

import java.util.*;

public class MethodInfo implements IMethodInfo{
  private final String name;
  private final Optional<Position> srcBeginLine;
  private final Optional<Position> endBeginLine;
  private IClassReport parent;
  private Type returnType;
  private List<String> parameters;

  public MethodInfo(String name, Optional<Position> srcBeginLine, Optional<Position> endBeginLine) {
    this.name = name;
    this.srcBeginLine = srcBeginLine;
    this.endBeginLine = endBeginLine;
    this.parameters = new ArrayList<>();
  }

  public MethodInfo(String name, Optional<Position> srcBeginLine, Optional<Position> endBeginLine, IClassReport parent) {
    this.name = name;
    this.srcBeginLine = srcBeginLine;
    this.endBeginLine = endBeginLine;
    this.parent = parent;
    this.parameters = new ArrayList<>();
  }

  @Override
  public void addParameter(final String parameter) {
    this.parameters.add(parameter);
  }

  @Override
  public List<String> getParameters() {
    return Collections.unmodifiableList(this.parameters);
  }

  @Override
  public void setReturnType(final Type returnType) {
    this.returnType = returnType;
  }

  @Override
  public Type getReturnType() {
    return returnType;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Position getSrcBeginLine() {
    return this.srcBeginLine.get();
  }

  @Override
  public Position getEndBeginLine() {
    return this.endBeginLine.get();
  }

  @Override
  public IClassReport getParent() {
    return this.parent;
  }
}
