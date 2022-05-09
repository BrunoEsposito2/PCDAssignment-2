package asynchJavaParser.eventDrivenJavaParser.lib;

import asynchJavaParser.eventDrivenJavaParser.lib.projectAnalyzer.AnalyzeProjectConfig;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IPackageReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IProjectReport;
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
	 * @param callback
	 */
	void analyzeProject(AnalyzeProjectConfig conf, Consumer<Message<?>> callback);
}
