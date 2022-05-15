package asynchJavaParser.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileExplorer {
    final String path;

    public FileExplorer(final String path) {
        this.path = path;
    }

    public List<String> getAllSubpackageFiles() {
        List<Path> fileList;
        try {
            fileList = Files.walk(Paths.get(this.path)).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileList.stream().map(Path::toString).filter(f -> f.contains(".java")).collect(Collectors.toList());
    }

    public List<String> getAllPackageFiles() {
        List<String> fileList = new ArrayList<>();
        getAllPackageList().stream().map(this::getFileList).forEach(fileList::addAll);
        return fileList;
    }

    public List<String> getAllPackageList() {
        List<String> subPackages = new ArrayList<>();
        List<String> allPackages = getPackageList(this.path);
        allPackages.stream().map(this::getPackageList).forEach(subPackages::addAll); // extracting all sub-packages
        allPackages.add(this.path); // add the most outer project package
        allPackages.addAll(subPackages); // add all sub-packages to the outer packages
        return allPackages.stream().distinct().collect(Collectors.toList());
    }

    private List<String> getPackageList(String p) {
        File[] packages = new File(p).listFiles(File::isDirectory);
        return Arrays.stream(packages).map(File::toString).collect(Collectors.toList()); // take all outer packages
    }

    private List<String> getFileList(String p) {
        File[] files = new File(p).listFiles(File::isFile);
        return Arrays.stream(files).map(File::toString).collect(Collectors.toList());
    }

}
