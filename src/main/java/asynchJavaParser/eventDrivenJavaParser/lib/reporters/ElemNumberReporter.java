package asynchJavaParser.eventDrivenJavaParser.lib.reporters;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.ClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.InterfaceReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.PackageReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IInterfaceReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IPackageReport;
import asynchJavaParser.eventDrivenJavaParser.lib.utils.FileExplorer;
import asynchJavaParser.eventDrivenJavaParser.lib.visitors.ElemCounterCollector;
import asynchJavaParser.eventDrivenJavaParser.lib.visitors.PackageVisitor;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class ElemNumberReporter extends AbstractVerticle {
    private final Promise<Integer> res;
    private final String path;
    private final FileExplorer fileExplorer;

    public ElemNumberReporter(Promise<Integer> res, String path){
        this.res = res;
        this.path = path;
        this.fileExplorer = new FileExplorer(this.path);
    }

    @Override
    public void start() {
        CompilationUnit cu;
        List<String> files = this.fileExplorer.getAllPackageFiles();
        Integer result = 0;
        ElemCounterCollector visitor = new ElemCounterCollector();
        for (String nameFile : files) {
            // System.out.println("nome file: " + nameFile); // for debug purposes
            try {
                log("Package reporter started...");
                cu = StaticJavaParser.parse(new File(nameFile));
                visitor.visit(cu, null);
                result = visitor.getCount();
            } catch (FileNotFoundException e) {
                log("Package reporter failed...");
                res.fail("invalid path");
            }
        }
        res.complete(result);
    }
    private static void log(String msg) {
        System.out.println("" + Thread.currentThread() + " " + msg);
    }
}