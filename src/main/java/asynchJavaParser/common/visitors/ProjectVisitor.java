package asynchJavaParser.common.visitors;

import asynchJavaParser.common.reports.PackageReport;
import asynchJavaParser.common.reports.interfaces.IClassReport;
import asynchJavaParser.common.reports.interfaces.IPackageReport;
import asynchJavaParser.common.reports.interfaces.IProjectReport;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectVisitor extends VoidVisitorAdapter<IProjectReport> implements Visitor<IProjectReport> {
    private IPackageReport packageReport;
    private final PackageVisitor packageVisitor;
    private List<String> mainClassList;

    public ProjectVisitor() {
        this.packageReport = new PackageReport();
        this.packageVisitor = new PackageVisitor();
    }

    @Override
    public void visit(PackageDeclaration pd, IProjectReport projectReport) {
        this.packageVisitor.visit(pd, this.packageReport);
        projectReport.addPackageReport(this.packageReport);
        if (this.mainClassExists(projectReport)) {
            projectReport.setMainClass(this.mainClassList.get(0));
        } else {
            projectReport.setMainClass("Main class does not exists");
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, IProjectReport projectReport) {
        this.packageVisitor.visit(cd, this.packageReport);
    }

    private boolean mainClassExists(IProjectReport projectReport) {
        this.mainClassList = projectReport.getPackageReports().stream()
                .map(IPackageReport::getClassReports)
                .flatMap(Collection::stream)
                .filter(cr -> mainMethodExists(cr))
                .map(IClassReport::getFullClassName)
                .collect(Collectors.toList());

        return this.mainClassList.size() > 0;
    }

    private boolean mainMethodExists(IClassReport classReport) {
        return classReport.getMethodsInfo().stream().anyMatch(method -> method.getName().equals("main"));
    }
}
