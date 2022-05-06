package asynchJavaParser.eventDrivenJavaParser.reports;

import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IFieldInfo;

public class FieldInfo implements IFieldInfo {
  private String name;
  private String fieldTypeFullName;
  private IClassReport parent;

  public FieldInfo(String name, String fieldTypeFullName, IClassReport parent) {
    this.name = name;
    this.fieldTypeFullName = fieldTypeFullName;
    this.parent = parent;
  }

  public FieldInfo(String name, String fieldTypeFullName) {
    this.name = name;
    this.fieldTypeFullName = fieldTypeFullName;
  }

  public FieldInfo() {
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
