package asynchJavaParser.eventDrivenJavaParser.lib.reports;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IMethodInfo;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IParameterInfo;
import com.github.javaparser.Position;
import com.github.javaparser.ast.type.Type;

import java.util.*;

public class MethodInfo implements IMethodInfo {
  private final String name;
  private final Optional<Position> srcBeginLine;
  private final Optional<Position> endBeginLine;
  private IClassReport parent;
  private Type returnType;
  private List<IParameterInfo> parameters;

  public MethodInfo(String name, Optional<Position> srcBeginLine, Optional<Position> endBeginLine) {
    this.name = name;
    this.srcBeginLine = srcBeginLine;
    this.endBeginLine = endBeginLine;
    this.parameters = new ArrayList<>();
  }

  @Override
  public void addParameter(final IParameterInfo parameter) {
    this.parameters.add(parameter);
  }

  @Override
  public List<IParameterInfo> getParameters() {
    return this.parameters;
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

  @Override
  public String toString() {
    return "MethodInfo{" +
            "name='" + name + '\'' +
            ", srcBeginLine=" + srcBeginLine +
            ", endBeginLine=" + endBeginLine +
            ", parent=" + parent +
            ", returnType=" + returnType +
            ", parameters=" + parameters +
            '}';
  }
}
