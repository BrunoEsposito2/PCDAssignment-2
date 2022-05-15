package asynchJavaParser.common.reports;

import asynchJavaParser.common.reports.interfaces.IClassReport;
import asynchJavaParser.common.reports.interfaces.IFieldInfo;

public class FieldInfo implements IFieldInfo {
  private String name;
  private String fieldTypeFullName;
  private IClassReport parent;

  public FieldInfo(String name, String fieldTypeFullName) {
    this.name = name;
    this.fieldTypeFullName = fieldTypeFullName;
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

  @Override
  public String toString() {
    return "FieldInfo{" +
            "name='" + name + '\'' +
            ", fieldTypeFullName='" + fieldTypeFullName + '\'' +
            ", parent=" + parent +
            '}';
  }
}
