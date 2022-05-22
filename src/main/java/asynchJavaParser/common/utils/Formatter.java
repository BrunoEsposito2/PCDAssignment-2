package asynchJavaParser.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Formatter {

    public static String formatPath(final String path){
        List<String> splitted = Stream.of(path.split("src")).map(elem -> new String(elem)).collect(Collectors.toList());
        String newPath;
        newPath = "src" + splitted.get(1);
        newPath = newPath.replace("\\","/");
        return newPath;
    }

    public static List<String> extractPackages(List<String> files) {
        List<String> pkgs = new ArrayList<>();

        files.forEach(f -> {
            String packageName = "";
            List<String> fileName = List.of(f.split(Pattern.quote("\\")));
            for (int i = 0; i <= fileName.size() - 2; i++) {
                packageName += "\\"+ fileName.get(i);
            }
            pkgs.add(packageName);
        });

        return pkgs.stream().distinct().collect(Collectors.toList());
    }
}
