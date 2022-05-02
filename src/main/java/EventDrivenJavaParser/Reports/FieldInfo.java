package EventDrivenJavaParser.Reports;

public class FieldInfo implements IFieldInfo{
  private final String name;
  private final String fieldTypeFullName;
  private final IClassReport parent;

  public FieldInfo(String name, String fieldTypeFullName, IClassReport parent) {
    this.name = name;
    this.fieldTypeFullName = fieldTypeFullName;
    this.parent = parent;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getFieldTypeFullName() {
    return this.fieldTypeFullName;
  }

  @Override
  public IClassReport getParent() {
    return this.parent;
  }
}
