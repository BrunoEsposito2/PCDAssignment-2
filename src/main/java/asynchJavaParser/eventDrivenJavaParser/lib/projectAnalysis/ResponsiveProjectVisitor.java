package asynchJavaParser.eventDrivenJavaParser.lib.projectAnalysis;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;

public class ResponsiveProjectVisitor extends VoidVisitorAdapter<Void>{
    private final Vertx vertx;
    private boolean shouldContinue;
    private final String responseAddress;
    private final String stopAddress;

    public ResponsiveProjectVisitor(Vertx v, String responseAddress, String stopAddress){
        this.responseAddress = responseAddress;
        this.stopAddress = stopAddress;
        this.shouldContinue = true;
        vertx = v;

        MessageConsumer<String> consumer = vertx.eventBus().consumer(stopAddress);
        consumer.handler(message -> {this.stop();});
        this.registerCodecs();
    }

    @Override
    public void visit(PackageDeclaration fd, Void collector) {
        VirtualCourier<PackageDeclaration> c = new VirtualCourier<PackageDeclaration>(fd, (a, b) -> super.visit(fd, null), responseAddress);
        if (this.shouldContinue) vertx.deployVerticle(c);
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, Void collector) {
        VirtualCourier<ClassOrInterfaceDeclaration> c = new VirtualCourier<ClassOrInterfaceDeclaration>(cd, (a, b) -> super.visit(cd, null), responseAddress);
        if (this.shouldContinue) vertx.deployVerticle(c);
    }

    @Override
    public void visit(MethodDeclaration md, Void collector) {
        VirtualCourier<MethodDeclaration> c = new VirtualCourier<MethodDeclaration>(md, (a, b) -> super.visit(md, null), responseAddress);
        if (this.shouldContinue) vertx.deployVerticle(c);
    }

    @Override
    public void visit(FieldDeclaration fd, Void collector) {
        VirtualCourier<FieldDeclaration> c = new VirtualCourier<FieldDeclaration>(fd, (a, b)->super.visit(fd, null), responseAddress);
        if(this.shouldContinue) vertx.deployVerticle(c);
    }

    public void stop(){
        this.shouldContinue = false;
    }

    private void registerCodecs(){
        try{
            vertx.eventBus().registerDefaultCodec(
                    ClassOrInterfaceDeclaration.class,
                    new GenericCodec<ClassOrInterfaceDeclaration>(ClassOrInterfaceDeclaration.class)
            );

            vertx.eventBus().registerDefaultCodec(
                    FieldDeclaration.class,
                    new GenericCodec<FieldDeclaration>(FieldDeclaration.class)
            );

            vertx.eventBus().registerDefaultCodec(
                    MethodDeclaration.class,
                    new GenericCodec<MethodDeclaration>(MethodDeclaration.class)
            );

            vertx.eventBus().registerDefaultCodec(
                    PackageDeclaration.class,
                    new GenericCodec<PackageDeclaration>(PackageDeclaration.class)
            );
        } catch (IllegalStateException e){

        }
    }
}
