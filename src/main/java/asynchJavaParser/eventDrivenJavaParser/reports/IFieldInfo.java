package asynchJavaParser.eventDrivenJavaParser.reports;

public interface IFieldInfo {

	String getName();
	String getFieldTypeFullName();

	IClassReport getParent();
}
