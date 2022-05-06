package asynchJavaParser.eventDrivenJavaParser.reports.interfaces;

import java.util.List;

public interface IProjectReport {

	IClassReport getMainClass();

	List<IClassReport> getAllClasses();

	IClassReport getClassReport(String fullClassName);
}
