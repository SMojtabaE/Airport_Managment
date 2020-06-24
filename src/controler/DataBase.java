package controler;
import model.Employee;
import model.Manager;
import model.Passenger;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DataBase {
    private static Connection connection;
    private static Statement statement;

    private DataBase(){}
    public static void makeconnection(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/airport_managment",
                    "root","9731");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeconection(){
        if (connection != null){
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    ///////////////////////////////////////////////////// // managers database;
    public static int creatmanager(Manager user) throws SQLException {
        makeconnection();  // making connection to database
        statement.execute(String.format("insert into users (name,lastname,username,password,email ,phoneNumber," +
                "adress,salary,job) values ( '%s', '%s', '%s', '%s', '%s', '%s', '%s', %f , %d)",
                user.getName(),user.getLastname(),user.getUsername(),user.getPassword(),user.getEmail()
                ,user.getPhoneNumber(),user.getAdress(),user.getSalary(),user.getJob())
                ,Statement.RETURN_GENERATED_KEYS); //writing into database
        ResultSet rs = statement.getGeneratedKeys(); // returning the id of user
        rs.next();
        int id = rs.getInt(1);


        //creating report
        DateTimeFormatter ftime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time1 = LocalTime.now();
        String time = time1.format(ftime);
        String date = String.valueOf(LocalDate.now());
        String report = "manager " + user.getUsername() + " created.";
        statement.execute(String.format("insert into reports (report,Time,Date) values ('%s','%s','%s')",
                report,time,date));
        ////
        closeconection(); // closing the connection
        return id;
    }

    public static void updatemanager(Manager user) throws SQLException {
        makeconnection();
        statement.execute(String.format("update users set name = '%s', lastname = '%s', username = '%s'," +
                " password = '%S', email = '%s', phoneNumber = '%s', adress = '%s', salary = %f where id = %d"
               ,user.getName(),user.getLastname(),user.getUsername(),user.getPassword(),user.getEmail()
                ,user.getPhoneNumber(),user.getAdress(),user.getSalary(),user.getId()));
        /////
        DateTimeFormatter ftime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time1 = LocalTime.now();
        String time = time1.format(ftime);
        String date = String.valueOf(LocalDate.now());
        String report = "user " + user.getUsername() + " updated.";
        statement.execute(String.format("insert into reports (report,Time,Date) values ('%s','%s','%s')",
                report,time,date));
        closeconection();
    }

    public static void deletmanager(Manager user) throws SQLException {
        makeconnection();
        statement.execute(String.format("delete from users where id = %d",user.getId()));
        /////////////////////
        DateTimeFormatter ftime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time1 = LocalTime.now();
        String time = time1.format(ftime);
        String date = String.valueOf(LocalDate.now());
        String report = "user " + user.getUsername() + " deleted.";
        statement.execute(String.format("insert into reports (report,Time,Date) values ('%s','%s','%s')",
                report,time,date));
        closeconection();
    }

    public static ArrayList<Manager> getmanagers() throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery("select * from users");
        ArrayList<Manager> users = new ArrayList<>();
        while (re.next()){
            if (re.getInt(10)==1) {
                users.add(new Manager(re.getInt(1), re.getString(2), re.getString(3),
                        re.getString(4), re.getString(5), re.getString(6),
                        re.getString(7), re.getString(8), re.getInt(9),
                        re.getInt(10)));
            }
        }
        closeconection();
        return users;
    }

    //////////////////////////////////// // employees database

    public static int createmployee(Employee user) throws SQLException {
        makeconnection();  // making connection to database
        statement.execute(String.format("insert into users (name,lastname,username,password,email ,phoneNumber," +
                        "adress,salary,job) values ( '%s', '%s', '%s', '%s', '%s', '%s', '%s', %f , %d)",
                user.getName(),user.getLastname(),user.getUsername(),user.getPassword(),user.getEmail()
                ,user.getPhoneNumber(),user.getAdress(),user.getSalary(),user.getJob())
                ,Statement.RETURN_GENERATED_KEYS); //writing into database
        ResultSet rs = statement.getGeneratedKeys(); // returning the id of user
        rs.next();
        int id = rs.getInt(1);

        //creating report
        DateTimeFormatter ftime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time1 = LocalTime.now();
        String time = time1.format(ftime);
        String date = String.valueOf(LocalDate.now());
        String report = "employee " + user.getUsername() + " created.";
        statement.execute(String.format("insert into reports (report,Time,Date) values ('%s','%s','%s')",
                report,time,date));
        ////
        closeconection(); // closing the connection
        return id;
    }

    public static void updatemployee(Employee emplo) throws SQLException {
        makeconnection();
        statement.execute(String.format("update users set name = '%s', lastname = '%s', username = '%s'," +
                        " password = '%S', email = '%s', phoneNumber = '%s', adress = '%s', salary = %f where id = %d"
                ,emplo.getName(),emplo.getLastname(),emplo.getUsername(),emplo.getPassword(),emplo.getEmail()
                ,emplo.getPhoneNumber(),emplo.getAdress(),emplo.getSalary(),emplo.getId()));
        /////
        DateTimeFormatter ftime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time1 = LocalTime.now();
        String time = time1.format(ftime);
        String date = String.valueOf(LocalDate.now());
        String report = "employy " + emplo.getUsername() + " updated.";
        statement.execute(String.format("insert into reports (report,Time,Date) values ('%s','%s','%s')",
                report,time,date));
        closeconection();
    }

    public static void deletmemployee(Employee user) throws SQLException {
        makeconnection();
        statement.execute(String.format("delete from users where id = %d",user.getId()));
        /////////////////////
        DateTimeFormatter ftime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time1 = LocalTime.now();
        String time = time1.format(ftime);
        String date = String.valueOf(LocalDate.now());
        String report = "employee " + user.getUsername() + " deleted.";
        statement.execute(String.format("insert into reports (report,Time,Date) values ('%s','%s','%s')",
                report,time,date));
        closeconection();
    }

    public static ArrayList<Employee> getemployees() throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery("select * from users");
        ArrayList<Employee> users = new ArrayList<>();
        while (re.next()){
            if (re.getInt(10)==2) {
                users.add(new Employee(re.getInt(1), re.getString(2), re.getString(3),
                        re.getString(4), re.getString(5), re.getString(6),
                        re.getString(7), re.getString(8), re.getInt(9),
                        re.getInt(10)));
            }
        }
        closeconection();
        return users;
    }

    /////////////////////////  passengar database

    public static int creatpassenger(Passenger user) throws SQLException {
        makeconnection();  // making connection to database
        statement.execute(String.format("insert into passengers (name,lastname,username,password,email ,phoneNumber,mony)" +
                        " values ( '%s', '%s', '%s', '%s', '%s', '%s', %f)",
                user.getName(),user.getLastname(),user.getUsername(),user.getPassword(),user.getEmail()
                ,user.getPhoneNumber(),user.getMoney())
                ,Statement.RETURN_GENERATED_KEYS); //writing into database
        ResultSet rs = statement.getGeneratedKeys(); // returning the id of user
        rs.next();
        int id = rs.getInt(1);

        //creating report
        DateTimeFormatter ftime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time1 = LocalTime.now();
        String time = time1.format(ftime);
        String date = String.valueOf(LocalDate.now());
        String report = "passenger " + user.getUsername() + " created.";
        statement.execute(String.format("insert into reports (report,Time,Date) values ('%s','%s','%s')",
                report,time,date));
        ////
        closeconection(); // closing the connection
        return id;
    }

    public static void updatpassenger(Passenger user) throws SQLException {
        makeconnection();
        statement.execute(String.format("update passengers set name = '%s', lastname = '%s', username = '%s'," +
                        " password = '%S', email = '%s', phoneNumber = '%s', mony = '%f', where id = %d"
                ,user.getName(),user.getLastname(),user.getUsername(),user.getPassword(),user.getEmail()
                ,user.getPhoneNumber(),user.getMoney(),user.getId()));
        /////
        DateTimeFormatter ftime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time1 = LocalTime.now();
        String time = time1.format(ftime);
        String date = String.valueOf(LocalDate.now());
        String report = "passenger " + user.getUsername() + " updated.";
        statement.execute(String.format("insert into reports (report,Time,Date) values ('%s','%s','%s')",
                report,time,date));
        closeconection();
    }

    public static void deletpassenger(Passenger user) throws SQLException {
        makeconnection();
        statement.execute(String.format("delete from passengers where id = %d",user.getId()));
        /////////////////////
        DateTimeFormatter ftime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time1 = LocalTime.now();
        String time = time1.format(ftime);
        String date = String.valueOf(LocalDate.now());
        String report = "passenger " + user.getUsername() + " deleted.";
        statement.execute(String.format("insert into reports (report,Time,Date) values ('%s','%s','%s')",
                report,time,date));
        closeconection();
    }

    public static ArrayList<Passenger> getpassengers() throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery("select * from passengers");
        ArrayList<Passenger> users = new ArrayList<>();
        while (re.next()){
                users.add(new Passenger(re.getInt(1), re.getString(2), re.getString(3),
                        re.getString(4), re.getString(5), re.getString(6),
                        re.getString(7), re.getInt(8)));
        }
        closeconection();
        return users;
    }


}
