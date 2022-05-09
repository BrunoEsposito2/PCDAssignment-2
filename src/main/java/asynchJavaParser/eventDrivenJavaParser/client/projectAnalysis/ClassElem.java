package asynchJavaParser.eventDrivenJavaParser.client.projectAnalysis;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ClassElem {
    private final Map<String, FieldDeclaration> fields;
    private final Map<String, MethodDeclaration> methods;
    private Optional<ClassOrInterfaceDeclaration> elem;

    public ClassElem() {
        this.fields = new HashMap<>();
        this.methods = new HashMap<>();
        this.elem = Optional.empty();
    }

    public void setElem(ClassOrInterfaceDeclaration elem) {
        this.elem = Optional.of(elem);
    }

    public Optional<ClassOrInterfaceDeclaration> getElem() {
        return elem;
    }

    public void addField(String name, FieldDeclaration f){
        this.fields.put(name, f);
    }

    public void addMethod(String name, MethodDeclaration m){
        this.methods.put(name, m);
    }
}
