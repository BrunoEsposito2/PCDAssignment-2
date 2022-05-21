package asynchJavaParser.common.reports.interfaces;

import java.util.List;

public interface IInterfaceReport extends IReport {

    void setFullInterfaceName(String fullInterfaceName);

    void setMethodsInfo(List<IMethodInfo> methodsInfo);

    String getFullInterfaceName();

    List<IMethodInfo> getMethodsInfo();

    void addMethodsInfo(IMethodInfo methodInfo);

}
