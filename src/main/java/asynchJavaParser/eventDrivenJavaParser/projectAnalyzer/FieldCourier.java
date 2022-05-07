package asynchJavaParser.eventDrivenJavaParser.projectAnalyzer;

import com.github.javaparser.ast.body.FieldDeclaration;
import io.vertx.core.AbstractVerticle;

import java.util.function.BiConsumer;

public class FieldCourier extends AbstractVerticle {
    private final BiConsumer<FieldDeclaration, Void> edp;
    private final FieldDeclaration cd;

    public FieldCourier(FieldDeclaration cd, BiConsumer<FieldDeclaration, Void> edp){
        this.edp = edp;
        this.cd= cd;
    }

    @Override
    public void start(){
        edp.accept(cd, null);
        vertx.eventBus().send("master", "s");
    }
}
