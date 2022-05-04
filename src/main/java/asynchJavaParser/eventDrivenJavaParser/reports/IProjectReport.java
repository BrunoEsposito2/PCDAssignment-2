package asynchJavaParser.EventDrivenJavaParser.Reports;

import java.util.List;

public interface IProjectReport {

	IClassReport getMainClass();

	List<IClassReport> getAllClasses();

	IClassReport getClassReport(String fullClassName);
}
