package asynchJavaParser.eventDrivenJavaParser.lib.projectAnalysis;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import java.util.function.BiConsumer;

public class VirtualCourier<X extends Node> extends AbstractVerticle {
    private final BiConsumer<X, Void> visitor;
    private final X elem;
    private final String responseAddress;

    public VirtualCourier(X elem, BiConsumer<X, Void> visitor, String responseAddress){
        this.visitor = visitor;
        this.elem = elem;
        this.responseAddress = responseAddress;
    }

    @Override
    public void start(){

        visitor.accept(elem, null);

        String[] completeClassName = elem.getClass().getName().split("[.]");
        String elemType = completeClassName[completeClassName.length - 1].replace("Declaration", "");

        if(elemType.equals("ClassOrInterface")){
            ClassOrInterfaceDeclaration cd = (ClassOrInterfaceDeclaration) elem;
            elemType = cd.isInterface() ? elemType.replace("ClassOr", "") : elemType.replace("OrInterface", "");
        }

        DeliveryOptions options = new DeliveryOptions();
        options.addHeader("type", elemType);
        vertx.eventBus().send(responseAddress, elem, options);
    }
}
