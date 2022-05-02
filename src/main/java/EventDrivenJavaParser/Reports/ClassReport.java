package EventDrivenJavaParser.Reports;

import java.util.List;

public class ClassReport implements IClassReport{
  private String fullClassName;
  private String srcFullFileName;
  private List<IMethodInfo> methodsInfo;
  private List<IFieldInfo> fieldsInfo;

  public ClassReport(String fullClassName, String srcFullFileName, List<IMethodInfo> methodsInfo, List<IFieldInfo> fieldsInfo) {
    this.fullClassName = fullClassName;
    this.srcFullFileName = srcFullFileName;
    this.methodsInfo = methodsInfo;
    this.fieldsInfo = fieldsInfo;
  }

  @Override
  public String getFullClassName() {
    return this.fullClassName;
  }

  @Override
  public String getSrcFullFileName() {
    return this.srcFullFileName;
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
