package asynchJavaParser.eventDrivenJavaParser;

import asynchJavaParser.eventDrivenJavaParser.reports.ClassReport;
import asynchJavaParser.eventDrivenJavaParser.reports.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.visitors.ClassVisitor;
import asynchJavaParser.eventDrivenJavaParser.visitors.InterfaceVisitor;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args){
        /*Vertx v = Vertx.vertx();
        IProjectAnalyzer pa = new EDProjectAnalyzer(v);
        Future<IClassReport> res = pa.getClassReport("src/main/java/asynchJavaParser/eventDrivenJavaParser/EDProjectAnalyzer.java");
        res.onComplete(result -> {
           System.out.println(res.result().getFullClassName());
        });*/

        try {
            CompilationUnit cu = StaticJavaParser.parse(new File("src/main/java/asynchJavaParser/eventDrivenJavaParser/EDProjectAnalyzer.java"));

            ClassVisitor cVisitor = new ClassVisitor();
            cVisitor.visit(cu, null);

            InterfaceVisitor iVisitor = new InterfaceVisitor();
            iVisitor.visit(cu, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
