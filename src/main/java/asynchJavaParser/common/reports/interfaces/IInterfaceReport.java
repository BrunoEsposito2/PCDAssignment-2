package asynchJavaParser.common.reports.interfaces;

import java.util.List;

public interface IInterfaceReport {

    void setFullInterfaceName(String fullInterfaceName);

    void setSrcFullFileName(String srcFullFileName);

    void setMethodsInfo(List<IMethodInfo> methodsInfo);

    String getFullInterfaceName();

    String getSrcFullFileName();

    List<IMethodInfo> getMethodsInfo();

    void addMethodsInfo(IMethodInfo methodInfo);

}
