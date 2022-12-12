package com.example.sqlassignment;

import java.util.ArrayList;

public class Student {
    private String empId;
    private String firstName;
    private String lastName;
    private String email;
    private myGender gender;
    private ArrayList<String> studentClasses;
    private ArrayList<String> studentSectionNumbers;
    private ArrayList<String> studentYears;
    private ArrayList<String> studentSemester;
    private ArrayList<String> studentGrades;

    public Student(){}
    public Student(String empId, String firstName, String lastName, String email, myGender gender) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }

    //SETTERS
    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setClasses (ArrayList<String> studentClasses) {
        this.studentClasses = studentClasses;
    }

    public void setStudentSectionNumbers(ArrayList<String> studentSectionNumbers) {
        this.studentSectionNumbers = studentSectionNumbers;
    }

    public void setStudentYears(ArrayList<String> studentYears) {
        this.studentYears = studentYears;
    }

    public void setStudentSemester(ArrayList<String> studentSemester) {
        this.studentSemester = studentSemester;
    }

    public void setStudentGrades(ArrayList<String> studentGrades) {
        this.studentGrades = studentGrades;
    }

    public void setGender(myGender gender) {
        this.gender = gender;
    }

   //GETTERSS
    public String getEmpId() {
        return empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public myGender getGender() {
        return gender;
    }
    public ArrayList<String> getStudentClasses (){return studentClasses;}

    public ArrayList<String> getStudentSectionNumbers() {
        return studentSectionNumbers;
    }

    public ArrayList<String> getStudentYears() {
        return studentYears;
    }

    public ArrayList<String> getStudentSemester() {
        return studentSemester;
    }

    public ArrayList<String> getStudentGrades() {
        return studentGrades;
    }
}
