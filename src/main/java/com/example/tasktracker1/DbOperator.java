package com.example.tasktracker1;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DbOperator extends DBConfig {

    private final static String TABLE_NAME = "tasktracker";
    private final static String COLUMN_NAME = "task";

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("org.postgresql.Driver");
        Properties auth = new Properties();
        auth.put("user", dbUser);
        auth.put("password", dbPass);DriverManager.getConnection(connectionString,auth);
        return DriverManager.getConnection(connectionString,auth);
    }

    public void taskWriter(String task, String listName) throws SQLException, ClassNotFoundException {
        String tName = "a" + listName;
        tName = tName.replaceAll(" ","_");
        String insert = "INSERT INTO " + tName +"(" + COLUMN_NAME + ")" + "VALUES(?)";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1, task);
        getDbConnection().close();
        preparedStatement.executeUpdate();
    }

    public void taskDeleter(String task, String tableName) throws ClassNotFoundException, SQLException {
        String tName = "a" + tableName;
        tName = tName.replaceAll(" ","_");
        String delete = "DELETE FROM " + tName + " WHERE " + COLUMN_NAME + " = "  + "'" + task + "'" + ";";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(delete);
       // preparedStatement.setC(1,task);// ПОЧЕМУ НЕ РАБОТАЕТ ЭТА ВАРИАЦИЯ?
        preparedStatement.executeUpdate();
        getDbConnection().close();
    }

    public ArrayList<String> loadListValues(String tableName) throws ClassNotFoundException, SQLException {
        //LinkedList <RadioButton> startButtonList = new LinkedList<>();
        ArrayList<String> existingsTasks = new ArrayList<>();
        String tName = "a" + tableName;
        tName = tName.replaceAll(" ","_");
        String select = "SELECT * " + "FROM " + tName;
        Statement statement = getDbConnection().createStatement();
        ResultSet result = statement.executeQuery(select);
        while (result.next()) {
            //RadioButton startRButton = new RadioButton();
            String task = result.getString(COLUMN_NAME);
            existingsTasks.add(task);
        }
        return existingsTasks;
    }

    public ArrayList<String> loadListNames() throws SQLException, ClassNotFoundException {
        ArrayList<String> existingsLists = new ArrayList<>();

//        String selection = "SHOW TABLES";
//        Statement statement = getDbConnection().createStatement();
//        ResultSet result = statement.executeQuery(selection);

        DatabaseMetaData metaData = getDbConnection().getMetaData();
        String[] type = {"TABLE"};
        ResultSet result = metaData.getTables(null, null, "%", type);
        while (result.next()) {
            String listName = result.getString("TABLE_NAME");
            listName = listName.substring(1);
            existingsLists.add(listName);
        }
        return existingsLists;
    }

    public void createList(String listName) throws SQLException, ClassNotFoundException {
        String chengedListName = "a" + listName;
        chengedListName = chengedListName.replaceAll(" ","_");
       // String creation = "ALTER TABLE "  + TABLE_NAME  + " ADD " + chengedListName + " TEXT";
        String creation = "CREATE TABLE " + chengedListName + " (task TEXT);";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(creation);
        preparedStatement.executeUpdate();
        getDbConnection().close();
    }

}
