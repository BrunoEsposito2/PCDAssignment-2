package asynchJavaParser.eventDrivenJavaParser;

import asynchJavaParser.eventDrivenJavaParser.reports.ClassReport;
import asynchJavaParser.eventDrivenJavaParser.reports.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.reports.IInterfaceReport;
import asynchJavaParser.eventDrivenJavaParser.reports.InterfaceReport;
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
            /*CompilationUnit cu = StaticJavaParser.parse(new File("src/main/java/asynchJavaParser/eventDrivenJavaParser/EDProjectAnalyzer.java"));

            IClassReport classReport = new ClassReport();
            ClassVisitor cVisitor = new ClassVisitor(classReport);
            cVisitor.visit(cu, null);

            IInterfaceReport interfaceReport = new InterfaceReport();
            InterfaceVisitor iVisitor = new InterfaceVisitor(interfaceReport);
            iVisitor.visit(cu, null);

            classReport.getFieldsInfo().forEach(x -> System.out.println(x));
            classReport.getMethodsInfo().forEach(x -> System.out.println(x.getName()));*/

            Vertx vx = Vertx.vertx();
            EDProjectAnalyzer analyzer = new EDProjectAnalyzer(vx);
            Future<IClassReport> cr = analyzer.getClassReport("src/main/java/asynchJavaParser/eventDrivenJavaParser/EDProjectAnalyzer.java");
            System.out.println(cr.result().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
