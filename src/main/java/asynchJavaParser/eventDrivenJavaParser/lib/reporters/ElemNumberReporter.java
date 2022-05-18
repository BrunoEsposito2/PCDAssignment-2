package asynchJavaParser.eventDrivenJavaParser.lib.reporters;

import asynchJavaParser.common.utils.FileExplorer;
import asynchJavaParser.common.visitors.ElemCounterCollector;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class ElemNumberReporter extends AbstractVerticle {
    private final Promise<Integer> res;
    private final String path;
    private final FileExplorer fileExplorer;

    public ElemNumberReporter(Promise<Integer> res, String path){
        this.res = res;
        this.path = path;
        this.fileExplorer = new FileExplorer(this.path);
    }

    @Override
    public void start() {
        CompilationUnit cu;
        List<String> files = this.fileExplorer.getAllSubpackageFiles();
        Integer result = 0;
        ElemCounterCollector visitor = new ElemCounterCollector(result);
        for (String nameFile : files) {
            // System.out.println("nome file: " + nameFile); // for debug purposes
            try {
                //log("Package reporter started...");
                cu = StaticJavaParser.parse(new File(nameFile));
                visitor.visit(cu, null);
                result = visitor.getCount();
            } catch (FileNotFoundException e) {
                log("Package reporter failed...");
                res.fail("invalid path");
            }
        }
        log(result.toString());
        res.complete(result);
    }
    private static void log(String msg) {
        System.out.println("" + Thread.currentThread() + " " + msg);
    }
}
