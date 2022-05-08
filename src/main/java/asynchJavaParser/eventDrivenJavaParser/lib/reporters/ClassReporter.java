package asynchJavaParser.eventDrivenJavaParser.lib.reporters;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.ClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.visitors.ClassVisitor;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import java.io.File;
import java.io.FileNotFoundException;

public class ClassReporter extends AbstractVerticle {
    private final Promise<IClassReport> res;
    private final String path;

    public ClassReporter(Promise<IClassReport> res, String path){
        this.res = res;
        this.path = path;
    }

    @Override
    public void start() throws Exception {
        CompilationUnit cu = null;
        try {
            cu = StaticJavaParser.parse(new File(this.path));

            IClassReport classReport = new ClassReport();
            ClassVisitor visitor = new ClassVisitor(classReport);

            visitor.visit(cu, null);
            log("task started...");
            res.complete(classReport);
        } catch (FileNotFoundException e) {
            log("task failed...");
            res.fail("invalid path");
        }
    }
    private static void log(String msg) {
        System.out.println("" + Thread.currentThread() + " " + msg);
    }
}
