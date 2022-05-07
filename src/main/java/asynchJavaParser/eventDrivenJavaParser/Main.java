package asynchJavaParser.eventDrivenJavaParser;

import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IClassReport;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

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
            cr.onSuccess(res -> {
                System.out.println(res.getSrcFullFileName());
            });

            cr.onFailure(res -> {
                System.out.println("failure");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
