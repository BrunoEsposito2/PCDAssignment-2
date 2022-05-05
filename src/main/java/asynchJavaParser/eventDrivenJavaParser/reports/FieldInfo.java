package asynchJavaParser.eventDrivenJavaParser.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FieldInfo implements IFieldInfo{
  private String name;
  private String fieldTypeFullName;
  private IClassReport parent;

  private List<String> fields;
  public FieldInfo(String name, String fieldTypeFullName, IClassReport parent) {
    this.name = name;
    this.fieldTypeFullName = fieldTypeFullName;
    this.parent = parent;
    this.fields = new ArrayList<>();
  }

  public FieldInfo(String name, String fieldTypeFullName) {
    this.name = name;
    this.fieldTypeFullName = fieldTypeFullName;
    this.fields = new ArrayList<>();
  }

  public FieldInfo() {
  }

  public void addField(String field) {
    this.fields.add(field);
  }

  public List<String> getFields() {
    return Collections.unmodifiableList(this.fields);
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
            ", fields=" + fields +
            '}';
  }
}
