package asynchJavaParser.eventDrivenJavaParser.lib.reporters;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IPackageReport;
import asynchJavaParser.eventDrivenJavaParser.lib.utils.FileExplorer;
import asynchJavaParser.eventDrivenJavaParser.lib.visitors.PackageVisitor;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class PackageReporter extends AbstractVerticle {
    private final Promise<IPackageReport> res;
    private final String path;
    private final FileExplorer fileExplorer;

    public PackageReporter(Promise<IPackageReport> res, String path){
        this.res = res;
        this.path = path;
        this.fileExplorer = new FileExplorer(this.path);
    }

    @Override
    public void start() {
        CompilationUnit cu;
        List<String> files = this.fileExplorer.getFileList(this.path);
        PackageVisitor visitor = new PackageVisitor();
        for (String nameFile : files) {
            // System.out.println("nome file: " + nameFile); // for debug purposes
            try {
                log("Package reporter started...");
                cu = StaticJavaParser.parse(new File(nameFile));
                visitor.visit(cu, visitor.getPackageReport());
            } catch (FileNotFoundException e) {
                log("Package reporter failed...");
                res.fail("invalid path");
            }
        }
        res.complete(visitor.getPackageReport());
    }
    private static void log(String msg) {
        System.out.println("" + Thread.currentThread() + " " + msg);
    }

//    private List<String> getFileList() {
//        File[] files = new File(this.path).listFiles(File::isFile);
//        return Arrays.stream(files).map(File::toString).collect(Collectors.toList());
//    }
}
