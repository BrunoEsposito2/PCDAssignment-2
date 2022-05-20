package asynchJavaParser.reactiveJavaParser.client;

import asynchJavaParser.reactiveJavaParser.ProjectStructure;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnalyzeProject implements ActionListener {

    private final VisualizerFrame view;

    private ProjectStructure ps;

    public AnalyzeProject(final VisualizerFrame frame) {
        this.view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.ps = new ProjectStructure();

        this.view.resetTree();
        this.view.getMethodButtons().get("analyzeProject").setEnabled(false);

        Observable<ProjectStructure> ob = this.view.getLib().analyzeProject(this.view.getNameDirectory().getText());
        Disposable disposable = ob.subscribe(p -> {
            ps.addTo(p);
        });

    }
}
