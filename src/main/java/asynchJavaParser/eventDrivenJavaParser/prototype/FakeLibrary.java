package asynchJavaParser.eventDrivenJavaParser.prototype;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;

import java.util.function.Consumer;

public class FakeLibrary {
    private Vertx vertx;
    public FakeLibrary(Vertx v){
        vertx = v;
    }
    public void method(Consumer<String> callback){
        System.out.println("method started");
        vertx.deployVerticle(new AtomicVerticle(0));
        MessageConsumer<String> consumer = vertx.eventBus().consumer("master");
        consumer.handler(message -> {
           callback.accept(message.body());
        });
        System.out.println("method ended");
    }
}
