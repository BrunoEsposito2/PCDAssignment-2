package asynchJavaParser.eventDrivenJavaParser.visitors;

import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IClassReport;
import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IInterfaceReport;
import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IPackageReport;
import asynchJavaParser.eventDrivenJavaParser.reports.interfaces.IProjectReport;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectVisitor extends VoidVisitorAdapter<Void> implements Visitor {
    private final IProjectReport projectReport;
    private final PackageVisitor packageVisitor;
    private List<String> mainClassList;

    public ProjectVisitor(IProjectReport projectReport, IPackageReport packageReport, IClassReport cr, IInterfaceReport ir) {
        this.projectReport = projectReport;
        this.packageVisitor = new PackageVisitor(packageReport, cr, ir);
    }

    @Override
    public void visit(PackageDeclaration pd, Void collector) {
        this.packageVisitor.visit(pd, collector);
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
