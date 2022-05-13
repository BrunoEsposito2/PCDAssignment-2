package asynchJavaParser.eventDrivenJavaParser.client.projectAnalysis;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.Map;
import java.util.NoSuchElementException;

public class ProjectStructure {
    private PackageElem root;

    public ProjectStructure(){
        root = new PackageElem();
    }

    public void putPackage(PackageDeclaration p){
        String path = p.getNameAsString();
        String[] individualPackages = path.split("[.]");

        Map<String, PackageElem> packages = root.getInnerPackages();
        PackageElem alreadySavedPackage;

        //for each package in the path
        for(int i=0; i<individualPackages.length; i++){
            //check in the package at the current nesting level is saved in the map
            alreadySavedPackage = packages.get(individualPackages[i]);

            if(alreadySavedPackage == null){
                //if it's not saved, save the name, and if we are in the final nesting level save the element
                PackageElem newPE = new PackageElem();
                if(i == individualPackages.length-1) newPE.setElem(p);
                packages.put(individualPackages[i], newPE);
                alreadySavedPackage = packages.get(individualPackages[i]);
            } else {
                //if the package is in the map but the element is not present (and we are in last nesting level)...
                if(alreadySavedPackage.getElem().isEmpty() && i == individualPackages.length-1){
                    alreadySavedPackage.setElem(p);  //...save the element
                }
            }
            //increment nesting level
            packages = alreadySavedPackage.getInnerPackages();
        }
    }

    public void putClassOrInterface(ClassOrInterfaceDeclaration c){
        String fullPath = c.getFullyQualifiedName().get();
        System.out.println("fp: "+fullPath);
        String[] path = fullPath.split("[.]");
        PackageElem p = reachPackage(path);

        if(c.isInterface()){
            InterfaceElem alreadySavedInterface = p.getInnerInterfaces().get(path[path.length - 1]);
            if (alreadySavedInterface == null){
                InterfaceElem newInterface = new InterfaceElem();
                p.getInnerInterfaces().put(path[path.length - 1], newInterface);
            }
            p.getInnerInterfaces().get(path[path.length - 1]).setElem(c);
        } else {
            ClassElem alreadySavedClass = p.getInnerClasses().get(path[path.length - 1]);
            if (alreadySavedClass == null){
                ClassElem newClass = new ClassElem();
                p.getInnerClasses().put(path[path.length - 1], newClass);
            }
            p.getInnerClasses().get(path[path.length - 1]).setElem(c);
        }
    }

    private PackageElem reachPackage(String[] path){
        if(path.length == 1) return this.root;

        Map<String, PackageElem> packages = root.getInnerPackages();
        PackageElem alreadySavedPackage;

        //for each package in the path
        for(int i=0; i<path.length-1; i++){
            //check in the package at the current nesting level is saved in the map
            alreadySavedPackage = packages.get(path[i]);

            if(alreadySavedPackage == null) {
                //if it's not saved, save the name
                PackageElem newPE = new PackageElem();
                packages.put(path[i], newPE);
                alreadySavedPackage = packages.get(path[i]);
            }
            if(i == path.length - 2) return packages.get(path[i]);
            //increment nesting level

            packages = alreadySavedPackage.getInnerPackages();
        }
        return null;
    }

    public void putMethod(MethodDeclaration m){
        try{
            ClassOrInterfaceDeclaration methodParent = (ClassOrInterfaceDeclaration) m.getParentNode().get();
            String fullPath = (String) methodParent.getFullyQualifiedName().get();
            String[] path = fullPath.split("[.]");
            PackageElem p = reachPackage(path);

            if(methodParent.isInterface()){
                String interfaceName = path[path.length - 1];
                InterfaceElem c = p.getInnerInterfaces().get(interfaceName);
                if(c == null){
                    InterfaceElem newInterface = new InterfaceElem();
                    p.addInnerInterface(interfaceName, newInterface);
                }
                p.getInnerInterfaces().get(interfaceName).addMethod(m.getNameAsString(), m);

            } else {
                String className = path[path.length - 1];
                ClassElem c = p.getInnerClasses().get(className);
                if(c == null){
                    ClassElem newClass = new ClassElem();
                    p.addInnerClass(className, newClass);
                }
                p.getInnerClasses().get(className).addMethod(m.getNameAsString(), m);
            }

        } catch (NullPointerException| NoSuchElementException e){
            e.printStackTrace();
        }
    }

    public void putField(FieldDeclaration f){
        try{
            ClassOrInterfaceDeclaration methodParent = (ClassOrInterfaceDeclaration) f.getParentNode().get();
            String fullPath = (String) methodParent.getFullyQualifiedName().get();
            String[] path = fullPath.split("[.]");
            PackageElem p = reachPackage(path);

            String className = path[path.length - 1];
            ClassElem c = p.getInnerClasses().get(className);
            if(c == null){
                ClassElem newClass = new ClassElem();
                p.addInnerClass(className, newClass);
            }
            p.getInnerClasses().get(className).setFieldDeclaration(f);

        } catch (NullPointerException| NoSuchElementException e){
            e.printStackTrace();
        }
    }

    public PackageElem getRoot(){return this.root;}
}
