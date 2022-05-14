package asynchJavaParser.reactiveJavaParser.prototype;

import com.github.javaparser.ast.PackageDeclaration;
import io.reactivex.rxjava3.core.Observer;

public class User {
    public static void main(String[] args){
        FakeLibrary fl = new FakeLibrary();
        /*fl.get().subscribe(s->System.out.println(s));
        fl.getWithThreadAndParameter("aaa").subscribe(s->System.out.println(s));
        fl.getClassReport("classeeee").subscribe(c->System.out.println(c.getFullClassName()));*/
        fl.getPackageDeclarations().subscribe(s->{
            if(s.getType().equals("PackageDeclaration")){
                PackageDeclaration p = (PackageDeclaration) s.getElem();
                System.out.println(p.getNameAsString());
            }
        });

    }
}
