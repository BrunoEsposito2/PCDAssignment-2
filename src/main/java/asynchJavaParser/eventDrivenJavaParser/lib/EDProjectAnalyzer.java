package asynchJavaParser.eventDrivenJavaParser.lib;

import asynchJavaParser.eventDrivenJavaParser.lib.projectAnalyzer.ResponsiveProjectVisitor;
import asynchJavaParser.eventDrivenJavaParser.lib.reporters.ProjectReporter;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IPackageReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IProjectReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reporters.ClassReporter;
import asynchJavaParser.eventDrivenJavaParser.lib.reporters.PackageReporter;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.vertx.core.Future;
import io.vertx.core.*;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.function.Consumer;

public class EDProjectAnalyzer implements IProjectAnalyzer {

  private final Vertx vertx;
  private final EventBus eventBus;


  public EDProjectAnalyzer(Vertx v){
    this.vertx = v;
    this.eventBus = v.eventBus();
  }

  @Override
  public Future<IClassReport> getClassReport(String srcClassPath) {
    Promise<IClassReport> promise = Promise.promise();
    ClassReporter reporter = new ClassReporter(promise, srcClassPath);
    this.vertx.deployVerticle(reporter);
    return promise.future();
  }

  @Override
  public Future<IPackageReport> getPackageReport(String srcPackagePath) {
    Promise<IPackageReport> promise = Promise.promise();
    PackageReporter reporter = new PackageReporter(promise, srcPackagePath);
    this.vertx.deployVerticle(reporter);
    return promise.future();
  }

  @Override
  public Future<IProjectReport> getProjectReport(String srcProjectFolderPath) {
    Promise<IProjectReport> promise = Promise.promise();
    ProjectReporter reporter = new ProjectReporter(promise, srcProjectFolderPath);
    this.vertx.deployVerticle(reporter);
    return promise.future();
  }

  @Override
  public void analyzeProject(String srcProjectFolderName, Consumer<Message<?>> callback, String address) {

    //registra l'handler dei messaggi alla loro ricezione (cioè la callback)
    MessageConsumer<String> consumer = eventBus.consumer(address);
    consumer.handler(callback::accept);

    ResponsiveProjectVisitor rpv = new ResponsiveProjectVisitor(this.vertx, address, "projectVisitor");
    CompilationUnit cu = null;
    try {
      cu = StaticJavaParser.parse(new File(srcProjectFolderName));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    rpv.visit(cu, null);
  }

  public void stopAnalyzeProject(){
    eventBus.publish("projectVisitor", "STOP");
  }

  private static void log(String msg) {
    System.out.println("" + Thread.currentThread() + " " + msg);
  }

}

