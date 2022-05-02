package AsynchJavaParser.EventDrivenJavaParser.Reports;

import java.util.List;

public class PackageReport implements IPackageReport{
  private final String fullClassName;
  private final String srcFullClassName;
  private final List<IMethodInfo> methodsInfo;
  private final List<IFieldInfo> fieldsInfo;

  public PackageReport(String fullClassName, String srcFullClassName, List<IMethodInfo> methodsInfo, List<IFieldInfo> fieldsInfo) {
    this.fullClassName = fullClassName;
    this.srcFullClassName = srcFullClassName;
    this.methodsInfo = methodsInfo;
    this.fieldsInfo = fieldsInfo;
  }

  @Override
  public String getFullClassName() {
    return this.fullClassName;
  }

  @Override
  public String getSrcFullFileName() {
    return this.srcFullClassName;
  }

  @Override
  public List<IMethodInfo> getMethodsInfo() {
    return this.methodsInfo;
  }

  @Override
  public List<IFieldInfo> getFieldsInfo() {
    return this.fieldsInfo;
  }
}
