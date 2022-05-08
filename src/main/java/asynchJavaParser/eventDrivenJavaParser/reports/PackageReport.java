package asynchJavaParser.eventDrivenJavaParser.reports;

import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IInterfaceReport;
import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IPackageReport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PackageReport implements IPackageReport {
  private String packageName;
  private List<IClassReport> classReports;
  private List<IInterfaceReport> interfaceReports;

  public PackageReport(){
    this.classReports = new ArrayList<>();
    this.interfaceReports = new ArrayList<>();
  }

  @Override
  public void setSrcFullName(String srcFullName) {
    this.packageName = srcFullName;
  }

  @Override
  public void addClassReport(IClassReport cr) {
    this.classReports.add(cr);
  }

  @Override
  public void addInterfaceReport(IInterfaceReport ir) {
    this.interfaceReports.add(ir);
  }

  @Override
  public String getSrcFullName() {
    return this.packageName;
  }

  @Override
  public List<IClassReport> getClassReports() {
    return Collections.unmodifiableList(this.classReports);
  }

  @Override
  public List<IInterfaceReport> getInterfaceReports() {
    return Collections.unmodifiableList(this.interfaceReports);
  }

  @Override
  public String toString() {
    return "PackageReport{" +
            "packageName='" + packageName + '\'' +
            ", classReports=" + classReports +
            ", interfaceReports=" + interfaceReports +
            '}';
  }
}
