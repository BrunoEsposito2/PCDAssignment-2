package asynchJavaParser.eventDrivenJavaParser.reports;

import java.util.List;

public interface IFieldInfo {

	String getName();
	String getFieldTypeFullName();
	IClassReport getParent();

	void addField(String field);

	List<String> getFields();
}
