package asynchJavaParser.common.reports;

import asynchJavaParser.common.reports.interfaces.IInterfaceReport;
import asynchJavaParser.common.reports.interfaces.IMethodInfo;

import java.util.ArrayList;
import java.util.List;

public class InterfaceReport implements IInterfaceReport {
    private String fullInterfaceName;
    private String srcFullFileName;
    private List<IMethodInfo> methodsInfo;

    public InterfaceReport() {
        this.methodsInfo = new ArrayList<>();
    }

    @Override
    public void setFullInterfaceName(String fullInterfaceName) {
        this.fullInterfaceName = fullInterfaceName;
    }

    @Override
    public void setSrcFullFileName(String srcFullFileName) {
        this.srcFullFileName = srcFullFileName;
    }

    @Override
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

    @Override
    public String toString() {
        return "InterfaceReport{" +
                "fullInterfaceName='" + fullInterfaceName + '\'' +
                ", srcFullFileName='" + srcFullFileName + '\'' +
                ", methodsInfo=" + methodsInfo +
                '}';
    }
}
