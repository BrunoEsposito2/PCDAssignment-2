package asynchJavaParser.eventDrivenJavaParser.reports.interfaces;

import java.util.List;

public interface IPackageReport {

	void setSrcFullName(String srcFullName);

	void addClassReport(IClassReport cr);

	void addInterfaceReport(IInterfaceReport ir);

	String getSrcFullName();

	List<IClassReport> getClassReports();

	List<IInterfaceReport> getInterfaceReports();

}
