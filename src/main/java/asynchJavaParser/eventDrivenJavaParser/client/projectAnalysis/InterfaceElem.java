package asynchJavaParser.eventDrivenJavaParser.client.projectAnalysis;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InterfaceElem {
    private final Map<String, MethodDeclaration> methods;
    private Optional<ClassOrInterfaceDeclaration> elem;

    public InterfaceElem() {
        this.methods = new HashMap<>();
        this.elem = Optional.empty();
    }

    public void setElem(ClassOrInterfaceDeclaration elem) {
        this.elem = Optional.of(elem);
    }

    public Optional<ClassOrInterfaceDeclaration> getElem() {
        return elem;
    }

    public void addMethod(String name, MethodDeclaration m){
        this.methods.put(name, m);
    }

    public Map<String, MethodDeclaration> getMethods() {
        return this.methods;
    }
}
