package asynchJavaParser.eventDrivenJavaParser.reports;

import java.util.List;

public interface IInterfaceReport {

    String getFullInterfaceName();

    String getSrcFullFileName();

    List<IMethodInfo> getMethodsInfo();

    void addMethodsInfo(IMethodInfo methodInfo);

}
