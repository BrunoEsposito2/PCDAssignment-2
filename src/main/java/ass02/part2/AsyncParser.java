package ass02.part2;

import ass02.part2.lib.InterfaceReport;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class AsyncParser {

    private final Vertx vertx;

    public AsyncParser(Vertx vertx) {
        this.vertx = vertx;
    }

    public Future<InterfaceReport> interfaceReport(String srcInterfacePath) {
        // TODO
        return null;
    }
}
