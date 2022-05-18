package asynchJavaParser.reactiveJavaParser.prototype;

import asynchJavaParser.common.reports.ClassReport;
import asynchJavaParser.common.utils.FileExplorer;
import asynchJavaParser.reactiveJavaParser.EmitterVisitor;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class FakeLibrary {
    public Observable<String> get(){
        return Observable.just("ciao");
    }

    public Observable<String> getWithThreadAndParameter(String arg){
        return Observable.just(arg+" O").observeOn(Schedulers.computation());
    }

    public Observable<ClassReport> getClassReport(String path){
        //parse
        return Observable.create(emitter->{
            new Thread(()->{
                ClassReport c = new ClassReport();
                c.setFullClassName(path);
                emitter.onNext(c);
            }).start();
        });
    }

    public Observable<?> analyzeProject() {
        return Observable.create(emitter->{
            FileExplorer fileExplorer = new FileExplorer("src/main/java");
            List<String> files = fileExplorer.getAllSubpackageFiles();
            EmitterVisitor fv = new EmitterVisitor(emitter);

            for (String nameFile : files) {
                try {
                    CompilationUnit cu = StaticJavaParser.parse(new File(nameFile));
                    fv.visit(cu, null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            emitter.onComplete();
        }).observeOn(Schedulers.computation());
    }

}
