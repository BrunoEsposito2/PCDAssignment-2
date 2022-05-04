package asynchJavaParser.eventDrivenJavaParser.reports;

import java.util.List;

public interface IPackageReport {

	String getFullClassName();

	String getSrcFullFileName();

	List<IMethodInfo> getMethodsInfo();

	List<IFieldInfo> getFieldsInfo();

}
