package asynchJavaParser.eventDrivenJavaParser.lib.visitors;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.ProjectReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IPackageReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IProjectReport;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectVisitor extends VoidVisitorAdapter<IProjectReport> implements Visitor<IProjectReport> {
    private IProjectReport projectReport;
    private final PackageVisitor packageVisitor;
    private List<String> mainClassList;

    public ProjectVisitor() {
        this.projectReport = new ProjectReport();
        this.packageVisitor = new PackageVisitor();
    }

    public IProjectReport getProjectReport() {
        return this.projectReport;
    }

    @Override
    public void visit(PackageDeclaration pd, IProjectReport collector) {
        this.packageVisitor.visit(pd, this.packageVisitor.getPackageReport());
        this.projectReport = collector;
        this.projectReport.addPackageReport(this.packageVisitor.getPackageReport());
        if (this.mainClassExists()) {
            this.projectReport.setMainClass(this.mainClassList.get(0));
        } else {
            this.projectReport.setMainClass("Main class does not exists");
        }
    }

    private boolean mainClassExists() {
        this.mainClassList = this.projectReport.getPackageReports().stream()
                .map(IPackageReport::getClassReports)
                .flatMap(Collection::stream)
                .filter(cr -> mainMethodExists(cr))
                .map(IClassReport::getFullClassName)
                .collect(Collectors.toList());

        return this.mainClassList.size() > 0;
    }

    private boolean mainMethodExists(IClassReport cr) {
        return cr.getMethodsInfo().stream().anyMatch(method -> method.getName().equals("main"));
    }
}
