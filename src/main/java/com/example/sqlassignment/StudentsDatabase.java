package com.example.sqlassignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


enum myGender { F, M, U}
enum myGrade { A,B,C,D,F,W }
public class StudentsDatabase implements StudentsDatabaseInterface, TableInterface{
    String url;
    String username;
    String password;
    Connection connection;
    StudentsDatabase() {}
    StudentsDatabase (String url, String username, String password) throws SQLException {
        this.url = url;
        this.username = username;
        this.password = password;
        this.connection = getConnection (url, username,password);

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(StudentsDatabaseInterface.dropSchema);
            System.out.println("\nSchema dropped");

        }
        catch (SQLException e) {
            System.out.println("\nSchema not dropped");
            e.printStackTrace();
        }

        //CREATE SCHEMA
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(StudentsDatabaseInterface.createSchema);
            System.out.println("\nSchema created");

        }
        catch (SQLException e) {
            System.out.println("\nSchema not created");
            e.printStackTrace();
        }
    }
    public Connection getConnection (String url, String username, String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("\nConnection to the database server successful!");
        } catch (SQLException e) {
            System.out.println(e);
        }
        return connection;
    }

    // Get map of aggregate grades
    public Map<Character,Integer> getAggregateGrades (String nameTable) {
        Map<Character,Integer> mapAggregateGrades = new HashMap<>();

        try {
            ResultSet RS = TableInterface.getTable(connection, nameTable);
            while (RS.next()) {
                mapAggregateGrades.put(RS.getString("grade").charAt(0), RS.getInt("num_students"));
            }
        }
        catch (SQLException e) {System.out.println(e);}

        return mapAggregateGrades;
    }
    public void updateCourseInstructor (Connection connection, String courseId, String sectionNumber, String nameInstructor) throws SQLException {
        String updateQuery = StudentsDatabaseInterface.upDateCourseInstructor(courseId, sectionNumber, nameInstructor);
        PreparedStatement pStatement = connection.prepareStatement(updateQuery);
        try {
            pStatement.executeUpdate();
            System.out.println("\nUpdate successful for courseId " + courseId + ".");
            System.out.println(nameInstructor + "is your new instructor");
        }
        catch (SQLException e) {
            System.out.println("\nError in update course Instructor");
            System.out.println(e);
        }

    }

    public class Schedule {
        String createTable;
        String populateTable;
        String updateCourseInstructor;
        String updateInstructor;
        String fileName;
        String nameTable;

        Schedule (String createTable, String fileName, String nameTable) throws SQLException {
            this.createTable = createTable;
            this.fileName = fileName;
            this.nameTable = nameTable;
            this.populateTable = TableInterface.loadDataInFileTable(fileName,nameTable);

            // Create Table
            TableInterface.dropTable(connection, nameTable);
            TableInterface.createTable(connection, createTable, nameTable);


            // Populate Table
            TableInterface.setLocalInFileLoading(connection);
            TableInterface.populateTable(connection, populateTable,nameTable);


        }

    }
    public class Students{
        String createTable;
        String populateTable;

        String nameTable;

        public Students (String createTable, String nameTable, String sql) throws SQLException {
            this.createTable = createTable;
            this.nameTable = nameTable;

            // Create Table
            TableInterface.dropTable(connection, nameTable);
            TableInterface.createTable(connection, createTable,nameTable);

            // Populate Table

            TableInterface.populateTable(connection, sql,nameTable);
        }
    }
    public class Courses{
    String createTable;
    String populateTable;
    String nameFromTable;
    String nameToTable;

        public Courses(String createTable, String nameToTable, String nameFromTable) throws SQLException {
            this.createTable = createTable;
            this.nameFromTable = nameFromTable;
            this.nameToTable = nameToTable;
            this.populateTable = StudentsDatabaseInterface.insertTableCourses(nameToTable,nameFromTable);


            // Create Table
            TableInterface.dropTable(connection, nameToTable);
            TableInterface.createTable(connection, createTable,nameToTable);

            // Populate Table
            TableInterface.populateTable(connection, populateTable,nameToTable);
        }
    }
    public class Classes{
        String createTable;
        String populateTable;
        String nameTable;

        public Classes(String createTable, String nameTable) throws SQLException {
            this.createTable = createTable;
            this.nameTable = nameTable;
            // Create Table
            TableInterface.dropTable(connection, nameTable);
            TableInterface.createTable(connection, createTable,nameTable);
        }
        static void updateGrade(Connection connection, String ID) throws SQLException {
            String sql = "UPDATE Students.Classes SET grade = \"A\" " +
                    "WHERE courseId = \"22100 R\" AND empId = \'" + ID + "\' OR " +
                    "courseId = \"22100 R\" AND empId = \'" + ID + "\' OR " +
                    "courseId = \"22100 R\" AND empId = \'" + ID + "\';";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.executeUpdate();
        }
    }
    public class AggregateGrades{
        String createTable;
        String populateTable;

        String nameTable;


        public AggregateGrades(String createTable, String nameTable) throws SQLException {
            this.createTable = createTable;
            this.nameTable = nameTable;
            this.populateTable = "INSERT INTO  " + nameTable + " " + StudentsDatabaseInterface.aggregateGradesAllClasses;

            // Create Table
            TableInterface.dropTable(connection, nameTable);
            TableInterface.createTable(connection, createTable, nameTable);

            // Populate Table
            TableInterface.populateTable(connection, populateTable, nameTable);
        }
        static void updateAggregateGrades(Connection connection, String nameTable, String grade, String num_students) throws SQLException {
            String updateQuery = "UPDATE " + nameTable +
                    " SET num_students = '" + num_students + "'" +
                    " WHERE grade = '" + grade + "';";
            PreparedStatement pStatement = connection.prepareStatement(updateQuery);
            try {
                pStatement.executeUpdate();
                System.out.println("\nUpdate successful for AggregateGrades:  " + nameTable + ".");

            }
            catch (SQLException e) {
                System.out.println("\nError in update Aggregate grades; " + nameTable);
                System.out.println(e);
            }
        }

    }
}

