package AsynchJavaParser.EventDrivenJavaParser.Reports;

import java.util.List;

public interface IInterfaceReport {

    String getFullInterfaceName();

    String getSrcFullFileName();

    List<IMethodInfo> getMethodsInfo();

    void addMethodsInfo(IMethodInfo methodInfo);

}
