package ua.com.itinterview.utils;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.FlatXmlWriter;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * The class is utility class to work with DbUnit from java code.
 * Look at the test for usage example.
 */
public class DbUnitUtil{

    private static final String DATA_SET_SRC_FOLDER = "src/test/resources/dataset";
    private static final String HSQLDB_DRIVER = "org.hsqldb.jdbcDriver";
    private static final String HSQLDB_CONN_STR = "jdbc:hsqldb:file:../db/it-interview;shutdown=true;sql.syntax_pgs=true";

   /* @Test
    public void testSaveDataSet()throws Exception{
          Class driverClass = Class.forName(HSQLDB_DRIVER);
        Connection jdbcConnection = null;
        try{
            jdbcConnection = DriverManager.getConnection(HSQLDB_CONN_STR);

            String fileName = "expected.xml";
            //create IDataSet and save it
            IDataSet fullDataSet = getDataSet(jdbcConnection);
            saveDataSet(fullDataSet, DATA_SET_SRC_FOLDER, fileName);

            //load IDataSet from file
            IDataSet expectedDataSet = loadDataSet(DATA_SET_SRC_FOLDER, fileName);
            System.out.println("expectedDataSet: "+Arrays.toString(expectedDataSet.getTableNames()));

            //create actual IDataSet
            IDataSet actualDataSet = new DatabaseConnection(jdbcConnection).createDataSet();
            System.out.println("actualDataSet: "+Arrays.toString(actualDataSet.getTableNames()));

            //assertion error: entries for empty tables are not written to dataset
            Assertion.assertEquals(expectedDataSet, actualDataSet);
        } finally {
            if (jdbcConnection != null) {
                jdbcConnection.close();
            }
        }
    }*/

    public static IDataSet getDataSet(final Connection connection, final String... tableNames) throws SQLException, DatabaseUnitException {
        IDatabaseConnection dbuConnection = null;
        try{
            dbuConnection = new DatabaseConnection(connection);
            IDataSet dataSet = null;
            if (tableNames != null && tableNames.length != 0){
                dataSet = dbuConnection.createDataSet(tableNames);
            } else {
                dataSet = dbuConnection.createDataSet();
            }
            return dataSet;
        } catch (DatabaseUnitException e) {
            StringBuilder message = new StringBuilder();
            message.append(e.getClass().getName() + " occurred on create IDataSet for ")
                    .append(Arrays.toString(tableNames))
                    .append(". Try to create a full IDataSet.");
            throw new DatabaseUnitException(message.toString(), e);
        }
    }

    public static IDataSet getDataSet(final Connection connection) throws SQLException, DatabaseUnitException {
        return getDataSet(connection, null);
    }

    public static IDataSet getDataSet(final DataSource dataSource) throws SQLException, DatabaseUnitException {
        return getDataSet(dataSource.getConnection(), null);
    }

    public static IDataSet getDataSet(final DataSource dataSource, final String... tableNames) throws SQLException, DatabaseUnitException {
        return getDataSet(dataSource.getConnection(), tableNames);
    }

    public static IDataSet loadDataSet(final File file) throws DatabaseUnitException{
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        try {
            return builder.build(new FileReader(file));
        } catch (DataSetException e) {
            StringBuilder message = new StringBuilder();
            message.append(e.getClass().getName() + " occurred on read IDataSet from file ")
                    .append(file.getAbsoluteFile().getName());
            throw new DatabaseUnitException(message.toString(), e);
        } catch (FileNotFoundException e) {
            StringBuilder message = new StringBuilder();
            message.append(e.getClass().getName() + " occurred on read IDataSet from file ")
                    .append(file.getAbsoluteFile().getName());
            throw new DatabaseUnitException(message.toString(), e);
        }
    }

    public static IDataSet loadDataSet(final String path, final String fileName) throws DatabaseUnitException{
        File file = new File(path, fileName);
        return loadDataSet(file);
    }

    public static void saveDataSet(final IDataSet dataSet, final File file) throws DatabaseUnitException {
        try {
            FlatXmlWriter writer = new FlatXmlWriter(new FileWriter(file), "UTF-8");
            writer.write(dataSet);
        } catch (IOException e) {
            StringBuilder message = new StringBuilder();
            message.append(e.getClass().getName() + " occurred on write IDataSet to file ")
                    .append(file.getAbsoluteFile().getName());
            throw new DatabaseUnitException(message.toString(), e);
        }
    }

    public static void saveDataSet(final IDataSet dataSet, final String path, final String fileName) throws DatabaseUnitException {
        File file = new File(path, fileName);
        saveDataSet(dataSet, file);
    }
}
