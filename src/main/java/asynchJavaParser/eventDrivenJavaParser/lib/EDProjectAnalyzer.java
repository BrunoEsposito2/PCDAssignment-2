package asynchJavaParser.eventDrivenJavaParser.lib;

import asynchJavaParser.eventDrivenJavaParser.lib.projectAnalysis.AnalyzeProjectConfig;
import asynchJavaParser.eventDrivenJavaParser.lib.projectAnalysis.ProjectAnalyzerReporter;
import asynchJavaParser.eventDrivenJavaParser.lib.reporters.ClassReporter;
import asynchJavaParser.eventDrivenJavaParser.lib.reporters.ElemNumberReporter;
import asynchJavaParser.eventDrivenJavaParser.lib.reporters.PackageReporter;
import asynchJavaParser.eventDrivenJavaParser.lib.reporters.ProjectReporter;
import asynchJavaParser.common.reports.interfaces.IClassReport;
import asynchJavaParser.common.reports.interfaces.IPackageReport;
import asynchJavaParser.common.reports.interfaces.IProjectReport;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;

import java.util.concurrent.atomic.AtomicReference;
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
  public void analyzeProject(AnalyzeProjectConfig conf, Consumer<Message<?>> callback) {

    AtomicReference<Integer> receivedMsgNr = new AtomicReference<>(0);
    AtomicReference<Integer> expectedMsgNr = new AtomicReference<>(0);

    Promise<Integer> promiseElemNr = Promise.promise();
    ElemNumberReporter pnr = new ElemNumberReporter(promiseElemNr, conf.getSrcProjectFolderName());
    this.vertx.deployVerticle(pnr);
    Future<Integer> futureElemNr = promiseElemNr.future();

    futureElemNr.onSuccess((a) -> {
      expectedMsgNr.set(futureElemNr.result());
      ProjectAnalyzerReporter par = new ProjectAnalyzerReporter(conf);
      this.vertx.deployVerticle(par);

    }).onFailure(status -> {
      System.out.println("error"); //TODO return failure message
    });

    //Message handler registration.
    //The messages will be generated and received only after elemNumber is completed with success, setting "expected" variable.
    MessageConsumer<String> consumer = eventBus.consumer(conf.getResponseAddress());
    consumer.handler(message -> {
      callback.accept(message);
      receivedMsgNr.getAndSet(receivedMsgNr.get() + 1);
      System.out.println(receivedMsgNr.get()+"/"+expectedMsgNr.get());
      if(receivedMsgNr.get().equals(expectedMsgNr.get())){
        vertx.eventBus().send(conf.getCompletitionNotifAddress(), "complete");
      }
    });
  }

  public void stopAnalyzeProject(){
    eventBus.publish("projectVisitor", "STOP");
  }
}

