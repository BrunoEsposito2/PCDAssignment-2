package asynchJavaParser.reactiveJavaParser;

import asynchJavaParser.common.reports.ClassReport;
import asynchJavaParser.common.reports.PackageReport;
import asynchJavaParser.common.reports.ProjectReport;
import asynchJavaParser.common.reports.interfaces.IClassReport;
import asynchJavaParser.common.reports.interfaces.IPackageReport;
import asynchJavaParser.common.reports.interfaces.IProjectReport;
import asynchJavaParser.common.utils.FileExplorer;
import asynchJavaParser.common.visitors.ClassVisitor;
import asynchJavaParser.common.visitors.PackageVisitor;
import asynchJavaParser.common.visitors.ProjectVisitor;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.reactivex.rxjava3.core.Observable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class ReactiveProjectAnalyzer implements IProjectAnalyzer{

    @Override
    public Observable<IClassReport> getClassReport(String srcClassPath) {
        return Observable.create(emitter-> {
            new Thread(() -> {
                CompilationUnit cu;
                try {
                    log("Class reporter started...");
                    cu = StaticJavaParser.parse(new File(srcClassPath));
                    ClassReport classReport = new ClassReport();
                    ClassVisitor visitor = new ClassVisitor();
                    visitor.visit(cu, classReport);
                    emitter.onNext(classReport);
                    emitter.onComplete();
                } catch (FileNotFoundException e) {
                    log("Class reporter failed...");
                    emitter.onError(e);
                }
            }).start();
        });
    }

    @Override
    public Observable<IPackageReport> getPackageReport(String srcPackagePath) {
        return Observable.create(emitter-> {
            new Thread(() -> {
                CompilationUnit cu;
                FileExplorer fileExplorer = new FileExplorer(srcPackagePath);
                List<String> files = fileExplorer.getAllPackageFiles();
                IPackageReport packageReport = new PackageReport();
                for (String nameFile : files) {
                    // System.out.println("nome file: " + nameFile); // for debug purposes
                    try {
                        log("Package reporter started...");
                        cu = StaticJavaParser.parse(new File(nameFile));
                        PackageVisitor visitor = new PackageVisitor();
                        visitor.visit(cu, packageReport);
                    } catch (FileNotFoundException e) {
                        log("Package reporter failed...");
                        emitter.onError(e);
                    }
                }
                emitter.onNext(packageReport);
                emitter.onComplete();
            }).start();
        });
    }

    @Override
    public Observable<IProjectReport> getProjectReport(String srcProjectFolderPath) {
        return Observable.create(emitter-> {
            new Thread(() -> {
                CompilationUnit cu;
                FileExplorer fileExplorer = new FileExplorer(srcProjectFolderPath);
                List<String> packages = fileExplorer.getAllPackageFiles();
                ProjectReport projectReport = new ProjectReport();
                // System.out.println("ALL PACKAGES: " + packages); // for debug purposes
                for (String p : packages) {
                    // System.out.println("package and its content: " + p); // for debug purposes
                    try {
                        log("Project reporter started...");
                        cu = StaticJavaParser.parse(new File(p));
                        ProjectVisitor visitor = new ProjectVisitor();
                        visitor.visit(cu, projectReport);
                    } catch (FileNotFoundException e) {
                        log("Project reporter failed...");
                        emitter.onError(e);
                    }
                }
                emitter.onNext(projectReport);
                emitter.onComplete();
            }).start();
        });
    }

    @Override
    public Observable<?> analyzeProject(String srcProjectFolderPath) {
        return null;
    }

    private static void log(String msg) {
        System.out.println("" + Thread.currentThread() + " " + msg);
    }
}
