package asynchJavaParser.eventDrivenJavaParser;

import asynchJavaParser.eventDrivenJavaParser.reports.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.reports.IPackageReport;
import asynchJavaParser.eventDrivenJavaParser.reports.IProjectReport;
import asynchJavaParser.eventDrivenJavaParser.reporters.ClassReporter;
import asynchJavaParser.eventDrivenJavaParser.reporters.PackageReporter;
import io.vertx.core.Future;
import io.vertx.core.*;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

import java.util.function.Consumer;

public class EDProjectAnalyzer implements IProjectAnalyzer {

  private final Vertx vertx;
  private final EventBus eventBus;
  public EDProjectAnalyzer(Vertx v){
    this.vertx = v;
    this.eventBus = v.eventBus();
    //registra il codificatore di messaggi per projectAnalyzer
    //eventBus.registerDefaultCodec(MyPOJO.class, myCodec);
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
    return null;
  }

  @Override
  public void analyzeProject(String srcProjectFolderName, Consumer<IProjectElem> callback) {

    //registra l'handler dei messaggi alla loro ricezione (cioè la callback)
    MessageConsumer<String> consumer = eventBus.consumer("news.uk.sport");
    consumer.handler(message -> {
      //callback.accept(message);
      System.out.println("I have received a message: " + message.body());
    });

    //nel visitor andrà passanto l'oggetto Event Bus perchè per ogni elemento che trova dovrà inviare un messaggio di questo tipo
    //eventBus.send("news.uk.sport", "Yay! Someone kicked a ball");
  }
  private static void log(String msg) {
    System.out.println("" + Thread.currentThread() + " " + msg);
  }

}

