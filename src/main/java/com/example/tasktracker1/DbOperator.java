package com.example.tasktracker1;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DbOperator extends DBConfig {
    String TASK_TABLE = "tasktracker";
    String TASK_COLUMN = "task";

    public Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("org.postgresql.Driver");
        Properties auth = new Properties();
        auth.put("user", dbUser);
        auth.put("password", dbPass);
        Connection dbConnection = DriverManager.getConnection(connectionString,auth);
        return dbConnection;
    }
    public void taskWriter(String task) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + TASK_TABLE +"(" + TASK_COLUMN + ")" + "VALUES(?)";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1,task);
        getDbConnection().close();
        preparedStatement.executeUpdate();
    }
    public void taskDeleter(String task) throws ClassNotFoundException, SQLException {
        String delete = "DELETE FROM " + TASK_TABLE + " WHERE " + TASK_COLUMN + " = "  + "'" + task + "'" + ";";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(delete);
       // preparedStatement.setC(1,task);// ПОЧЕМУ НЕ РАБОТАЕТ ЭТА ВАРИАЦИЯ?
        preparedStatement.executeUpdate();
        getDbConnection().close();
    }

    public ArrayList<String> valueGetter() throws ClassNotFoundException, SQLException{
        //LinkedList <RadioButton> startButtonList = new LinkedList<>();
        ArrayList<String> startButtonList = new ArrayList<>();
        String select = "SELECT * " + "FROM " + TASK_TABLE;
        Statement statement = getDbConnection().createStatement();
        ResultSet result = statement.executeQuery(select);
        while (result.next()) {
            //RadioButton startRButton = new RadioButton();
            String task = result.getString("task");
            startButtonList.add(task);
        }
        return startButtonList;
    }

}
