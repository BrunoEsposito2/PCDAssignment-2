package asynchJavaParser.eventDrivenJavaParser.projectAnalyzer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;

import java.util.function.BiConsumer;

public class Courier<X> extends AbstractVerticle {
    private final BiConsumer<X, Void> edp;
    private final X cd;

    public Courier(X cd, BiConsumer<X, Void> edp){
        this.edp = edp;
        this.cd= cd;
    }

    @Override
    public void start(){
        edp.accept(cd, null);
        //vertx.eventBus().send("master", "ciao");
        vertx.eventBus().send("master", new ProjectElem<X>(cd.getClass().getName(), cd));
    }
}
