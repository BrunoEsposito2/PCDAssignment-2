package asynchJavaParser.eventDrivenJavaParser.lib.projectAnalyzer;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import java.util.function.BiConsumer;

public class Courier<X extends Node> extends AbstractVerticle {
    private final BiConsumer<X, Void> visitor;
    private final X elem;
    private final String responseAddress;

    public Courier(X elem, BiConsumer<X, Void> visitor, String responseAddress){
        this.visitor = visitor;
        this.elem = elem;
        this.responseAddress = responseAddress;
    }

    @Override
    public void start(){
        visitor.accept(elem, null);

        String elemType = elem.getClass().getName().replace("Declaration", "");
        if(elemType == "ClassOrInterface"){
            ClassOrInterfaceDeclaration cd = (ClassOrInterfaceDeclaration) elem;
            elemType = cd.isInterface() ? elemType.replace("ClassOr", "") : elemType.replace("OrInterface", "");
        }

        DeliveryOptions options = new DeliveryOptions();
        options.addHeader("type", elemType);
        vertx.eventBus().send(responseAddress, elem, options);
    }
}
