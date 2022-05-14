package asynchJavaParser.reactiveJavaParser.prototype;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.ClassReport;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;

import java.util.ArrayList;
import java.util.List;

public class FakeVisitor {
    public Observable<IProjectElem> visit_one(ClassReport par){
        ArrayList<String> packageNames = new ArrayList<>();
        packageNames.add("reporters");
        packageNames.add("utils");

        return null;
    }

}
