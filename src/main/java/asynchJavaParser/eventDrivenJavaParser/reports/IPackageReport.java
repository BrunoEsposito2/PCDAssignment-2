package asynchJavaParser.EventDrivenJavaParser.Reports;

import java.util.List;

public interface IPackageReport {

	String getFullClassName();

	String getSrcFullFileName();

	List<IMethodInfo> getMethodsInfo();

	List<IFieldInfo> getFieldsInfo();

}
