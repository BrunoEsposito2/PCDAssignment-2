package asynchJavaParser.eventDrivenJavaParser.prototype;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.MessageConsumer;

public class AtomicVerticle extends AbstractVerticle {
    Integer i;
    public AtomicVerticle(int j){
        i=j;
    }

    public void start(){
        MessageConsumer<String> consumer = vertx.eventBus().consumer("worker");
        consumer.handler(message -> {
            //callback.accept(message);
            System.out.println("I have received a message: " + message.body());
            i=99;
        });
        vertx.setTimer(2000, res -> {
            vertx.eventBus().send("master", i.toString());
            if(i<10){
                vertx.deployVerticle(new AtomicVerticle(i+1));
            }
        });
    }
}
