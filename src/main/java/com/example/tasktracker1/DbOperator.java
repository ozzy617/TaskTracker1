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
        String getId = "SELECT id FROM lists WHERE name = '" + tableName + "';";
        Statement statementId = getDbConnection().createStatement();
        ResultSet resultSet = statementId.executeQuery(getId);
        resultSet.next();
        String tableId = resultSet.getString(1);

        String deleteTasks = "INSERT INTO tasks (task, list_id) VALUES ('" + task + "', '" + tableId +"');";
        Statement deleteTasksStatement = getDbConnection().createStatement();
        deleteTasksStatement.executeUpdate(deleteTasks);
        deleteTasksStatement.close();
    }
    //DONE
    public void deleteTask(String task, String tableName) throws ClassNotFoundException, SQLException {
        //ПОВТОР
        String getId = "SELECT id FROM lists WHERE name = '" + tableName + "';";
        Statement statementId = getDbConnection().createStatement();
        ResultSet resultSet = statementId.executeQuery(getId);
        resultSet.next();
        String tableId = resultSet.getString(1);
        //

        String deleteTasks = "DELETE FROM tasks WHERE id = ( SELECT id FROM tasks WHERE list_id = " + tableId  + " AND task = '" + task + "' ORDER BY id DESC LIMIT 1);";
        Statement deleteTaskStatement = getDbConnection().createStatement();
        deleteTaskStatement.executeUpdate(deleteTasks);
        deleteTaskStatement.close();
        System.out.println(deleteTasks);
    }

    //DONE
    public ArrayList<String> loadListValues(String tableName) throws ClassNotFoundException, SQLException {
        ArrayList<String> existingsTasks = new ArrayList<>();
        String select = "SELECT tasks.task FROM tasks JOIN lists ON tasks.list_id = lists.id WHERE lists.name = '" + tableName + "';";
        Statement statement = getDbConnection().createStatement();
        ResultSet result = statement.executeQuery(select);
        while (result.next()) {
            String task = result.getString(COLUMN_NAME);
            existingsTasks.add(task);
        }
        statement.close();
        return existingsTasks;
    }

    //DONE
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

    //DONE
    public void deleteTable(String tableName, int position) throws SQLException, ClassNotFoundException {
        String getId = "SELECT id FROM lists WHERE name = '" + tableName + "';";
        Statement statementId = getDbConnection().createStatement();
        ResultSet resultSet = statementId.executeQuery(getId);
        resultSet.next();
        String tableId = resultSet.getString(1);
        System.out.println(tableId + " - idDelTable");

        String deleteTasks = "DELETE FROM tasks WHERE list_id = " + tableId + ";";
        Statement deleteTasksStatement = getDbConnection().createStatement();
        deleteTasksStatement.executeUpdate(deleteTasks);
        deleteTasksStatement.close();

        String deleteTable = "DELETE FROM lists WHERE name = '" + tableName + "';";
        Statement deleteTableStatement = getDbConnection().createStatement();
        deleteTableStatement.executeUpdate(deleteTable);
        deleteTableStatement.close();
    }

    //DONE
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

    //DONE
    public String createList(String listName) throws SQLException, ClassNotFoundException {
        String create = "INSERT INTO " + " lists (name)" +  "VALUES('" + listName + "');";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(create);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return listName;
    }
}

