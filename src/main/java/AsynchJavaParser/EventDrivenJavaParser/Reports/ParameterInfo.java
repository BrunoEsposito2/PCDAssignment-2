package AsynchJavaParser.EventDrivenJavaParser.Reports;

import com.github.javaparser.ast.type.Type;

public class ParameterInfo implements IParameterInfo {
    private String name;
    private Type parType;

    public ParameterInfo(String name, Type parType) {
        this.name = name;
        this.parType = parType;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Type getType() {
        return this.parType;
    }

    @Override
    public String toString() {
        return "ParameterInfo{" +
                "name='" + name + '\'' +
                ", parType=" + parType +
                '}';
    }
}
