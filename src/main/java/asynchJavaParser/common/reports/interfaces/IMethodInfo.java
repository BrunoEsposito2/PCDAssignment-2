package asynchJavaParser.common.reports.interfaces;

import com.github.javaparser.Position;
import com.github.javaparser.ast.type.Type;

import java.util.List;

public interface IMethodInfo {

	String getName();
	Position getSrcBeginLine();
	Position getEndBeginLine();
	IClassReport getParent();
	void setReturnType(Type returnType);
	Type getReturnType();
	void addParameter(IParameterInfo parameter);
	List<IParameterInfo> getParameters();

}
