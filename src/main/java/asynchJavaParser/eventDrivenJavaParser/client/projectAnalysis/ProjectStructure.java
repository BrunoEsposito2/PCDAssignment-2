package asynchJavaParser.eventDrivenJavaParser.client.projectAnalysis;

import com.github.javaparser.ast.PackageDeclaration;

public class ProjectStructure {
    private PackageElem root;

    public ProjectStructure(){
        root = new PackageElem();
    }

    public void putPackage(PackageDeclaration p){
        String path = p.getNameAsString();
        String[] individualPackages = path.split("[.]");
    }
}
