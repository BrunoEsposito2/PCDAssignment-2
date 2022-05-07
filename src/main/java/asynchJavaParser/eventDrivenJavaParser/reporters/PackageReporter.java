package asynchJavaParser.eventDrivenJavaParser.reporters;

import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IPackageReport;
import asynchJavaParser.eventDrivenJavaParser.reports.PackageReport;
import asynchJavaParser.eventDrivenJavaParser.visitors.PackageVisitor;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import java.io.File;
import java.io.FileNotFoundException;

public class PackageReporter extends AbstractVerticle {
    private final Promise<IPackageReport> res;
    private final String path;

    public PackageReporter(Promise<IPackageReport> res, String path){
        this.res = res;
        this.path = path;
    }

    @Override
    public void start() throws Exception {
        CompilationUnit cu = null;
        try {
            log("task started...");
            cu = StaticJavaParser.parse(new File(this.path));
            IPackageReport packageReport = new PackageReport();
            PackageVisitor visitor = new PackageVisitor(packageReport);
            visitor.visit(cu, null);
            res.complete(packageReport);
        } catch (FileNotFoundException e) {
            log("task failed...");
            res.fail("invalid path");
        }
    }
    private static void log(String msg) {
        System.out.println("" + Thread.currentThread() + " " + msg);
    }
}
