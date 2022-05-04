package asynchJavaParser.eventDrivenJavaParser;

import asynchJavaParser.eventDrivenJavaParser.reports.IClassReport;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class Main {
    public static void main(String[] args){
        Vertx v = Vertx.vertx();
        IProjectAnalyzer pa = new EDProjectAnalyzer(v);
        Future<IClassReport> res = pa.getClassReport("aaa");
        res.onComplete(result -> {
           System.out.println("4");
        });
    }
}
