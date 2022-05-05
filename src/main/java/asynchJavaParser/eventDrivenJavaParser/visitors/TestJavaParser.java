package asynchJavaParser.eventDrivenJavaParser.visitors;

import java.io.File;
import java.util.ArrayList;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class TestJavaParser {

	public static void main(String[] args) throws Exception {
		CompilationUnit cu = StaticJavaParser.parse(new File("src/main/java/Ass_02/AsynchJavaParser.EventDrivenJavaParser/TestJavaParser.java"));

		var methodNames = new ArrayList<String>();
		var methodNameCollector = new MethodNameCollector();
		methodNameCollector.visit(cu,methodNames);
		methodNames.forEach(n -> System.out.println("MethodNameCollected:" + n));

		var fullc = new FullCollector();
		fullc.visit(cu, null);
	}
}
