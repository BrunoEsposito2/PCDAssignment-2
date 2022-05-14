package asynchJavaParser.reactiveJavaParser;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import io.reactivex.rxjava3.core.ObservableEmitter;

import java.util.ArrayList;
import java.util.List;

public class EmitterVisitor extends VoidVisitorAdapter<Void> {
    private final ObservableEmitter<Object> emitter;
    private final List<Object> alreadySentElements;
    public EmitterVisitor(ObservableEmitter<Object> emitter){
        this.emitter = emitter;
        alreadySentElements = new ArrayList<>();
    }

    @Override
    public void visit(PackageDeclaration p, Void collector){
        if(!emitter.isDisposed()){
            super.visit(p, null);
            if(!alreadySentElements.contains(p)){
                alreadySentElements.add(p);
                emitter.onNext(p);
            }
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration c, Void collector){
        if(!emitter.isDisposed()){
            super.visit(c, null);
            if(!alreadySentElements.contains(c)){
                alreadySentElements.add(c);
                emitter.onNext(c);
            }
        }
    }

    @Override
    public void visit(MethodDeclaration m, Void collector){
        if(!emitter.isDisposed()){
            super.visit(m, null);
            if(!alreadySentElements.contains(m)){
                alreadySentElements.add(m);
                emitter.onNext(m);
            }
        }
    }

    @Override
    public void visit(FieldDeclaration f, Void collector){
        if(!emitter.isDisposed()){
            super.visit(f, null);
            if(!alreadySentElements.contains(f)){
                alreadySentElements.add(f);
                emitter.onNext(f);
            }
        }
    }
}
