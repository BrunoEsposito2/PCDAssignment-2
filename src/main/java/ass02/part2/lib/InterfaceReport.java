package ass02.part2.lib;

import java.util.ArrayList;
import java.util.List;

public class InterfaceReport {

    private String interfaceName;
    private String interfacePackage;
    private final List<Method> methods;

    public InterfaceReport() {
        this.methods = new ArrayList<>();
    }

    public void setInterfaceName(String reportName) {
        this.interfaceName = reportName;
    }

    public void setInterfacePackage(String reportPackage) {
        this.interfacePackage = reportPackage;
    }

    public void addMethod(Method m) {
        this.methods.add(m);
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getInterfacePackage() {
        return interfacePackage;
    }

    public List<Method> getMethods() {
        return methods;
    }
}
