package asynchJavaParser.eventDrivenJavaParser.lib.visitors;

import asynchJavaParser.eventDrivenJavaParser.lib.reports.ClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.InterfaceReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.ProjectReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IPackageReport;
import asynchJavaParser.eventDrivenJavaParser.lib.reports.interfaces.IProjectReport;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectVisitor extends VoidVisitorAdapter<IProjectReport> implements Visitor<IProjectReport> {
    private IProjectReport projectReport;
    private IPackageReport packageReport;
    private final PackageVisitor packageVisitor;
    private List<String> mainClassList;

    public ProjectVisitor(ProjectReport projectReport, IPackageReport packageReport, ClassReport cr, InterfaceReport ir) {
        this.projectReport = projectReport;
        this.packageReport = packageReport;
        this.packageVisitor = new PackageVisitor(this.packageReport, cr, ir);
    }

    public IProjectReport getProjectReport() {
        return this.projectReport;
    }

    @Override
    public void visit(PackageDeclaration pd, IProjectReport collector) {
        this.packageVisitor.visit(pd, this.packageReport);
        this.projectReport.addPackageReport(this.packageReport);
        if (this.mainClassExists()) {
            this.projectReport.setMainClass(this.mainClassList.get(0));
        } else {
            this.projectReport.setMainClass("Main class does not exists");
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, IProjectReport collector) {
        this.packageVisitor.visit(cd, this.packageReport);
        this.projectReport.addPackageReport(this.packageReport);
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
