package ua.com.itinterview.utils;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdWriter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.FlatXmlWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * The class is utility class to work with DbUnit from java code.
 * Look at the test for usage example.
 */
@Component
public class DbUnitUtil
{

    @Autowired
    private DataSource dataSource;

    private static final String DATASET_SRC_FOLDER = "src/test/resources/dataset";

    public IDataSet getDataSet(final Connection connection, final String... tableNames) throws SQLException, DatabaseUnitException
    {
        IDatabaseConnection dbuConnection = null;
        try
        {
            dbuConnection = new DatabaseConnection(connection);
            IDataSet dataSet = null;
            if (tableNames != null && tableNames.length != 0)
            {
                dataSet = dbuConnection.createDataSet(tableNames);
            }
            else
            {
                dataSet = dbuConnection.createDataSet();
            }
            return dataSet;
        }
        catch (DatabaseUnitException e)
        {
            StringBuilder message = new StringBuilder();
            message.append(e.getClass().getName() + " occurred on create IDataSet for ")
                    .append(Arrays.toString(tableNames))
                    .append(". Try to create a full IDataSet.");
            throw new DatabaseUnitException(message.toString(), e);
        }
    }

    public IDataSet getDataSet(final Connection connection) throws SQLException, DatabaseUnitException
    {
        return getDataSet(connection, null);
    }

    public IDataSet getDataSet(final DataSource dataSource) throws SQLException, DatabaseUnitException
    {
        return getDataSet(dataSource.getConnection(), null);
    }

    public IDataSet getDataSet(final DataSource dataSource, final String... tableNames) throws SQLException, DatabaseUnitException
    {
        return getDataSet(dataSource.getConnection(), tableNames);
    }

    public IDataSet loadDataSet(final File file) throws DatabaseUnitException
    {
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        try
        {
            return builder.build(new FileReader(file));
        }
        catch (DataSetException e)
        {
            StringBuilder message = new StringBuilder();
            message.append(e.getClass().getName() + " occurred on read IDataSet from file ")
                    .append(file.getAbsoluteFile().getName());
            throw new DatabaseUnitException(message.toString(), e);
        }
        catch (FileNotFoundException e)
        {
            StringBuilder message = new StringBuilder();
            message.append(e.getClass().getName() + " occurred on read IDataSet from file ")
                    .append(file.getAbsoluteFile().getName());
            throw new DatabaseUnitException(message.toString(), e);
        }
    }

    public IDataSet loadDataSet(final String path, final String fileName) throws DatabaseUnitException
    {
        File file = new File(path, fileName);
        return loadDataSet(file);
    }

    public void saveDataSet(final IDataSet dataSet, final File file) throws DatabaseUnitException
    {
        try
        {
            FlatXmlWriter writer = new FlatXmlWriter(new FileWriter(file), "UTF-8");
            writer.write(dataSet);
        }
        catch (IOException e)
        {
            StringBuilder message = new StringBuilder();
            message.append(e.getClass().getName() + " occurred on write IDataSet to file ")
                    .append(file.getAbsoluteFile().getName());
            throw new DatabaseUnitException(message.toString(), e);
        }
    }

    public void saveDataSet(final IDataSet dataSet, final String path, final String fileName) throws DatabaseUnitException
    {
        File file = new File(path, fileName);
        saveDataSet(dataSet, file);
    }

    public void exportTestDtdSchema() throws SQLException, DatabaseUnitException
    {
        IDatabaseConnection connection = new DatabaseConnection(dataSource.getConnection());
        IDataSet dataSet = connection.createDataSet();

        saveDtd(dataSet, DATASET_SRC_FOLDER, "interview-it.dtd");
    }

    public void saveDtd(final IDataSet dataSet, final String path, final String fileName) throws DatabaseUnitException
    {
        File file = new File(path, fileName);
        saveDtd(dataSet, file);
    }

    private void saveDtd(IDataSet dataSet, File file) throws DatabaseUnitException
    {
        try
        {
            FlatDtdWriter writer = new FlatDtdWriter(new FileWriter(file));
            writer.setContentModel(FlatDtdWriter.CHOICE);
            writer.write(dataSet);
        }
        catch (IOException e)
        {
            StringBuilder message = new StringBuilder();
            message.append(e.getClass().getName() + " occurred on write DTD to file ")
                    .append(file.getAbsoluteFile().getName());
            throw new DatabaseUnitException(message.toString(), e);
        }
        catch (DataSetException e)
        {
            StringBuilder message = new StringBuilder();
            message.append(e.getClass().getName() + " occurred on write DTD to file ")
                    .append(file.getAbsoluteFile().getName());
            throw new DatabaseUnitException(message.toString(), e);
        }
    }
}
