package server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class SearchServer {


    private Server server;

    public synchronized void start() throws Exception {
        server = new Server(8080);

        String rootPath = SearchServer.class.getClassLoader().getResource(".").toString();
        WebAppContext webapp = new WebAppContext(rootPath + "../../src/main/webapp", "");
        server.setHandler(webapp);

        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }


    public void startAnother() throws Exception {
        server = new Server(8080);

        String rootPath = SearchServer.class.getClassLoader().getResource(".").toString();
        WebAppContext webapp = new WebAppContext(rootPath + "../../src/main/webappsdfsdff", "");
        server.setHandler(webapp);

        server.start();
    }
}
