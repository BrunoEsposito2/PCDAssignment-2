package asynchJavaParser.eventDrivenJavaParser.lib.projectAnalyzer;

import asynchJavaParser.eventDrivenJavaParser.lib.utils.FileExplorer;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class ProjectAnalyzerReporter extends AbstractVerticle {
    private final AnalyzeProjectConfig conf;
    private final FileExplorer fileExplorer;

    public ProjectAnalyzerReporter(AnalyzeProjectConfig conf){
        this.conf = conf;
        this.fileExplorer = new FileExplorer(this.conf.getSrcProjectFolderName());
    }

    @Override
    public void start() {
        CompilationUnit cu = null;
        List<String> files = this.fileExplorer.getAllPackageFiles();
        ResponsiveProjectVisitor visitor = new ResponsiveProjectVisitor(this.vertx, conf.getResponseAddress(), conf.getResponseAddress());
        for (String nameFile : files) {
            // System.out.println("nome file: " + nameFile); // for debug purposes
            try {
                log("Project analyzer reporter started...");
                cu = StaticJavaParser.parse(new File(nameFile));
                visitor.visit(cu, null);
            } catch (FileNotFoundException e) {
                log("Project analyzer reporter failed...");
                //TODO send failure message
            }
        }
    }
    private static void log(String msg) {
        System.out.println("" + Thread.currentThread() + " " + msg);
    }
}
