package AsynchJavaParser.EventDrivenJavaParser.Reports;

public interface IMethodInfo {

	String getName();
	int getSrcBeginLine();
	int getEndBeginLine();
	IClassReport getParent();

}
