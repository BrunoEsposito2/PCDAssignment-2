package asynchJavaParser.eventDrivenJavaParser.projectAnalyzer;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import io.vertx.core.AbstractVerticle;

import java.util.function.BiConsumer;

public class ClassCourier extends AbstractVerticle {
    private final BiConsumer<ClassOrInterfaceDeclaration, Void> edp;
    private final ClassOrInterfaceDeclaration cd;

    public ClassCourier(ClassOrInterfaceDeclaration cd, BiConsumer<ClassOrInterfaceDeclaration, Void> edp){
        this.edp = edp;
        this.cd= cd;
    }

    @Override
    public void start(){
        edp.accept(cd, null);
        vertx.eventBus().send("master", cd.getNameAsString());
    }
}
