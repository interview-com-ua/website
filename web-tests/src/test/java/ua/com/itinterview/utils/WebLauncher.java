package ua.com.itinterview.utils;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import ua.com.itinterview.jbehave.AcceptanceTestSuite;

/**
 * Created with IntelliJ IDEA.
 * User: viktor
 * Date: 11/28/13
 * Time: 5:52 PM
 */
public class WebLauncher
{

    private Server server;
    private int port;
    private String contextPath;

    public WebLauncher() {
        port = AcceptanceTestSuite.PORT;
        contextPath = AcceptanceTestSuite.CONTEXT_PATH;
    }

    public WebLauncher(int port, String contextPath) {
        this.port = port;
        this.contextPath = contextPath;
    }


    public void start() {
        server = new Server(port);
        WebAppContext context = new WebAppContext();
        context.setDescriptor("src/main/webapp/WEB-INF/web.xml");
        context.setResourceBase("src/main/webapp");
        context.setContextPath(contextPath);
        context.setParentLoaderPriority(true);

        server.setHandler(context);

        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() throws Exception {
        server.stop();
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


}
