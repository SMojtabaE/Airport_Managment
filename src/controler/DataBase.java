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
    public static Manager getsuperadmin() throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery("select * from users where job like 0");
        Manager superadmin = null;
         if(re.next()){
                superadmin = new Manager(re.getInt(1), re.getString(2), re.getString(3),
                        re.getString(4), re.getString(5), re.getString(6),
                        re.getString(7), re.getString(8), re.getInt(9),
                        re.getInt(10));
                closeconection();
                return superadmin;
            }
        closeconection();
         return superadmin;
    }
    public static void report(String massage){
        /*this method get a string that is the massage of report and than insert it to the database,these massage
        * could be logging in a user or anything else */
        makeconnection();
        DateTimeFormatter ftime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time1 = LocalTime.now();
        String time = time1.format(ftime);
        String date = String.valueOf(LocalDate.now());
        try {
            statement.execute(String.format("insert into reports (report,Time,Date) values ('%s','%s','%s')",
                    massage,time,date));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeconection();
    }

    public static String resetpasswordusers(String email) throws SQLException {
        makeconnection();
        String password = null;
        ResultSet re = statement.executeQuery(String.format("select * from users where email like '%s'",email));
        if(re.next()){
            password = re.getString(5);
            closeconection();
            return password;
        }
        closeconection();
        return password;
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
        ResultSet re = statement.executeQuery("select * from users where job like 1");
        ArrayList<Manager> users = new ArrayList<>();
        while (re.next()){
                users.add(new Manager(re.getInt(1), re.getString(2), re.getString(3),
                        re.getString(4), re.getString(5), re.getString(6),
                        re.getString(7), re.getString(8), re.getInt(9),
                        re.getInt(10)));
        }
        closeconection();
        return users;
    }

    public static Manager checkusernamOfmanager(String username,String password) throws SQLException {
        makeconnection();
        Manager user = null;
       // System.out.println("user = null");
        ResultSet re = statement.executeQuery(String.format("select * from users where username like '%s' and job like 1",username));
       // System.out.println("user name from database " + re.getString(4));

        if(re.next()){
            if (password.equals(re.getString(5))){
                user = new Manager(re.getInt(1), re.getString(2), re.getString(3),
                        re.getString(4), re.getString(5), re.getString(6),
                        re.getString(7), re.getString(8), re.getFloat(9),
                        re.getInt(10));
                closeconection();
                return user;
            }
        }
       // System.out.println(" null");
        closeconection();
        return user;
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
        ResultSet re = statement.executeQuery("select * from users where job like 2");
        ArrayList<Employee> users = new ArrayList<>();
        while (re.next()){
                users.add(new Employee(re.getInt(1), re.getString(2), re.getString(3),
                        re.getString(4), re.getString(5), re.getString(6),
                        re.getString(7), re.getString(8), re.getInt(9),
                        re.getInt(10)));

        }
        closeconection();
        return users;
    }
    public static Employee checkusernamOfemployees(String username,String password) throws SQLException {
        makeconnection();
        Employee user = null;
        ResultSet re = statement.executeQuery(String.format("select * from users where username like '%s' and job like 2",username));
        if(re.next()){
            if (password.equals(re.getString(5))){
                user = new Employee(re.getInt(1), re.getString(2), re.getString(3),
                        re.getString(4), re.getString(5), re.getString(6),
                        re.getString(7), re.getString(8), re.getFloat(9),
                        re.getInt(10));
                closeconection();
                return user;
            }
        }
        closeconection();
        return user;
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

    public static String resetpasswordPassengers(String email) throws SQLException {
        makeconnection();
        String password = null;
        ResultSet re = statement.executeQuery(String.format("select * from passengers where email like '%s'",email));
        if(re.next()){
            password = re.getString(5);
            closeconection();
            return password;
        }
        closeconection();
        return password;
    }

    public static Passenger checkusernamOfpassengers(String username, String password) throws SQLException {
        makeconnection();
        Passenger passenger = null;
        // System.out.println("user = null");
        ResultSet re = statement.executeQuery(String.format("select * from passengers where username like '%s'",username));
        // System.out.println("user name from database " + re.getString(4));
        if(re.next()){
            if (password.equals(re.getString(5))){
                passenger = new Passenger(re.getInt(1), re.getString(2), re.getString(3),
                        re.getString(4), re.getString(5), re.getString(6),
                        re.getString(7), re.getInt(8));
                closeconection();
                return passenger;
            }
        }
        closeconection();
        return passenger;
    }


}
