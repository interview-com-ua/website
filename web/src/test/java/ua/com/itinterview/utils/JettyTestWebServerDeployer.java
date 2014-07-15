package ua.com.itinterview.utils;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyTestWebServerDeployer
{

    private static final int DEFAULT_PORT = 8080;

    private static final String DEFAULT_CONTEXT_PATH = "/interview-it";

    private Server server;
    private int port;
    private String contextPath;

    public JettyTestWebServerDeployer()
    {
        this(DEFAULT_PORT, DEFAULT_CONTEXT_PATH);
    }

    public JettyTestWebServerDeployer(int port)
    {
        this(port, DEFAULT_CONTEXT_PATH);
    }

    public JettyTestWebServerDeployer(int port, String contextPath)
    {
        this.port = port;
        this.contextPath = contextPath;
    }

    public static void main(String[] args)
    {
        int port;
        if (args.length > 0)
        {
            port = Integer.parseInt(args[0]);
        }
        else
        {
            port = DEFAULT_PORT;
        }

        JettyTestWebServerDeployer jettyTestServer = new JettyTestWebServerDeployer(port);
        jettyTestServer.start();
    }

    public void start()
    {
        server = new Server(port);
        WebAppContext context = new WebAppContext();
        context.setDescriptor("src/main/webapp/WEB-INF/web.xml");
        context.setResourceBase("src/main/webapp");
        context.setContextPath(contextPath);
        context.setParentLoaderPriority(true);

        server.setHandler(context);

        try
        {
            server.start();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public void stop() throws Exception
    {
        server.stop();
    }

    public Server getServer()
    {
        return server;
    }

    public void setServer(Server server)
    {
        this.server = server;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

}
