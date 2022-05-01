package ass02.part2.lib;

import java.util.ArrayList;
import java.util.List;

public class Method {

    private String name;
    private String type;
    private final List<Parameter> parameters;

    public Method() {
        this.parameters = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addParameter(Parameter p) {
        this.parameters.add(p);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
