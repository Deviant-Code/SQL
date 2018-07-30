/**
 * Created by Jesse Ericksen on 7/26/18.
 * CSCI 330 - Western Washington University
 * Summer 2018

This program connects to Western's mysql Student database and prompts the user for a student ID
to return a transcript of that student.

Requires myswl-connector-java-8.0.11.jar

***Prompts for WWU username and password for connecting to database -- or you can choose to manually
type in your username and password for the inputId and password fields on lines 38 and 40

To compile and run on command line in Linux:
- javac -classpath mysql-connector-java-8.0.11.jar:. Lab5.java
- java - classpath mysql-connector-java-8.0.11.jar:. Lab5

This program will hold a connection with the database allowing you to pull as many transcripts as required.
Program will end if connection closes or you exit the program.
 */

import java.util.Scanner;
import java.sql.*;

public class Lab5 {

    public static void main(String[] args) throws SQLException {
        Connection connection = connectSql();
        while(!connection.isClosed()){
            queryTranscript(connection);
        }
    }

    //Creates and returns an ew connection to WWU sql database
    private static Connection connectSql() throws SQLException {
        String connectionString = "jdbc:mysql://mysql.cs.wwu.edu:3306/ericks80?useSSL=false&serverTimezone=UTC";

        System.out.println("Please enter your username to connect to the WWU database");
        String inputId = getString(); // Enter your UserId for the database
        System.out.println("Password: ");
        String password = getString(); // Enter your password for the database

        Connection connection = null;

        try {
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(connectionString, inputId, password);
            System.out.println("Connection successful");
        } catch(SQLException sqle){
            System.out.println(sqle);
        }

        return connection;
    }

    //Can be used to get Database username and Database password for WWU
    private static String getString(){
        Scanner input = new Scanner(System.in);
        return input.next();
    }

    //Requests SID from user input for obtaining a student transcript
    private static String requestId(){
        Scanner input = new Scanner(System.in);
        String userId = "";
        System.out.println("Welcome. To retrieve your transcript enter your student id: ");

        while(userId.length() != 5) {
            userId = input.next();
            if(userId.length() != 5){
                System.out.println("Invalid userId!! Please try again:");
            }
        }
        return userId;
    }

    //Handles database output and formatting transcript to console
    private static void queryTranscript(Connection connection) throws SQLException {
        ResultSet result = null;
        String sqlString = "";
        String userId = requestId();

        try {
            Statement statement = connection.createStatement();
            sqlString += ("SELECT A.name, B.course_id, C.title, B.semester, B.year, B.grade, C.credits FROM Student A, Takes B, Course C WHERE (A.id = B.id AND C.course_id = B.course_id AND A.id = '" + userId + "');");
            result = statement.executeQuery(sqlString);
        } catch(SQLException sqle){
            System.out.println("Error trying to find your transcript: " + sqle);
            queryTranscript(connection);
        }

        int i = 0;
        while(result.next()){
            if(i == 0){
                String name = result.getString("NAME");
                System.out.println("Transcript for " + name);
                System.out.printf("%6s %30s %9s %8s %6s %8s\n", "CourseId", "Title", "Semester", "Year", "Grade", "Credits");
                i++;
            }
            String course_id = result.getString("course_id");
            String title = result.getString("title");
            String semester = result.getString("semester");
            String year = result.getString("year");
            String grade = result.getString("grade");
            String credits = result.getString("credits");
            System.out.printf("%6s   %30s %9s %8s %6s %8s \n", course_id, title,semester, year, grade, credits );
        }
        System.out.println();

    }
}
