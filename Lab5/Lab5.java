/**
 * Created by ericks80 on 7/26/18.
 
 
    Prompt the user for their student ID.
    Use their ID to query their transcript using Dynamic SQL. The transcript should include the course number, course title, semester, year, grade, and credits of every course the Student has taken in chronological order.
        Must use JDBC, not an ORM like Hibernate.
    Display the results of their transcript. The interface can be command line or a GUI.

 */

import java.util.Scanner;
import java.sql.*;

public class Lab5 {

    public static void main(String[] args) throws SQLException {
        Connection connection = connectSql();
        queryTranscript(connection);

    }

    private static Connection connectSql() throws SQLException {
        String connectionString = "jdbc:mysql://mysql.cs.wwu.edu:3306/ericks80?useSSL=false&serverTimezone=UTC";
        String inputId = "ericks80";
        String password = "=vt9dfansV";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionString, inputId, password);
        } catch(SQLException sqle){
            System.out.println(sqle);
        }
        return connection;
    }

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

    private static void queryTranscript(Connection connection) throws SQLException {
        ResultSet result = null;
        String sqlString = "";
        String userId = requestId();

        try {
            Statement statement = connection.createStatement();
            sqlString += ("SELECT A.name, B.course_id, C.title, B.semester, B.year, B.grade, C.credits FROM Student A, Takes B, Course C WHERE (A.id = B.id AND C.course_id = B.course_id AND A.id = '00128');");
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

    }
}


