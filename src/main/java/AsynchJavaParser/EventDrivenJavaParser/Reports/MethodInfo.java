package AsynchJavaParser.EventDrivenJavaParser.Reports;

public class MethodInfo implements IMethodInfo{
  private final String name;
  private final int srcBeginLine;
  private final int endBeginLine;
  private final IClassReport parent;

  public MethodInfo(String name, int srcBeginLine, int endBeginLine, IClassReport parent) {
    this.name = name;
    this.srcBeginLine = srcBeginLine;
    this.endBeginLine = endBeginLine;
    this.parent = parent;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getSrcBeginLine() {
    return this.srcBeginLine;
  }

  @Override
  public int getEndBeginLine() {
    return this.endBeginLine;
  }

  @Override
  public IClassReport getParent() {
    return this.parent;
  }
}
