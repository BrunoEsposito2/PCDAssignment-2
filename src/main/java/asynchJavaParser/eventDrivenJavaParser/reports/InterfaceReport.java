package asynchJavaParser.EventDrivenJavaParser.Reports;

import java.util.List;

public class InterfaceReport implements IInterfaceReport {

    private String fullInterfaceName;
    private String srcFullFileName;
    private List<IMethodInfo> methodsInfo;

    public InterfaceReport() {
    }

    public void setFullInterfaceName(String fullInterfaceName) {
        this.fullInterfaceName = fullInterfaceName;
    }

    public void setSrcFullFileName(String srcFullFileName) {
        this.srcFullFileName = srcFullFileName;
    }

    public void setMethodsInfo(List<IMethodInfo> methodsInfo) {
        this.methodsInfo = methodsInfo;
    }

    @Override
    public String getFullInterfaceName() {
        return this.fullInterfaceName;
    }

    @Override
    public String getSrcFullFileName() {
        return this.srcFullFileName;
    }

    @Override
    public List<IMethodInfo> getMethodsInfo() {
        return this.methodsInfo;
    }

    @Override
    public void addMethodsInfo(IMethodInfo methodInfo) {
        this.methodsInfo.add(methodInfo);
    }
}
