package asynchJavaParser.eventDrivenJavaParser.reports;

import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IFieldInfo;
import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IMethodInfo;
import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IPackageReport;

import java.util.List;

public class PackageReport implements IPackageReport {
  private String fullClassName;
  private String srcFullClassName;
  private List<IMethodInfo> methodsInfo;
  private List<IFieldInfo> fieldsInfo;

  public PackageReport(){

  }

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
