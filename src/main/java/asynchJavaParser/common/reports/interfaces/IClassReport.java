package asynchJavaParser.common.reports.interfaces;

import java.util.List;

public interface IClassReport extends IReport {

	String getFullClassName();

	List<IMethodInfo> getMethodsInfo();

	List<IFieldInfo> getFieldsInfo();

	void setFullClassName(String fullClassName);

	void addMethodsInfo(IMethodInfo methodsInfo);

	void addFieldsInfo(IFieldInfo fieldsInfo);

}
