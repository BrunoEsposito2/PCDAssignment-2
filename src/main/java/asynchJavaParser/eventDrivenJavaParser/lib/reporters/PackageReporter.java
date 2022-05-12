package asynchJavaParser.eventDrivenJavaParser.lib.reporters;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.ClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.InterfaceReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.PackageReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IInterfaceReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IPackageReport;
import asynchJavaParser.eventDrivenJavaParser.lib.utils.Reporter;
import asynchJavaParser.eventDrivenJavaParser.lib.visitors.PackageVisitor;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class PackageReporter extends AbstractVerticle {
    private final Promise<IPackageReport> res;
    private final String path;
    private final Reporter reporter;

    public PackageReporter(Promise<IPackageReport> res, String path){
        this.res = res;
        this.path = path;
        this.reporter = new Reporter(this.path);
    }

    @Override
    public void start() {
        CompilationUnit cu;
        List<String> files = this.reporter.getFileList(this.path);
        IPackageReport packageReport = new PackageReport();
        for (String nameFile : files) {
            // System.out.println("nome file: " + nameFile); // for debug purposes
            try {
                log("Package reporter started...");
                cu = StaticJavaParser.parse(new File(nameFile));
                IClassReport classReport = new ClassReport();
                IInterfaceReport interfaceReport = new InterfaceReport();
                PackageVisitor visitor = new PackageVisitor(packageReport, classReport, interfaceReport);
                visitor.visit(cu, null);
            } catch (FileNotFoundException e) {
                log("Package reporter failed...");
                res.fail("invalid path");
            }
        }
        res.complete(packageReport);
    }
    private static void log(String msg) {
        System.out.println("" + Thread.currentThread() + " " + msg);
    }

//    private List<String> getFileList() {
//        File[] files = new File(this.path).listFiles(File::isFile);
//        return Arrays.stream(files).map(File::toString).collect(Collectors.toList());
//    }
}
