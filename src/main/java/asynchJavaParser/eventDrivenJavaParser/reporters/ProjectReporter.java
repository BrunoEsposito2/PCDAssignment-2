package asynchJavaParser.eventDrivenJavaParser.reporters;

import asynchJavaParser.eventDrivenJavaParser.reports.IProjectReport;
import asynchJavaParser.eventDrivenJavaParser.reports.ProjectReport;
import asynchJavaParser.eventDrivenJavaParser.visitors.ProjectVisitor;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import java.io.File;
import java.io.FileNotFoundException;

public class ProjectReporter extends AbstractVerticle {
    private final Promise<IProjectReport> res;
    private final String path;

    public ProjectReporter(Promise<IProjectReport> res, String path){
        this.res = res;
        this.path = path;
    }

    @Override
    public void start() throws Exception {
        CompilationUnit cu = null;
        try {
            log("task started...");
            cu = StaticJavaParser.parse(new File(this.path));
            IProjectReport projectReport = new ProjectReport();
            ProjectVisitor visitor = new ProjectVisitor(projectReport);
            visitor.visit(cu, null);
            res.complete(projectReport);
        } catch (FileNotFoundException e) {
            log("task failed...");
            res.fail("invalid path");
        }
    }
    private static void log(String msg) {
        System.out.println("" + Thread.currentThread() + " " + msg);
    }
}
