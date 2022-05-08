package asynchJavaParser.eventDrivenJavaParser.reports.interfaces;

import java.util.List;

public interface IProjectReport {

	void setMainClass(String mainClass);

	void addPackageReport(IPackageReport pr);

	List<IPackageReport> getPackageReports();

	String getMainClass();
}
