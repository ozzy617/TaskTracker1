package com.example.tasktracker1;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DbOperator extends DBConfig {

    private final static String COLUMN_NAME = "task";

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("org.postgresql.Driver");
        Properties auth = new Properties();
        auth.put("user", dbUser);
        auth.put("password", dbPass);
        return DriverManager.getConnection(connectionString,auth);
    }

    public void writeTask(String task, String tableName) throws SQLException, ClassNotFoundException {
        tableName = changeNameForm(tableName);
        String insert = "INSERT INTO " + tableName +"(" + COLUMN_NAME + ")" + "VALUES(?)";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1, task);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void deleteTask(String task, String tableName) throws ClassNotFoundException, SQLException {
        tableName = changeNameForm(tableName);
        String delete = "DELETE FROM " + tableName + " WHERE " + COLUMN_NAME + " = "  + "'" + task + "'" + ";";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(delete);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public ArrayList<String> loadListValues(String tableName) throws ClassNotFoundException, SQLException {
        ArrayList<String> existingsTasks = new ArrayList<>();
        tableName = changeNameForm(tableName);
        String select = "SELECT * " + "FROM " + tableName;
        Statement statement = getDbConnection().createStatement();
        ResultSet result = statement.executeQuery(select);
        while (result.next()) {
            String task = result.getString(COLUMN_NAME);
            existingsTasks.add(task);
        }
        statement.close();
        return existingsTasks;
    }

    public ArrayList<String> countRows(ArrayList<String> tableNames) throws SQLException, ClassNotFoundException {
        ArrayList<String> rowsSum = new ArrayList<>();
        Statement statement = getDbConnection().createStatement();
        for (String s : tableNames) {
            s = changeNameForm(s);
            String select = "SELECT COUNT(*) FROM " + s + ";";
            ResultSet set = statement.executeQuery(select);
            set.next();
            String res = set.getString(1);
            rowsSum.add(res);
        }
        statement.close();
        return rowsSum;
    }

    public void deleteTable(String tableName) throws SQLException, ClassNotFoundException {
        tableName = changeNameForm(tableName);
        String delete = "DROP TABLE " + tableName;
        Statement statement = getDbConnection().createStatement();
        statement.executeUpdate(delete);
        statement.close();
    }

    public ArrayList<String> loadListNames() throws SQLException, ClassNotFoundException {
        ArrayList<String> existingsLists = new ArrayList<>();
        DatabaseMetaData metaData = getDbConnection().getMetaData();
        String[] type = {"TABLE"};
        ResultSet result = metaData.getTables(null, null, "%", type);
        while (result.next()) {
            String listName = result.getString("TABLE_NAME");
            listName = listName.substring(1);
            existingsLists.add(listName);
        }
        getDbConnection().close();
        return existingsLists;
    }

    public void createList(String listName) throws SQLException, ClassNotFoundException {
         listName = changeNameForm(listName);
        String create = "CREATE TABLE " + listName + " (task TEXT);";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(create);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    private String changeNameForm(String tName) {
        tName = "a" + tName;
        tName = tName.replaceAll(" ","_");
        return tName;
    }
}
