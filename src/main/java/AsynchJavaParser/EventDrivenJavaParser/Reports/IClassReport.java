package AsynchJavaParser.EventDrivenJavaParser.Reports;

import java.util.List;

public interface IClassReport {

	String getFullClassName();

	String getSrcFullFileName();

	List<IMethodInfo> getMethodsInfo();

	List<IFieldInfo> getFieldsInfo();

}
