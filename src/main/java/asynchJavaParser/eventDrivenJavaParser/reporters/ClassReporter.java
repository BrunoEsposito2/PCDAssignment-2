package asynchJavaParser.eventDrivenJavaParser.reporters;

import asynchJavaParser.eventDrivenJavaParser.reports.ClassReport;
import asynchJavaParser.eventDrivenJavaParser.reports.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.visitors.FullCollector;
import asynchJavaParser.eventDrivenJavaParser.visitors.MethodNameCollector;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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
                cu = StaticJavaParser.parse(new File("src/main/java/Ass_02/AsynchJavaParser.EventDrivenJavaParser/EDProjectAnalyzer.java"));
            } catch (FileNotFoundException e) {
                log("3");
                res.fail("invalid path");
            }

            var methodNames = new ArrayList<String>();
            var methodNameCollector = new MethodNameCollector();
            methodNameCollector.visit(cu,methodNames);
            methodNames.forEach(n -> System.out.println("MethodNameCollected:" + n));

            var fullc = new FullCollector();
            fullc.visit(cu, null);
            ClassReport classReport = new ClassReport("nomeClasse", "nomeFile", new ArrayList<>(), new ArrayList<>());
            log("3");

            res.complete(classReport);
    }
    private static void log(String msg) {
        System.out.println("" + Thread.currentThread() + " " + msg);
    }
}
