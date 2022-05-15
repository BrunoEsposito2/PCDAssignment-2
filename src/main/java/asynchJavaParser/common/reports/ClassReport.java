package asynchJavaParser.common.reports;

import asynchJavaParser.common.reports.interfaces.IClassReport;
import asynchJavaParser.common.reports.interfaces.IFieldInfo;
import asynchJavaParser.common.reports.interfaces.IMethodInfo;

import java.util.ArrayList;
import java.util.List;

public class ClassReport implements IClassReport {
    private String fullClassName;
    private String srcFullFileName;
    private List<IMethodInfo> methodsInfo;
    private List<IFieldInfo> fieldsInfo;

    public ClassReport() {
        this.methodsInfo = new ArrayList<>();
        this.fieldsInfo = new ArrayList<>();
    }

    @Override
    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }

    @Override
    public void setSrcFullFileName(String srcFullFileName) {
        this.srcFullFileName = srcFullFileName;
    }

    @Override
    public void addMethodsInfo(IMethodInfo methodsInfo) {
        this.methodsInfo.add(methodsInfo);
    }

    @Override
    public void addFieldsInfo(IFieldInfo fieldsInfo) {
        this.fieldsInfo.add(fieldsInfo);
    }

    @Override
    public String getFullClassName() {
        return this.fullClassName;
    }

    @Override
    public String getSrcFullFileName() {
        return this.srcFullFileName;
    }

    @Override
    public List<IMethodInfo> getMethodsInfo() {
        return this.methodsInfo;
    }

    @Override
    public List<IFieldInfo> getFieldsInfo() {
        return this.fieldsInfo;
    }

    @Override
    public String toString() {
        return "ClassReport{" +
                "fullClassName='" + fullClassName + '\'' +
                ", srcFullFileName='" + srcFullFileName + '\'' +
                ", methodsInfo=" + methodsInfo +
                ", fieldsInfo=" + fieldsInfo +
                '}';
    }
}
