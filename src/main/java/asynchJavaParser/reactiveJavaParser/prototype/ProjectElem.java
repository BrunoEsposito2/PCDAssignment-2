package asynchJavaParser.reactiveJavaParser.prototype;

public class ProjectElem<X> implements  IProjectElem{
    private final X elem;
    private final String type;

    public ProjectElem(X elem) {
        this.elem = elem;
        this.type = this.elem.getClass().getSimpleName();
    }

    public X getElem(){
        return this.elem;
    }

    @Override
    public String getType(){
        return this.type;
    }
}
