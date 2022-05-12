package asynchJavaParser.eventDrivenJavaParser.lib.reporters;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IProjectReport;
import asynchJavaParser.eventDrivenJavaParser.lib.utils.FileExplorer;
import asynchJavaParser.eventDrivenJavaParser.lib.visitors.ProjectVisitor;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class ProjectReporter extends AbstractVerticle {
    private final Promise<IProjectReport> res;
    private final String path;
    private final FileExplorer fileExplorer;

    public ProjectReporter(Promise<IProjectReport> res, String path){
        this.res = res;
        this.path = path;
        this.fileExplorer = new FileExplorer(this.path);
    }

    @Override
    public void start() {
        CompilationUnit cu;
        List<String> packages = this.fileExplorer.getAllPackageFiles();
        ProjectVisitor visitor = new ProjectVisitor();
        // System.out.println("ALL PACKAGES: " + packages); // for debug purposes
        for (String p : packages) {
            // System.out.println("package and its content: " + p); // for debug purposes
            try {
                log("Project reporter started...");
                cu = StaticJavaParser.parse(new File(p));
                visitor.visit(cu, visitor.getProjectReport());
            } catch (FileNotFoundException e) {
                log("Project reporter failed...");
                res.fail("invalid path");
            }
        }
        res.complete(visitor.getProjectReport());
    }
    private static void log(String msg) {
        System.out.println("" + Thread.currentThread() + " " + msg);
    }

//    private List<String> getAllPackageFiles() {
//        List<String> fileList = new ArrayList<>();
//        getAllPackageList().stream().map(this::getFileList).forEach(fileList::addAll);
//        return fileList;
//    }
//
//    private List<String> getFileList(String p) {
//        File[] files = new File(p).listFiles(File::isFile);
//        return Arrays.stream(files).map(File::toString).collect(Collectors.toList());
//    }
//
//    private List<String> getAllPackageList() {
//        List<String> subPackages = new ArrayList<>();
//        List<String> allPackages = getPackageList(this.path);
//        allPackages.stream().map(this::getPackageList).forEach(subPackages::addAll); // extracting all sub-packages
//        allPackages.add(this.path); // add the most outer project package
//        allPackages.addAll(subPackages); // add all sub-packages to the outer packages
//        return allPackages.stream().distinct().collect(Collectors.toList());
//    }
//
//    private List<String> getPackageList(String p) {
//        File[] packages = new File(p).listFiles(File::isDirectory);
//        return Arrays.stream(packages).map(File::toString).collect(Collectors.toList()); // take all outer packages
//    }
}
