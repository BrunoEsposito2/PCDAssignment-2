package asynchJavaParser.reactiveJavaParser.prototype;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.ClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.utils.FileExplorer;
import asynchJavaParser.reactiveJavaParser.EmitterVisitor;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import io.reactivex.rxjava3.core.*;
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

    public Observable<ProjectElem<?>> getPackageDeclarations() {
        return Observable.create(emitter->{
            FileExplorer fileExplorer = new FileExplorer("src/main/java");
            List<String> files = fileExplorer.getAllPackageFiles();
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
        })
                .observeOn(Schedulers.computation())
                .map(o ->{
                    switch(o.getClass().getSimpleName()){
                        case "PackageDeclaration":
                            ProjectElem<PackageDeclaration> p = new ProjectElem<PackageDeclaration>((PackageDeclaration)o);
                            return p;
                        case "ClassOrInterfaceDeclaration":
                            ProjectElem<ClassOrInterfaceDeclaration> c = new ProjectElem<>((ClassOrInterfaceDeclaration) o);
                            return c;
                        case "MethodDeclaration":
                            ProjectElem<MethodDeclaration> m = new ProjectElem<>((MethodDeclaration) o);
                            return m;
                        case "FieldDeclaration":
                            ProjectElem<FieldDeclaration> f = new ProjectElem<>((FieldDeclaration) o);
                            return f;
                        default:
                            return null;
                    }
                });
    }

    public Observable<ClassOrInterfaceDeclaration> getClassOrInterfaceDeclarations(){
        return Observable.create(emitter->{
            FileExplorer fileExplorer = new FileExplorer("src/main/java/asynchJavaParser");
            List<String> files = fileExplorer.getAllPackageFiles();
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

        }).observeOn(Schedulers.computation()).cast(ClassOrInterfaceDeclaration.class);
    }

}
