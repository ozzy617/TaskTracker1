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

    public void taskWriter(String task) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + TABLE_NAME +"(" + COLUMN_NAME + ")" + "VALUES(?)";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1, task);
        getDbConnection().close();
        preparedStatement.executeUpdate();
    }

    public void taskDeleter(String task) throws ClassNotFoundException, SQLException {
        String delete = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = "  + "'" + task + "'" + ";";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(delete);
       // preparedStatement.setC(1,task);// ПОЧЕМУ НЕ РАБОТАЕТ ЭТА ВАРИАЦИЯ?
        preparedStatement.executeUpdate();
        getDbConnection().close();
    }

    public ArrayList<String> loadValues() throws ClassNotFoundException, SQLException {
        //LinkedList <RadioButton> startButtonList = new LinkedList<>();
        ArrayList<String> existingsTasks = new ArrayList<>();
        String select = "SELECT * " + "FROM " + TABLE_NAME;
        Statement statement = getDbConnection().createStatement();
        ResultSet result = statement.executeQuery(select);
        while (result.next()) {
            //RadioButton startRButton = new RadioButton();
            String task = result.getString(COLUMN_NAME);
            existingsTasks.add(task);
        }
        return existingsTasks;
    }

}
