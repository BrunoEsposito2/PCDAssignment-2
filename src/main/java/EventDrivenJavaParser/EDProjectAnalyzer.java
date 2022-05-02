package EventDrivenJavaParser;

import EventDrivenJavaParser.Visitors.FullCollector;
import EventDrivenJavaParser.Visitors.MethodNameCollector;
import EventDrivenJavaParser.Reports.ClassReport;
import EventDrivenJavaParser.Reports.IClassReport;
import EventDrivenJavaParser.Reports.IPackageReport;
import EventDrivenJavaParser.Reports.IProjectReport;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.vertx.core.Future;
import io.vertx.core.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class EDProjectAnalyzer implements IProjectAnalyzer {

  private Vertx vertx;
  public EDProjectAnalyzer(Vertx v){
    this.vertx = v;
  }

  @Override
  public Future<IClassReport> getClassReport(String srcClassPath) {
    log("pre");

    Promise<IClassReport> promise = Promise.promise();

    this.vertx.runOnContext(res -> {

      CompilationUnit cu = null;
      try {
        cu = StaticJavaParser.parse(new File("src/main/java/Ass_02/EventDrivenJavaParser/EDProjectAnalyzer.java"));
      } catch (FileNotFoundException e) {
        log("...complete with failure.");
        promise.fail("invalid path");
      }

      var methodNames = new ArrayList<String>();
      var methodNameCollector = new MethodNameCollector();
      methodNameCollector.visit(cu,methodNames);
      methodNames.forEach(n -> System.out.println("MethodNameCollected:" + n));

      var fullc = new FullCollector();
      fullc.visit(cu, null);
      ClassReport classReport = new ClassReport("nomeClasse", "nomeFile", new ArrayList<>(), new ArrayList<>());
      log("...complete with success.");
      promise.complete(classReport);
    });
    return promise.future();
  }

  @Override
  public Future<IPackageReport> getPackageReport(String srcPackagePath) {
    return null;
  }

  @Override
  public Future<IProjectReport> getProjectReport(String srcProjectFolderPath) {
    return null;
  }

  @Override
  public void analyzeProject(String srcProjectFolderName, Consumer<IProjectElem> callback) {

  }
  private static void log(String msg) {
    System.out.println("" + Thread.currentThread() + " " + msg);
  }

}

