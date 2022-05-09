package asynchJavaParser.eventDrivenJavaParser.client.projectAnalysis;

import com.github.javaparser.ast.PackageDeclaration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PackageElem {
    private final Map<String, PackageElem> innerPackages;
    private final Map<String, ClassElem> innerClasses;
    private final Map<String, InterfaceElem> innerInterfaces;
    private Optional<PackageDeclaration> elem;

    public PackageElem(){
        this.elem = Optional.empty();
        innerPackages = new HashMap<>();
        innerClasses = new HashMap<>();
        innerInterfaces = new HashMap<>();
    }

    public void addInnerPackage(String name, PackageElem p){
        innerPackages.put(name, p);
    }

    public void addInnerClass(String name, ClassElem c){
        innerClasses.put(name, c);
    }

    public void addInnerInterface(String name, InterfaceElem i){
        innerInterfaces.put(name, i);
    }

    public Map<String, PackageElem> getInnerPackages() {
        return innerPackages;
    }

    public Map<String, ClassElem> getInnerClasses() {
        return innerClasses;
    }

    public Map<String, InterfaceElem> getInnerInterfaces() {
        return innerInterfaces;
    }

    public void setElem(PackageDeclaration p){
        this.elem = Optional.of(p);
    }

    public Optional<PackageDeclaration> getElem() {
        return elem;
    }
}
