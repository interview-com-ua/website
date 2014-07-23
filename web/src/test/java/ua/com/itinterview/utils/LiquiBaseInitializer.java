package ua.com.itinterview.utils;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.FileSystemResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 * User: viktor
 * Date: 11/27/13
 * Time: 8:03 AM
 */
public class LiquiBaseInitializer
{
    private static final String PROJECT_ROOT_DIR_NAME = "interview-it";
    private static Logger logger = LoggerFactory.getLogger(LiquiBaseInitializer.class);

    private DataSource dataSource;
    private String dbSchema;

    private String parentRootDir = PROJECT_ROOT_DIR_NAME;
    private String rootDirName = "/web";
    private String changesPath = "/src/main/resources/sql/liquibase/sql-changes.xml";
    private String context = "";

    public void insertInitialDatabase() throws Exception
    {
        logger.debug("###Liqubase update for banq database will start in few seconds. Current runMode is "
                + System.getProperty("runMode"));
        Liquibase liquibase = null;
        Connection connection = dataSource.getConnection();
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        database.setDefaultSchemaName(dbSchema);

        File currentDir = new File("");
        String strCurrentDir = currentDir.getAbsolutePath().replace("\\", "/");
        logger.debug("###Current directory is " + strCurrentDir);
        File baseDir = new File(getProjectRootDir(parentRootDir), rootDirName);
        File sqlChanges = new File(baseDir, changesPath);
        liquibase = new Liquibase(sqlChanges.getAbsolutePath(), new FileSystemResourceAccessor(baseDir.getAbsolutePath()), database);
        liquibase.setChangeLogParameter("db.schema", dbSchema);
        liquibase.update(context);
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public void setDbSchema(String dbSchema)
    {
        this.dbSchema = dbSchema;
    }

    public void setContext(String context)
    {
        this.context = context;
    }

    public void setParentRootDir(String parentRootDir)
    {
        this.parentRootDir = parentRootDir;
    }

    public void setRootDirName(String rootDirName)
    {
        this.rootDirName = rootDirName;
    }

    public void setChangesPath(String changesPath)
    {
        this.changesPath = changesPath;
    }

    private static File getProjectRootDir(String parentName)
    {
        return getProjectRootDir(parentName, new File("."));
    }

    private static File getProjectRootDir(String parentName, File currentDir)
    {
        if (parentName.equals(currentDir.getName()))
        {
            return currentDir;
        }

        return getProjectRootDir(parentName, currentDir.getAbsoluteFile().getParentFile());
    }
}


