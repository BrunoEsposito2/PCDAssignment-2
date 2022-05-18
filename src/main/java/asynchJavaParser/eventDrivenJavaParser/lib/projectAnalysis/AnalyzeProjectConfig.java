package asynchJavaParser.eventDrivenJavaParser.lib.projectAnalysis;

public class AnalyzeProjectConfig {
    private final String responseAddress;
    private final String statusNotifAddress;
    private final String srcProjectFolderName;

    public AnalyzeProjectConfig(String responseAddress, String statusNotifAddress, String srcProjectFolderName) {
        this.responseAddress = responseAddress;
        this.statusNotifAddress = statusNotifAddress;
        this.srcProjectFolderName = srcProjectFolderName;
    }

    public String getResponseAddress() {
        return responseAddress;
    }

    public String getStatusNotifAddress() {
        return statusNotifAddress;
    }

    public String getSrcProjectFolderName() {
        return srcProjectFolderName;
    }
}
