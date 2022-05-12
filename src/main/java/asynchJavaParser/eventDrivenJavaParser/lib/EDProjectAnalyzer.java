package asynchJavaParser.eventDrivenJavaParser.lib;

import asynchJavaParser.eventDrivenJavaParser.lib.projectAnalyzer.AnalyzeProjectConfig;
import asynchJavaParser.eventDrivenJavaParser.lib.projectAnalyzer.ResponsiveProjectVisitor;
import asynchJavaParser.eventDrivenJavaParser.lib.reporters.ClassReporter;
import asynchJavaParser.eventDrivenJavaParser.lib.reporters.PackageReporter;
import asynchJavaParser.eventDrivenJavaParser.lib.reporters.ProjectReporter;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IPackageReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IProjectReport;
import asynchJavaParser.eventDrivenJavaParser.lib.utils.Reporter;
import asynchJavaParser.eventDrivenJavaParser.lib.visitors.ElemCounterCollector;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class EDProjectAnalyzer implements IProjectAnalyzer {

  private final Vertx vertx;
  private final EventBus eventBus;
  private Reporter reporter;


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

    AtomicReference<Integer> messageReceived = new AtomicReference<>(0);
    Integer expected = 0;
    //registra l'handler dei messaggi alla loro ricezione (cioè la callback)


    ResponsiveProjectVisitor rpv = new ResponsiveProjectVisitor(this.vertx, conf.getResponseAddress(), "projectVisitor");
    CompilationUnit cu = null;

    this.reporter = new Reporter(conf.getSrcProjectFolderName());

    ElemCounterCollector ecc = new ElemCounterCollector();

    for (final String pkg : reporter.getAllPackageFiles()) {
      try {
        cu = StaticJavaParser.parse(new File(pkg));
        ecc.visit(cu, null);
        expected = ecc.getCount();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }

    MessageConsumer<String> consumer = eventBus.consumer(conf.getResponseAddress());
    Integer finalExpected = expected;
    consumer.handler(message -> {
      callback.accept(message);
      messageReceived.getAndSet(messageReceived.get() + 1);
      if(messageReceived.get().equals(finalExpected)){
        vertx.eventBus().send(conf.getCompletitionNotifAddress(), "complete");
      }
    });

    rpv.visit(cu, null);
  }

  public void stopAnalyzeProject(){
    eventBus.publish("projectVisitor", "STOP");
  }

  private static void log(String msg) {
    System.out.println("" + Thread.currentThread() + " " + msg);
  }

}

