package asynchJavaParser.eventDrivenJavaParser.lib.reporters;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.PackageReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IPackageReport;
import asynchJavaParser.eventDrivenJavaParser.lib.utils.FileExplorer;
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
    private final FileExplorer fileExplorer;

    public PackageReporter(Promise<IPackageReport> res, String path){
        this.res = res;
        this.path = path;
        this.fileExplorer = new FileExplorer(this.path);
    }

    @Override
    public void start() {
        CompilationUnit cu;
        List<String> files = this.fileExplorer.getAllPackageFiles();
        IPackageReport packageReport = new PackageReport();
        for (String nameFile : files) {
            // System.out.println("nome file: " + nameFile); // for debug purposes
            try {
                log("Package reporter started...");
                cu = StaticJavaParser.parse(new File(nameFile));
                PackageVisitor visitor = new PackageVisitor();
                visitor.visit(cu, packageReport);
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

}
