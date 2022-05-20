package asynchJavaParser.reactiveJavaParser;

public class ProjectStructure {
    private int packagesNr;
    private int interfacesNr;
    private int classNr;
    private int methodsNr;
    private int fieldsNr;

    public ProjectStructure() {
        this.packagesNr = 0;
        this.classNr = 0;
        this.methodsNr = 0;
        this.fieldsNr = 0;
    }

    public void incrementPackages(){
        this.packagesNr++;
    }

    public void incrementInterfaces(){
        this.interfacesNr++;
    }

    public void incrementClasses(){
        this.classNr++;
    }

    public void incrementMethods(){
        this.methodsNr++;
    }

    public void incrementFields(){
        this.fieldsNr++;
    }

    public int getPackagesNr() {
        return packagesNr;
    }

    public int getInterfacesNr() {
        return interfacesNr;
    }

    public int getClassNr() {
        return classNr;
    }

    public int getMethodsNr() {
        return methodsNr;
    }

    public int getFieldsNr() {
        return fieldsNr;
    }

    public void addTo(ProjectStructure p){
        this.packagesNr += p.getPackagesNr();
        this.interfacesNr += p.getInterfacesNr();
        this.classNr += p.getClassNr();
        this.methodsNr += p.getMethodsNr();
        this.fieldsNr += p.getFieldsNr();
    }

    @Override
    public String toString() {
        return "ProjectStructure{" +
                "packagesNr=" + packagesNr +
                ", interfacesNr=" + interfacesNr +
                ", classNr=" + classNr +
                ", methodsNr=" + methodsNr +
                ", fieldsNr=" + fieldsNr +
                '}';
    }
}
