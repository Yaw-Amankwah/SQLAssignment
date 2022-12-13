package com.example.sqlassignment;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface TableInterface {
    static void dropSchema (Connection connection, String dbName) throws SQLException {

        PreparedStatement psDropSchema = connection.prepareStatement("DROP SCHEMA IF EXISTS " + dbName);
        try {
            psDropSchema.executeUpdate();
            System.out.println("Schema dropped");
        }
        catch (SQLException e) {
            System.out.println("Error in dropSchema");
            System.out.println(e);
        }
    }
    static void dropTable (Connection connection, String nameTable) throws SQLException {
        PreparedStatement psDropTable = connection.prepareStatement ("DROP TABLE IF EXISTS " + nameTable);
        try {
            psDropTable.executeUpdate();
        }
        catch (SQLException e) {System.out.println(e);}
    }

    // Create a Table
    static void createTable (Connection connection, String createTable, String nameTable) throws SQLException {
        PreparedStatement psCreateTable = connection.prepareStatement(createTable);
        try {
            psCreateTable.executeUpdate();
            System.out.println("\nTable created successfully: " + nameTable);
        }
        catch (SQLException e) {
            System.out.println("\nError in createTable: " + nameTable);
            System.out.println(e);
        }
    }

    // Set Local in file parameter for local data loading: MySQL server
    static void setLocalInFileLoading (Connection connection) throws SQLException {
        PreparedStatement psSetLocalInFileLoading = connection.prepareStatement("SET GLOBAL local_infile = 1"); //MORE STUFF TO TYPE HERE
        try {
            psSetLocalInFileLoading.executeUpdate();
            System.out.println("\nGlobal local infile set successfully");
        }
        catch (SQLException e) {System.out.println(e);} //REMOVE THIS
    }

    static ResultSet getTable(Connection connection, String nameTable) throws SQLException {
        ResultSet RS = null;
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM " + nameTable;
        try {
            RS = statement.executeQuery(query);
            System.out.println("\nGet table successful");
        }
        catch (SQLException e) {
            System.out.println("\nError in getTable");
            System.out.println(e);
        }
        return RS;
    }

    static String loadDataInFileTable(String fileName, String nameTable) {
        return "LOAD DATA LOCAL INFILE '" + fileName + "' INTO TABLE " + nameTable +
                " FIELDS TERMINATED BY '\t'" +
                " LINES TERMINATED BY '\n'" +
                " IGNORE 1 LINES;";
    }

    static void populateTable(Connection connection, String populateTable, String nameTable) throws SQLException {
        Statement statement = connection.createStatement();
        try{
            statement.executeUpdate(populateTable);
            System.out.println("\nTable populated successfully: " + nameTable);
        }
        catch(SQLException e) {
            System.out.println("\nError in populate Table: " + nameTable);
            System.out.println(e);
        }
    }
    static void updateTable (Connection connection, String nameTable, String setField, String to, String whereField, String from) throws SQLException {
        String sql = "UPDATE " + nameTable +
                " SET " + setField + " = " + to +
                " WHERE " + whereField + " = " + from;
        PreparedStatement pStatement = connection.prepareStatement(sql);
        try {
            pStatement.executeUpdate();
            System.out.println("\nUpdate successful: " + nameTable);
        }
        catch (SQLException e) {
            System.out.println("\nError in updateTable " + nameTable);
            System.out.println(e);
        }

    }
}
