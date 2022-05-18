package asynchJavaParser.eventDrivenJavaParser.lib.reporters;

import asynchJavaParser.common.reports.PackageReport;
import asynchJavaParser.common.reports.interfaces.IPackageReport;
import asynchJavaParser.common.utils.FileExplorer;
import asynchJavaParser.common.visitors.PackageVisitor;
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
        if (this.fileExplorer.getPath().contains(".java")) {
            res.fail("invalid path for Package Report");
            return;
        }
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
