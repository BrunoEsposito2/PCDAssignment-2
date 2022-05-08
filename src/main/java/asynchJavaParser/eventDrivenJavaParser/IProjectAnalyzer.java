package asynchJavaParser.eventDrivenJavaParser;

import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IPackageReport;
import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IProjectReport;
import io.vertx.core.*;
import io.vertx.core.eventbus.Message;

import java.util.function.*;

public interface IProjectAnalyzer {

	/**
	 * Async method to retrieve the report about a specific class,
	 * given the full path of the class source file
	 *
	 * @param srcClassPath
	 * @return
	 */
	Future<IClassReport> getClassReport(String srcClassPath);

	/**
	 * Async method to retrieve the report about a package,
	 * given the full path of the package folder
	 *
	 * @param srcPackagePath
	 * @return
	 */
	Future<IPackageReport> getPackageReport(String srcPackagePath);

	/**
	 * Async method to retrieve the report about a project
	 * given the full path of the project folder
	 *
	 * @param srcProjectFolderPath
	 * @return
	 */
	Future<IProjectReport> getProjectReport(String srcProjectFolderPath);

	/**
	 * Async function that analyze a project given the full path of the project folder,
	 * executing the callback each time a project element is found
	 *
	 * @param srcProjectFolderName
	 * @param callback
	 * @param address
	 */
	void analyzeProject(String srcProjectFolderName, Consumer<Message> callback, String address);
}
