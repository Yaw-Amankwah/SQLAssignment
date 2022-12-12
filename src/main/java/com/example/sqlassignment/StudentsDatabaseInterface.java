package com.example.sqlassignment;

public interface StudentsDatabaseInterface {
    String dropSchema = "DROP SCHEMA IF EXISTS Students";
    String createSchema = "CREATE SCHEMA Students ";
    String createTableSchedule = "CREATE TABLE Students.Schedule (" +
            "courseId CHAR(12) NOT NULL UNIQUE, " +
            "sectionNumber VARCHAR(64), " +
            "title VARCHAR(64), " +
            "year INT, " +
            "semester CHAR(6), " +
            "instructor VARCHAR(24), " +
            "department CHAR(16), " +
            "program VARCHAR(48), " +
            "PRIMARY KEY(courseId))";
    String createTableStudents = "CREATE TABLE Students.Students (" +
            "empId VARCHAR(32) PRIMARY KEY, " +
            "firstName VARCHAR(32) NOT NULL, " +
            "lastName VARCHAR(32) NOT NULL, " +
            "email VARCHAR(32) NOT NULL, " +
            "gender CHAR CHECK (gender = 'F' OR gender = 'M' OR gender = 'U'))";
    String createTableCourses = "CREATE TABLE Students.Courses (" +
            "courseId CHAR(12) PRIMARY KEY REFERENCES Students.Schedule(courseId), " +
            "title VARCHAR(64), " +
            "department CHAR(16), " +
            "program VARCHAR(48))";
    String createTableClasses = "CREATE TABLE Students.Classes (" +
            "empId VARCHAR(32) REFERENCES Student(empId), " +
            "courseId CHAR(12) REFERENCES Schedule (courseId), " +
            "sectionNumber VARCHAR(8) REFERENCES Schedule(sectionNumber), " +
            "year INT, " +
            "semester CHAR(6), " +
            "grade CHAR CHECK (grade = 'A' OR grade = 'B' OR grade = 'C' OR grade = 'D' OR grade = 'F' OR grade = 'W'), " +
            "PRIMARY KEY(empId, courseId,sectionNumber))";
    String aggregateGrades221 = "SELECT grade, count(grade) " +
            "FROM Students.Classes " +
            "WHERE courseId = \"22100 R\" OR courseId = \"22100 F\" OR courseId = \"22100 P\" " +
            "GROUP BY grade";
<<<<<<< HEAD
    String aggregateGradesAllClasses = "SELECT grade, count(grade) " +
            "FROM Students.Classes " +
            "GROUP BY grade";


    static String upDateCourseInstructor (String courseId, String sectionNumber, String nameInstructor) {
     return  "UPDATE Students.Schedule" +
=======

    static String upDateCourseInstructor (String courseId, String sectionNumber, String nameInstructor) {
     return  "UPDATE Schedule" +
>>>>>>> 78abf7d (Initial commit)
             " SET instructor = " + nameInstructor +
             " WHERE courseId = " + courseId + " AND + " + "sectionNumber = " + sectionNumber;
    }
    static String updateInstructor (String nameInstructor, String nameNewInstructor) {
<<<<<<< HEAD
     return "UPDATE Students.Schedule " +
             " SET instructor = " + nameNewInstructor +
             " WHERE instructor = " + nameInstructor;
=======
     return "UPDATE Schedule " +
             " SET instructor = " + nameInstructor +
             " WHERE instructor = " + nameNewInstructor; //SHOULDN'T THE TWO ABOVE BE SWITCHED??
>>>>>>> 78abf7d (Initial commit)
    }

    static String insertTableCourses (String nameToTable, String nameFromTable) {
     return "INSERT INTO " + nameToTable +
             " SELECT courseId, title, department, program" +
             " FROM " + nameFromTable;
    }
    static String insertTableClasses (String nameToTable, String nameFromTable) {
        return "INSERT INTO " + nameToTable +
                " SELECT courseId, empId, sectionNumber, year, semester, grade" +
                " FROM " + nameFromTable;
    }


}

