package com.example.tasktracker1;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class DbOperator extends DBConfig {

    private final static String COLUMN_ID = "id";

    private  final static String COLUMN_NAME_TASK = "task";

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("org.postgresql.Driver");
        Properties auth = new Properties();
        auth.put("user", dbUser);
        auth.put("password", dbPass);
        return DriverManager.getConnection(connectionString,auth);
    }

    //TEST PASSED
    public void writeTask(String task, String tableName) throws SQLException, ClassNotFoundException {
        String getId = "SELECT " + COLUMN_ID + " FROM lists WHERE name = '" + tableName + "';";
        Statement statementId = getDbConnection().createStatement();
        ResultSet resultSet = statementId.executeQuery(getId);
        resultSet.next();
        String tableId = resultSet.getString(1);

        String deleteTasks = "INSERT INTO tasks (task, list_id) VALUES ('" + task + "', '" + tableId +"');";
        Statement deleteTasksStatement = getDbConnection().createStatement();
        deleteTasksStatement.executeUpdate(deleteTasks);
        deleteTasksStatement.close();
    }

    //TEST PASSED
    public void deleteTask(String task, String tableName) throws ClassNotFoundException, SQLException {
        String tableId = getTableId(tableName);

        String deleteTasks = "DELETE FROM tasks WHERE " + COLUMN_ID + " = ( SELECT " + COLUMN_ID + " FROM tasks WHERE list_id = " + tableId  + " AND task = '" + task + "' ORDER BY id DESC LIMIT 1);";
        Statement deleteTaskStatement = getDbConnection().createStatement();
        deleteTaskStatement.executeUpdate(deleteTasks);
        deleteTaskStatement.close();
        System.out.println(deleteTasks);
    }

    //TEST PASSED
    public ArrayList<String> loadListValues(String tableName) throws ClassNotFoundException, SQLException {
        ArrayList<String> existingsTasks = new ArrayList<>();
        String select = "SELECT tasks.task FROM tasks JOIN lists ON tasks.list_id = lists.id WHERE lists.name = '" + tableName + "';";
        Statement statement = getDbConnection().createStatement();
        ResultSet result = statement.executeQuery(select);
        while (result.next()) {
            String task = result.getString(COLUMN_NAME_TASK);
            existingsTasks.add(task);
        }
        statement.close();
        getDbConnection().close();
        return existingsTasks;
    }
    //HashMap<String, String>
    public ArrayList<String> loadSearchedValues(String task) throws SQLException, ClassNotFoundException {
        String selectSearch = "SELECT tasks.task, lists.name FROM tasks JOIN lists ON tasks.list_id = lists.id WHERE tasks.task = '" + task +"'";
        Statement statement =  getDbConnection().createStatement();
        ResultSet result = statement.executeQuery(selectSearch);
        HashMap<String, String> tableMap = new HashMap<String, String>();
        ArrayList<String> searchedLists = new ArrayList<>();
        while(result.next()) {
            String listValue = result.getString("name");
            searchedLists.add(listValue);
            String taskValue = result.getString(COLUMN_NAME_TASK);
        }
        statement.close();
        getDbConnection().close();
        return searchedLists;
    }

    //TEST PASSED
    public ArrayList<String> countRows(ArrayList<String> tableNames) throws SQLException, ClassNotFoundException {
        ArrayList<String> rowsSum = new ArrayList<>();
        Statement statement = getDbConnection().createStatement();
        for (String s : tableNames) {
            String select = "SELECT COUNT(*) FROM tasks JOIN lists ON tasks.list_id = lists.id WHERE lists.name = '" + s + "';";
            ResultSet set = statement.executeQuery(select);
            set.next();
            String res = set.getString(1);
            rowsSum.add(res);
        }
        statement.close();
        return rowsSum;
    }

    //TEST PASSED
    public void deleteTable(String tableName) throws SQLException, ClassNotFoundException {
        String tableId = getTableId(tableName);

        String deleteTasks = "DELETE FROM tasks WHERE list_id = " + tableId + ";";
        Statement deleteTasksStatement = getDbConnection().createStatement();
        deleteTasksStatement.executeUpdate(deleteTasks);
        deleteTasksStatement.close();

        String deleteTable = "DELETE FROM lists WHERE name = '" + tableName + "';";
        Statement deleteTableStatement = getDbConnection().createStatement();
        deleteTableStatement.executeUpdate(deleteTable);
        deleteTableStatement.close();
    }

    //TEST PASSED
    public ArrayList<String> loadListNames() throws SQLException, ClassNotFoundException {
        ArrayList<String> existingsLists = new ArrayList<>();
        String load = "SELECT name FROM lists;";
        Statement statement = getDbConnection().createStatement();
        ResultSet result = statement.executeQuery(load);
        while (result.next()) {
            String listName = result.getString(1);
            existingsLists.add(listName);
        }
        statement.close();
        getDbConnection().close();
        return existingsLists;
    }

    //TEST PASSED
    public String createList(String listName) throws SQLException, ClassNotFoundException {
        String create = "INSERT INTO " + " lists (name)" +  "VALUES('" + listName + "');";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(create);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return listName;
    }

    //NOT TESTING
    private String getTableId(String tableName) throws SQLException, ClassNotFoundException {
        String getId = "SELECT " + COLUMN_ID + " FROM lists WHERE name = '" + tableName + "';";
        Statement statementId = getDbConnection().createStatement();
        ResultSet resultSet = statementId.executeQuery(getId);
        resultSet.next();
        String tableId = resultSet.getString(1);
        return tableId;
    }
}

