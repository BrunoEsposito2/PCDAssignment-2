package asynchJavaParser.eventDrivenJavaParser.projectAnalyzer;

public class ProjectElem<X> implements IProjectElem{
    private final String type;
    private final X elem;

    public ProjectElem(String t, X e){
        this.type = t;
        this.elem = e;
    }

    public String getType() {
        return this.type;
    }

    public X getElem(){
        return this.elem;
    }
}
