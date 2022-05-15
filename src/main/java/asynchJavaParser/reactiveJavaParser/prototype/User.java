package asynchJavaParser.reactiveJavaParser.prototype;

import com.github.javaparser.ast.PackageDeclaration;

public class User {
    public static void main(String[] args){
        FakeLibrary fl = new FakeLibrary();
        /*fl.get().subscribe(s->System.out.println(s));
        fl.getWithThreadAndParameter("aaa").subscribe(s->System.out.println(s));
        fl.getClassReport("classeeee").subscribe(c->System.out.println(c.getFullClassName()));*/
        fl.analyzeProject().subscribe(s->{
            if(s instanceof PackageDeclaration){
                PackageDeclaration p = (PackageDeclaration) s;
                System.out.println(p);
            }
        });

    }
}
