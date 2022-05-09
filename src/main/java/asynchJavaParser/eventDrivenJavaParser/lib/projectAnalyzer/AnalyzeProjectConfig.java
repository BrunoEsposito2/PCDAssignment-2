package asynchJavaParser.eventDrivenJavaParser.lib.projectAnalyzer;

public class AnalyzeProjectConfig {
    private final String responseAddress;
    private final String completitionNotifAddress;
    private final String srcProjectFolderName;

    public AnalyzeProjectConfig(String responseAddress, String completitionNotifAddress, String srcProjectFolderName) {
        this.responseAddress = responseAddress;
        this.completitionNotifAddress = completitionNotifAddress;
        this.srcProjectFolderName = srcProjectFolderName;
    }

    public String getResponseAddress() {
        return responseAddress;
    }

    public String getCompletitionNotifAddress() {
        return completitionNotifAddress;
    }

    public String getSrcProjectFolderName() {
        return srcProjectFolderName;
    }
}
