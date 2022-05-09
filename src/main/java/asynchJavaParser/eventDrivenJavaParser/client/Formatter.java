package asynchJavaParser.eventDrivenJavaParser.client;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Formatter {

    public String formatPath(String path){
        List<String> splitted = Stream.of(path.split("src")).map(elem -> new String(elem)).collect(Collectors.toList());
        String newPath;
        newPath = "src" + splitted.get(1);
        newPath = newPath.replace("\\","/");
        return newPath;
    }
}
