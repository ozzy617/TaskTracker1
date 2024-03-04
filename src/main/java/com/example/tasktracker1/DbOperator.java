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
        //tableName = changeTaskNameForm(tableName);
        tableName = findTable(tableName);
        String insert = "INSERT INTO " + tableName + "(" + COLUMN_NAME + ")" + "VALUES(?)";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1, task);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void deleteTask(String task, String tableName) throws ClassNotFoundException, SQLException {
        tableName = findTable(tableName);
        System.out.println("deleteTask " + tableName);
        String delete = "DELETE FROM " + tableName + " WHERE " + COLUMN_NAME + " = "  + "'" + task + "'" + ";";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(delete);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public ArrayList<String> loadListValues(String tableName) throws ClassNotFoundException, SQLException {
        ArrayList<String> existingsTasks = new ArrayList<>();
        tableName = findTable(tableName);
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
            String select = "SELECT COUNT(*) FROM " + s + ";";
            ResultSet set = statement.executeQuery(select);
            set.next();
            String res = set.getString(1);
            rowsSum.add(res);
        }
        statement.close();
        return rowsSum;
    }

    public void deleteTable(String tableName, int position) throws SQLException, ClassNotFoundException {

        ArrayList<String> tableNames = loadListNames();
        for (int i = position; i < tableNames.size(); i++) {
            String oldValue = tableNames.get(i);
            int place = Integer.parseInt(String.valueOf(tableNames.get(i).charAt(3))) - 1;//!!!!!!!
            String newValue = "tbl" + String.valueOf(place) + tableNames.get(i).substring(4);
            System.out.println(newValue + " RENAMED TO!");
            renameTable(oldValue,newValue);
        }

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
            existingsLists.add(listName);
        }
        getDbConnection().close();
        return existingsLists;
    }

    public String createList(String listName) throws SQLException, ClassNotFoundException {
        if (findTable(listName).equals("")) {
            listName = changeTableNameForm(listName);
            String create = "CREATE TABLE " + listName + " (task TEXT);";
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(create);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return listName;
        }
        return null;
    }

    private String changeTableNameForm(String tName) throws SQLException, ClassNotFoundException {
        int listPlace = loadListNames().size() + 1;
        tName = "tbl" + String.valueOf(listPlace) + tName;
        tName = tName.replaceAll(" ","_");
        return tName;
    }
    private void renameTable(String oldV, String newV) throws SQLException, ClassNotFoundException {
        String rename = "ALTER TABLE " + oldV +  " RENAME TO " + newV;
        Statement statement = getDbConnection().createStatement();
        statement.executeUpdate(rename);
        statement.close();
    }
    private String findTable(String tableName) throws SQLException, ClassNotFoundException {
        ArrayList<String> tableNames = loadListNames();
        String actualTableName = "";
        tableName = tableName.replaceAll(" ","_");
        for (String name : tableNames) {
            System.out.println(name.substring(4) + " - name, - " + tableName);
            if (tableName.equals(name.substring(4))) {
                actualTableName = name;
            }
        }
        return actualTableName;
    }
}

