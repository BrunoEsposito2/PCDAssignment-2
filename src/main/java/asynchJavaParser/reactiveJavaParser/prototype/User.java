package asynchJavaParser.reactiveJavaParser.prototype;

import asynchJavaParser.reactiveJavaParser.IProjectAnalyzer;
import asynchJavaParser.reactiveJavaParser.ProjectStructure;
import asynchJavaParser.reactiveJavaParser.ReactiveProjectAnalyzer;
import com.github.javaparser.ast.PackageDeclaration;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class User {

    static AtomicInteger classNum = new AtomicInteger();
    static AtomicInteger packageNum = new AtomicInteger();
    static AtomicInteger methodNum = new AtomicInteger();

    public static void main(String[] args) {
        IProjectAnalyzer projectAnalyzer = new ReactiveProjectAnalyzer();

        /*fl.get().subscribe(s->System.out.println(s));
        fl.getWithThreadAndParameter("aaa").subscribe(s->System.out.println(s));
        fl.getClassReport("classeeee").subscribe(c->System.out.println(c.getFullClassName()));*/
        ProjectStructure ps = new ProjectStructure();
        Observable<ProjectStructure> ob = projectAnalyzer.analyzeProject("src/main/java");
        Disposable disposable = ob.subscribe(p -> {
            ps.addTo(p);
        });
        /*try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disposable.dispose();*/
        System.out.println("ciao");


    }
}
