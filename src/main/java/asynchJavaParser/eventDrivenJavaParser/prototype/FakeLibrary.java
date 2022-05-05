package asynchJavaParser.eventDrivenJavaParser.prototype;

import io.vertx.core.Vertx;

public class FakeLibrary {
    private Vertx vertx;
    public FakeLibrary(Vertx v){
        vertx = v;
    }
    public void method(){
        System.out.println("method started");
        vertx.deployVerticle(new AtomicVerticle(0));
        System.out.println("method ended");
    }
}
