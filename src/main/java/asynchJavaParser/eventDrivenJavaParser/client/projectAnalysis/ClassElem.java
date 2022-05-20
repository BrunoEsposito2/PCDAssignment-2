package asynchJavaParser.eventDrivenJavaParser.client.projectAnalysis;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

import java.util.*;

public class ClassElem {
    private final List<NodeList<VariableDeclarator>> fields;
    private final Map<String, MethodDeclaration> methods;
    private Optional<ClassOrInterfaceDeclaration> elem;

    public ClassElem() {
        this.fields = new ArrayList<>();
        this.methods = new HashMap<>();
        this.elem = Optional.empty();
    }

    public void setElem(ClassOrInterfaceDeclaration elem){this.elem = Optional.of(elem);}

    public Optional<ClassOrInterfaceDeclaration> getElem(){return this.elem;}

    public void setFieldDeclaration(NodeList<VariableDeclarator> f) {
        this.fields.add(f);
    }

    public List<NodeList<VariableDeclarator>> getFields() {
        return this.fields;
    }

    public void addMethod(String name, MethodDeclaration m){this.methods.put(name, m);}

    public Map<String, MethodDeclaration> getMethods() {
        return this.methods;
    }
}
