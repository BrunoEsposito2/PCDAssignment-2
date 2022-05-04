package asynchJavaParser.EventDrivenJavaParser.Reports;

import java.util.List;

public interface IClassReport {

	String getFullClassName();

	String getSrcFullFileName();

	List<IMethodInfo> getMethodsInfo();

	List<IFieldInfo> getFieldsInfo();

	void setFullClassName(String fullClassName);

	void setSrcFullFileName(String srcFullFileName);

	void addMethodsInfo(IMethodInfo methodsInfo);

	void addFieldsInfo(IFieldInfo fieldsInfo);

}
