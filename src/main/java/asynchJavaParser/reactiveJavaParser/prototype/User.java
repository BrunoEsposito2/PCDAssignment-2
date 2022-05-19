package asynchJavaParser.reactiveJavaParser.prototype;

import asynchJavaParser.reactiveJavaParser.ReactiveProjectAnalyzer;
import com.github.javaparser.ast.PackageDeclaration;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

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

    public static void main(String[] args){
        FakeLibrary fl = new FakeLibrary();
        /*fl.get().subscribe(s->System.out.println(s));
        fl.getWithThreadAndParameter("aaa").subscribe(s->System.out.println(s));
        fl.getClassReport("classeeee").subscribe(c->System.out.println(c.getFullClassName()));*/
        Map<String, Integer> result = new HashMap<>();
        result.put("count", 0);
/*
        Observable<?> ob=  fl.analyzeProject().map(x -> {
            switch (x.getClass().getSimpleName()) {
                case "ClassOrInterfaceDeclaration":
                    return classNum.get()+1;
                case "MethodDeclaration":
                    return methodNum.get()+1;
                case "PackageDeclaration":
                    return packageNum.get()+1;
                default:
            }
            return null;
        });*/
        ReactiveProjectAnalyzer rpv = new ReactiveProjectAnalyzer();
        String path = "src/main/java";
        Observable<?> observable = rpv.analyzeProject(path, result);
        Disposable disposable = observable.subscribe(entry -> print());
        disposable;
        Observer<?> observer = disposable
        Disposable disposable2 = observable.subscribe(s -> System.out.println(s.toString()));
        System.out.println(result.get("count"));



        /*ob.subscribe(x ->System.out.println(x));

        Map<String, Long> results = new HashMap<>();
        Map<String, List<Void>> observableMap = (Observable) fl.analyzeProject().collect(Collectors.groupingBy(el -> el.getClass().getName()));
        observableMap.forEach((x, y) -> results.put(x, Stream.of(y).count()));




       /*                                     //.collect(Collectors.groupingBy(elem -> elem.getClass().getSimpleName()))
        fl.analyzeProject().subscribe(s->{
            if(s instanceof PackageDeclaration){
                PackageDeclaration p = (PackageDeclaration) s;
                System.out.println(p);
            }
        });*/
    }
public static int count = 0;
    public static void print(){
        count++;
        System.out.println(count);
    }
}
