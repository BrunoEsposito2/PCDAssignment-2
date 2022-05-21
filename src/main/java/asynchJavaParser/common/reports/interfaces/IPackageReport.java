package asynchJavaParser.common.reports.interfaces;

import java.util.List;

public interface IPackageReport extends IReport {

	void addClassReport(IClassReport cr);

	void addInterfaceReport(IInterfaceReport ir);

	List<IClassReport> getClassReports();

	List<IInterfaceReport> getInterfaceReports();

}
