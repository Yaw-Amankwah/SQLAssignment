package com.example.sqlassignment;

import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

public class HelloApplication extends Application {
    final int CANVASWIDTH = 1080;
    final int CANVASHEIGHT = 720;
    final int linewidth = 5;

    int n;
    static final String url = "jdbc:mysql://localhost:3306/?autoReconnect=true&zeroDateTimeBehavior=convertToNull&useUnicode=yes&characterEncoding=UTF-8&allowLoadLocalInfile=true";
    static final String username = "root";
    static final String password = "Jynda$1226!";
    static Map<Character, Integer> AggregateGrades;
    @Override
    public void start(Stage primaryStage) throws IOException {

        n = 6;

        int padding = 100;
        DecimalFormat df = new DecimalFormat("#.##");

        HistogramAlphaBet h = new HistogramAlphaBet(AggregateGrades);
        HistogramAlphaBet.MyPieChart p = h.new MyPieChart(n,new MyPoint((CANVASWIDTH/2)-(padding * 0.45),CANVASHEIGHT/2)
                ,Math.min((CANVASHEIGHT - padding)/2, (CANVASWIDTH - padding)/2) ,0.0);

        Pane pane = new Pane();
        Scene scene = new Scene(pane, CANVASWIDTH,CANVASHEIGHT);
        //scene.setFill(Color.BLUE);
        Canvas canvas = new Canvas(CANVASWIDTH,CANVASHEIGHT);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(linewidth);
        gc.setStroke(MyColor.WHITE.getJavaFXColor());

        //GENERATE LEGEND
        int i = 0;
        double legendSide = 10;
        double dy = CANVASHEIGHT/8;
        double tPLCy = dy; //tPLC rectangle y
        double tPLCx = CANVASWIDTH - 125; //tPLC rectangle x
        MyPoint point = new MyPoint(tPLCx, tPLCy); // tPLC rectangle
        double textX = tPLCx + 15; //text shifted 20 pixels to right of rectangle
        double textY = tPLCy; // text at same height as rectangle
        double sum_probabilities = 0;

        for (Character Key: h.getProbability().keySet() ) {
            if (i < n) {
                MyRectangle rect = new MyRectangle(point, legendSide,legendSide, p.getSlices().get(Key).getColor());
                rect.draw(gc);
                point.shiftY(dy);//SHIFT SUBSEQUENT RECTANGLES dy PIXELS DOWN
                Text t = new Text(String.valueOf("Grade " + Key  + ": " + df.format(h.getProbability().get(Key))));
                t.setTextAlignment(TextAlignment.CENTER);
                t.setTextOrigin(VPos.TOP);
                t.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 12));
                t.setFill(MyColor.BLACK.getJavaFXColor());
                t.setX(textX);
                t.setY(textY);
                textY+=dy; //MOVE NEXT TEXT DOWN BY dy PIXELS
                pane.getChildren().addAll(t);
                sum_probabilities += h.getProbability().get(Key);
                i++;
            }
        }

        p.printOut();
        p.draw(gc);

        pane.getChildren().add(canvas);
        primaryStage.setTitle("GRADE DISTRIBUTION FOR CSC22100");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {

                StudentsDatabase DB = new StudentsDatabase(url, username,password);

                String createTable;
                String fileName = "/Users/yawamankwah/Desktop/FALL2022/CSC221/SQLAssignment4/Resources/ScheduleSpring2022.txt";
                String nameTable;
                Connection connection = DriverManager.getConnection(url, username,password);

                //Create Populate Table Students.Schedule
                nameTable = "Students.Schedule";
                createTable = StudentsDatabaseInterface.createTableSchedule;
                DB.new Schedule(createTable,fileName,nameTable);

                //Create Populate Table Students.Courses
                String nameToTable = "Students.Courses";
                String nameFromTable = "Students.Schedule";
                createTable = StudentsDatabaseInterface.createTableCourses;
                DB.new Courses(createTable,nameToTable,nameFromTable);

                //CREATE ARRAY OF STUDENT FIELDS
                int numStudents = 100;
                ArrayList<String> empIdList = new ArrayList<>();
                for (int i = 0; i < numStudents; i++) {
                    Random rnd = new Random();
                    int number = rnd.nextInt(10000000,99999999);
                    empIdList.add(String.valueOf(number));
                }
                ArrayList<String> firstNameList = new ArrayList<>();
                Collections.addAll(firstNameList,
                        "James",
                        "Robert",
                        "John",
                        "Michael",
                        "David",
                        "William",
                        "Richard",
                        "Joseph",
                        "Thomas",
                        "Charles",
                        "Mary",
                        "Patricia",
                        "Jennifer",
                        "Linda",
                        "Elizabeth",
                        "Barbara",
                        "Susan",
                        "Jessica",
                        "Sarah",
                        "Karen");
                ArrayList<String> lastNameList = new ArrayList<>();
                Collections.addAll(lastNameList,
                        "Smith",
                        "Johnson",
                        "Williams",
                        "Brown",
                        "Jones",
                        "Garcia",
                        "Miller",
                        "Davis",
                        "Rodriguez",
                        "Martinez",
                        "Hernandez",
                        "Lopez",
                        "Gonzales",
                        "Wilson",
                        "Anderson",
                        "Thomas",
                        "Taylor",
                        "Moore",
                        "Jackson",
                        "Martin");
                ArrayList<Student> studentList = new ArrayList<>();
                Collections.shuffle(firstNameList);
                Collections.shuffle(lastNameList);
                int size = firstNameList.size();
                for (int i = 0; i < numStudents; i++) {
                    if (i%size == 0) {
                        Collections.shuffle(firstNameList);
                        Collections.shuffle(lastNameList);
                    }
                    studentList.add(new Student(empIdList.get(i),firstNameList.get(i%size), lastNameList.get(i%size),
                            firstNameList.get(i%size) + "_"+ lastNameList.get(i%size) + "@myemail.com",
                            myGender.values()[new Random().nextInt(myGender.values().length)]));
                }

                //Create populate Table Students.Students
                nameTable = "Students.Students";
                StringBuilder sqlStudentsQuery = new StringBuilder();
                sqlStudentsQuery.append("INSERT INTO " + nameTable + " (empId, firstName, lastName, email, gender) VALUES ");
                String glue = "";
                for (int i = 0; i < numStudents; i++) {
                    sqlStudentsQuery.append(glue);
                    sqlStudentsQuery.append("('");
                    sqlStudentsQuery.append(studentList.get(i).getEmpId().replace(",", "''"));
                    sqlStudentsQuery.append("', '");
                    sqlStudentsQuery.append(studentList.get(i).getFirstName().replace(",", "''"));
                    sqlStudentsQuery.append("', '");
                    sqlStudentsQuery.append(studentList.get(i).getLastName().replace(",", "''"));
                    sqlStudentsQuery.append("', '");
                    sqlStudentsQuery.append(studentList.get(i).getEmail().replace(",", "''"));
                    sqlStudentsQuery.append("', '");
                    sqlStudentsQuery.append(studentList.get(i).getGender().toString().replace(",", "''"));
                    sqlStudentsQuery.append("')");
                    glue = ", ";
                }

                createTable = StudentsDatabaseInterface.createTableStudents;
                DB.new Students (createTable, nameTable, sqlStudentsQuery.toString());

                // Create ArrayList of Random Classes per student
                int numClasses = 7;
                String numClassesString = String.valueOf(numClasses);
                for (Student student: studentList) {
                    String sqlString =
                            "SELECT DISTINCT courseId, sectionNumber,year,semester " +
                                    "FROM Students.Schedule " +
                                    //"WHERE program = \'Undergraduate\' " +
                                    "ORDER BY RAND() " +
                                    "LIMIT " + numClassesString;
                    PreparedStatement pStatement = connection.prepareStatement(sqlString);
                    ResultSet resultset = pStatement.executeQuery(sqlString);
                    ArrayList<String> classes = new ArrayList<>();
                    ArrayList<String> sectionNumbers = new ArrayList<>();
                    ArrayList<String> classYears = new ArrayList<>();
                    ArrayList<String> classSemester = new ArrayList<>();
                    ArrayList<String> classGrades = new ArrayList<>();
                    while (resultset.next()) {
                        classes.add(resultset.getString("courseId"));
                        sectionNumbers.add(resultset.getString("sectionNumber"));
                        classYears.add(resultset.getString("year"));
                        classSemester.add(resultset.getString("semester"));
                        classGrades.add(myGrade.values()[new Random().nextInt(myGrade.values().length)].toString());
                    }
                    student.setClasses(classes);
                    student.setStudentGrades(classGrades);
                    student.setStudentYears(classYears);
                    student.setStudentSemester(classSemester);
                    student.setStudentSectionNumbers(sectionNumbers);
                }

                createTable = StudentsDatabaseInterface.createTableClasses;
                nameTable = "Students.Classes";
                String classesQuery = "INSERT INTO " + nameTable + " (empId, courseId, sectionNumber, year, semester, grade) VALUES (?,?,?,?,?,?)";
                DB.new Classes(createTable,nameTable);
                PreparedStatement pClassesStatement = connection.prepareStatement(classesQuery);
                for (Student student: studentList) {
                    for (int i = 0; i < numClasses; i ++) {
                        pClassesStatement.setString(1, student.getEmpId());
                        pClassesStatement.setString(2, student.getStudentClasses().get(i));
                        pClassesStatement.setString(3, student.getStudentSectionNumbers().get(i));
                        pClassesStatement.setString(4, student.getStudentYears().get(i));
                        pClassesStatement.setString(5, student.getStudentSemester().get(i));
                        pClassesStatement.setString(6, student.getStudentGrades().get(i));
                        pClassesStatement.addBatch();
                    }
                    pClassesStatement.executeBatch();
                }

                // Create populate Students.AggregateGrades for all Classes
                createTable = "CREATE TABLE Students.AggregateGradesAllClasses ( grade CHAR, num_students INT);";
                nameTable = "Students.AggregateGradesAllClasses";
                DB.new AggregateGrades(createTable,nameTable);

                //Create populate Students.AggregateGradesCSC22100
                String sqlAggregateGradesCSCQuery = "CREATE TABLE Students.AggregateGradesCSC22100 (grade CHAR, num_students INT);";
                PreparedStatement pAggregateGradesCSC = connection.prepareStatement(sqlAggregateGradesCSCQuery);
                try {
                    pAggregateGradesCSC.executeUpdate();
                    System.out.println ("\nTable created successfully: Students.AggregateGradesCSC22100!");
                }
                catch (SQLException e) {
                    System.out.println("\n Error in createTable: Students.AggregateGradesCSC22100!");
                    System.out.println(e);
                }

                String populateAggregateCSC = "INSERT INTO  Students.AggregateGradesCSC22100 (grade, num_students) " +
                        "SELECT grade, count(grade) " +
                        "FROM Students.Classes " +
                        "WHERE courseId = \"22100 R\" OR courseId = \"22100 F\" OR courseId = \"22100 P\" " +
                        "GROUP BY grade;";
                PreparedStatement pPopulateAggregateCSC = connection.prepareStatement(populateAggregateCSC);
                try {
                    pPopulateAggregateCSC.executeUpdate();
                    System.out.println("\nTable populated successfully: CSC22100Grades");
                }
                catch (SQLException e) {
                    System.out.println("\n Error in populate Table: CSC22100Grades");
                    System.out.println(e);
                }

                //Get map of Aggregate Grades for CSC22100
                Map<Character, Integer> AggregateGradesCSC22100 = DB.getAggregateGrades("Students.AggregateGradesCSC22100");
                System.out.println("\nAGGREGATE GRADES FOR CSC22100 ALL SECTIONS");
                for (Map.Entry<Character, Integer> entry : AggregateGradesCSC22100.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue().toString());
                }
                AggregateGrades = AggregateGradesCSC22100;
                launch();
        }

}