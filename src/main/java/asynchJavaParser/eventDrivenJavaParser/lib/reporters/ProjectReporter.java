package asynchJavaParser.eventDrivenJavaParser.lib.reporters;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.ClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.InterfaceReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.PackageReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.ProjectReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IInterfaceReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IPackageReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IProjectReport;
import asynchJavaParser.eventDrivenJavaParser.lib.visitors.ProjectVisitor;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectReporter extends AbstractVerticle {
    private final Promise<IProjectReport> res;
    private final String path;

    public ProjectReporter(Promise<IProjectReport> res, String path){
        this.res = res;
        this.path = path;
    }

    @Override
    public void start() {
        CompilationUnit cu;
        List<String> packages = getAllPackageFiles();
        // System.out.println("ALL PACKAGES: " + packages); // for debug purposes
        IProjectReport projectReport = new ProjectReport();
        for (String p : packages) {
            // System.out.println("package and its content: " + p); // for debug purposes
            try {
                log("Project reporter started...");
                cu = StaticJavaParser.parse(new File(p));
                IPackageReport packageReport = new PackageReport();
                IClassReport classReport = new ClassReport();
                IInterfaceReport interfaceReport = new InterfaceReport();
                ProjectVisitor visitor = new ProjectVisitor(projectReport, packageReport, classReport, interfaceReport);
                visitor.visit(cu, null);
            } catch (FileNotFoundException e) {
                log("Project reporter failed...");
                res.fail("invalid path");
            }
        }
        res.complete(projectReport);
    }
    private static void log(String msg) {
        System.out.println("" + Thread.currentThread() + " " + msg);
    }

    private List<String> getAllPackageFiles() {
        List<String> fileList = new ArrayList<>();
        getAllPackageList().stream().map(this::getFileList).forEach(fileList::addAll);
        return fileList;
    }

    private List<String> getFileList(String p) {
        File[] files = new File(p).listFiles(File::isFile);
        return Arrays.stream(files).map(File::toString).collect(Collectors.toList());
    }

    private List<String> getAllPackageList() {
        List<String> subPackages = new ArrayList<>();
        List<String> allPackages = getPackageList(this.path);
        allPackages.stream().map(this::getPackageList).forEach(subPackages::addAll); // extracting all sub-packages
        allPackages.add(this.path); // add the most outer project package
        allPackages.addAll(subPackages); // add all sub-packages to the outer packages
        return allPackages.stream().distinct().collect(Collectors.toList());
    }

    private List<String> getPackageList(String p) {
        File[] packages = new File(p).listFiles(File::isDirectory);
        return Arrays.stream(packages).map(File::toString).collect(Collectors.toList()); // take all outer packages
    }
}
