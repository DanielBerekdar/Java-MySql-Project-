import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Stream;


public class MySqlConnect {

    static  String username = "user"; //username
    static  String password = "123"; //password
    static String schema = "employee"; //change schema here
    static String table = "Employee"; //change table name here
    static String url = "jdbc:mysql://localhost:3306/" + schema; //change url here


    public static void showEmployeesUnder100k(){
        String query = "SELECT Employee_ID, Employee_name, Employee_Salary FROM " + table + " WHERE Employee_Salary < 100000;";
        try {
            Connection connection = DriverManager.getConnection(url, username, password );
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            System.out.println(query);
            while (result.next()){

                int  Employee_ID = result.getInt("Employee_ID");
                String Employee_Name = result.getString("Employee_Name");
                String Employee_Salary = result.getString("Employee_Salary");

                System.out.format("%s, %s, %s \n", Employee_ID, Employee_Name, Employee_Salary);
            }

        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public static void raiseFivePercent() {
        System.out.println();
        String updateQuery = "UPDATE " + table + " " +
                "SET Employee_Salary = Employee_Salary + (Employee_salary*.05)" +
                " WHERE Employee_Salary < 100000;";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            int raise = statement.executeUpdate(updateQuery);
            String selectQuery = "SELECT Employee_ID, Employee_name, Employee_Salary FROM " + table + " WHERE Employee_Salary < 100000;";

            ResultSet result = statement.executeQuery(selectQuery);
            System.out.println(updateQuery);
            while (result.next()) {

                int Employee_ID = result.getInt("Employee_ID");
                String Employee_Name = result.getString("Employee_Name");
                String Employee_Salary = result.getString("Employee_Salary");


                System.out.format("%s, %s, %s \n", Employee_ID, Employee_Name, Employee_Salary);
            }
        }catch(Exception e){
                System.err.println(e);
        }
    }
    public static void averageSalaryAbove150k(){

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            String selectQuery = "SELECT Employee_Salary FROM " + table + " WHERE Employee_Salary > 150000";

            ResultSet result = statement.executeQuery(selectQuery);
            List<Integer> salary = new LinkedList<Integer>();


            while (result.next()) {

                int Employee_Salary = result.getInt("Employee_Salary");
                salary.add(Employee_Salary);
            }
            OptionalDouble average = salary.stream()
                    .mapToDouble(a -> a)
                    .average();
            if (average.isPresent()) {
                System.out.println("The average salary above $150,000 is " + average.getAsDouble());
            }

        }catch(Exception e){
            System.err.println(e);
        }
    }

    public static void downSize(){
        System.out.println();
        String deleteQuery = "DELETE FROM " + table + " " +
                             "WHERE  Employee_Salary > 200000";
        String selectQuery = "SELECT Employee_ID, Employee_name, Employee_Salary FROM " + table + " WHERE Employee_Salary > 200000;";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(selectQuery);
            System.out.println(deleteQuery);
            while (result.next()) {
                int Employee_ID = result.getInt("Employee_ID");
                String Employee_Name = result.getString("Employee_Name");
                String Employee_Salary = result.getString("Employee_Salary");
                System.out.format("%s, %s, %s \n", Employee_ID, Employee_Name, Employee_Salary);
            }
            int removeNum = statement.executeUpdate(deleteQuery);

        }catch(Exception e){
            System.err.println(e);
        }
        System.out.println("The record(s) have been successfully deleted.");


    }


    public static void newHires(){
        System.out.println();
        String insertJohn = "INSERT INTO " + table + " (Employee_ID, Employee_Name, Employee_Salary)  Values (10256, 'John Doe', 124000);";
        String insertFoo = "INSERT INTO " + table + " (Employee_ID, Employee_Name, Employee_Salary)  Values (12443, 'Foo Bar', 88000);";

        System.out.println(insertJohn);

        System.out.println(insertFoo);

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            int updateJohn = statement.executeUpdate(insertJohn);
            int updateFoo = statement.executeUpdate(insertFoo);

            String selectQuery = "SELECT Employee_ID, Employee_name, Employee_Salary FROM " + table +
                    " WHERE Employee_Name = 'John Doe' OR 'Foo Bar'";

            ResultSet result = statement.executeQuery(selectQuery);

            while (result.next()) {

                int Employee_ID = result.getInt("Employee_ID");
                String Employee_Name = result.getString("Employee_Name");
                String Employee_Salary = result.getString("Employee_Salary");
                System.out.format("%s, %s, %s \n", Employee_ID, Employee_Name, Employee_Salary);

            }
        }catch(Exception e){
            System.err.println(e);
        }
        System.out.println("The record(s) have been successfully inserted.");

    }
   //TimeZone error, i went into mysql terminal and typed "SET GLOBAL time_zone = '+3:00';" (no quotes).

    public static void main(String args[]) {

            showEmployeesUnder100k(); // first method.
            raiseFivePercent();     // second method.
            downSize();             // third method.
            averageSalaryAbove150k(); // fourth method.
            newHires();             //fifth method.


    }
}
