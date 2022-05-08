package asynchJavaParser.eventDrivenJavaParser.reports;

import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IPackageReport;
import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IProjectReport;

import java.util.ArrayList;
import java.util.List;

public class ProjectReport implements IProjectReport {
  private String mainClass;
  private final List<IPackageReport> packageReports;

  public ProjectReport() {
    this.packageReports = new ArrayList<>();
  }

  @Override
  public void setMainClass(String mainClass) {
    this.mainClass = mainClass;
  }

  @Override
  public void addPackageReport(IPackageReport pr) {
    this.packageReports.add(pr);
  }

  @Override
  public List<IPackageReport> getPackageReports() {
    return this.packageReports;
  }

  @Override
  public String getMainClass() {
    return this.mainClass;
  }

  @Override
  public String toString() {
    return "ProjectReport{" +
            "mainClass='" + mainClass + '\'' +
            ", packageReports=" + packageReports +
            '}';
  }
}
