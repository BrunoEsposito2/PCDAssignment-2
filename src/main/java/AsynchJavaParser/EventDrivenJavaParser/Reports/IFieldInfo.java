package AsynchJavaParser.EventDrivenJavaParser.Reports;

public interface IFieldInfo {

	String getName();
	String getFieldTypeFullName();

	IClassReport getParent();
}
