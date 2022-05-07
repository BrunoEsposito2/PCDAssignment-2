package asynchJavaParser.eventDrivenJavaParser.projectAnalyzer;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;

public class ResponsiveProjectVisitor extends VoidVisitorAdapter<Void>{
    private final Vertx vertx;
    private boolean shouldContinue;


    public ResponsiveProjectVisitor(Vertx v){
        this.shouldContinue = true;
        vertx = v;
        MessageConsumer<String> consumer = vertx.eventBus().consumer("projectVisitor");
        consumer.handler(message -> {
            if(message.body() == "STOP"){
                this.shouldContinue = false;
            }
        });
    }
    @Override
    public void visit(PackageDeclaration fd, Void collector) {
        Courier<PackageDeclaration> c = new Courier(fd, (a, b)->super.visit(fd, null));
        vertx.deployVerticle(c);
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, Void collector) {
        Courier<ClassOrInterfaceDeclaration> c = new Courier(cd, (a, b)->super.visit(cd, null));
        vertx.deployVerticle(c);
    }

    @Override
    public void visit(MethodDeclaration md, Void collector) {
        Courier<MethodDeclaration> c = new Courier(md, (a, b)->super.visit(md, null));
        vertx.deployVerticle(c);
    }
}
