package asynchJavaParser.reactiveJavaParser;

import asynchJavaParser.common.reports.interfaces.IClassReport;
import asynchJavaParser.common.reports.interfaces.IPackageReport;
import asynchJavaParser.common.reports.interfaces.IProjectReport;
import io.reactivex.rxjava3.core.Observable;

public interface IProjectAnalyzer {

	/**
	 * Async method to retrieve the report about a specific class,
	 * given the full path of the class source file
	 *
	 * @param srcClassPath
	 * @return
	 */
	Observable<IClassReport> getClassReport(String srcClassPath);

	/**
	 * Async method to retrieve the report about a package,
	 * given the full path of the package folder
	 *
	 * @param srcPackagePath
	 * @return
	 */
	Observable<IPackageReport> getPackageReport(String srcPackagePath);

	/**
	 * Async method to retrieve the report about a project
	 * given the full path of the project folder
	 *
	 * @param srcProjectFolderPath
	 * @return
	 */
	Observable<IProjectReport> getProjectReport(String srcProjectFolderPath);

	/**
	 * Async function that analyze a project given the full path of the project folder,
	 * executing the callback each time a project element is found
	 *
	 * @param callback
	 */
	Observable<?> analyzeProject(String srcProjectFolderPath);
}
