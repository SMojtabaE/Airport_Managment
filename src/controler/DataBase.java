package controler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.*;
import java.text.SimpleDateFormat;
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
                        re.getString(7), re.getString(8), re.getDouble(9),
                        re.getInt(10),re.getString(11));
                closeconection();
                return superadmin;
            }
        closeconection();
         return superadmin;
    }
    public static void report(String massage){
        /*this method get a string that is the massage of report and than insert it to the database,these massage
        * could be logging in a user or anything else
        *  */
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
        user.setProfile_photo_Path(user.getProfile_photo_Path().replace("\\","\\\\"));
        statement.execute(String.format("insert into users (name,lastname,username,password,email ,phoneNumber," +
                "adress,salary,job,photo) values ( '%s', '%s', '%s', '%s', '%s', '%s', '%s', %f , %d,'%s')",
                user.getName(),user.getLastname(),user.getUsername(),user.getPassword(),user.getEmail()
                ,user.getPhoneNumber(),user.getAdress(),user.getSalary(),user.getJob(),user.getProfile_photo_Path())
                ,Statement.RETURN_GENERATED_KEYS); //writing into database
        ResultSet rs = statement.getGeneratedKeys(); // returning the id of user
        rs.next();
        int id = rs.getInt(1);

          report("manager " + user.getUsername() + " created.");
        closeconection(); // closing the connection
        return id;
    }

    public static void updatemanager(Manager user) throws SQLException {
        makeconnection();
        user.setProfile_photo_Path(user.getProfile_photo_Path().replace("\\","\\\\"));
        statement.execute(String.format("update users set name = '%s', lastname = '%s', username = '%s'," +
                " password = '%S', email = '%s', phoneNumber = '%s', adress = '%s', salary = %f, photo = '%s' where id = %d"
               ,user.getName(),user.getLastname(),user.getUsername(),user.getPassword(),user.getEmail()
                ,user.getPhoneNumber(),user.getAdress(),user.getSalary(),user.getProfile_photo_Path(),user.getId()));

        report("user " + user.getUsername() + " updated.");
        closeconection();
    }

    public static void deletmanager(Manager user) throws SQLException {
        makeconnection();
        statement.execute(String.format("delete from users where id = %d",user.getId()));

        report("user " + user.getUsername() + " deleted.");
        closeconection();
    }

    public static ObservableList<Manager> getmanagers() throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery("select * from users where job like 1");
        ObservableList<Manager> users = FXCollections.observableArrayList();
        while (re.next()){
                users.add(new Manager(re.getInt(1), re.getString(2), re.getString(3),
                        re.getString(4), re.getString(5), re.getString(6),
                        re.getString(7), re.getString(8), re.getDouble(9),
                        re.getInt(10),re.getString(11)));
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
                        re.getString(7), re.getString(8), re.getDouble(9),
                        re.getInt(10),re.getString(11));
                closeconection();
                return user;
            }
        }
       // System.out.println(" null");
        closeconection();
        return user;
    }

    public static Manager searchFormanager(int id) throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery(String.format("select * from users where id like %d and job = 1",id));
        Manager user = null;
        if(re.next()){
            user = new Manager(re.getInt(1), re.getString(2), re.getString(3),
                    re.getString(4), re.getString(5), re.getString(6),
                    re.getString(7), re.getString(8), re.getDouble(9),
                    re.getInt(10),re.getString(11));
            closeconection();
            return user;
        }
        closeconection();
        return user;
    }

    public static boolean checkregisrerOfmanager(String username) throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery(String.format("select * from users where username like '%s' and job = 1",username));
        if(re.next()){
            closeconection();
            return false;
        }
        closeconection();
        return true;
    }



    //////////////////////////////////// // employees database

    public static int createmployee(Employee user) throws SQLException {
        makeconnection();  // making connection to database
        user.setProfile_photo_Path(user.getProfile_photo_Path().replace("\\","\\\\"));
        statement.execute(String.format("insert into users (name,lastname,username,password,email ,phoneNumber," +
                        "adress,salary,job,photo) values ( '%s', '%s', '%s', '%s', '%s', '%s', '%s', %f , %d,'%s')",
                user.getName(),user.getLastname(),user.getUsername(),user.getPassword(),user.getEmail()
                ,user.getPhoneNumber(),user.getAdress(),user.getSalary(),user.getJob(),user.getProfile_photo_Path())
                ,Statement.RETURN_GENERATED_KEYS); //writing into database
        ResultSet rs = statement.getGeneratedKeys(); // returning the id of user
        rs.next();
        int id = rs.getInt(1);

        report("employee " + user.getUsername() + " created.");
        closeconection(); // closing the connection
        return id;
    }

    public static void updatemployee(Employee emplo) throws SQLException {
        makeconnection();
        emplo.setProfile_photo_Path(emplo.getProfile_photo_Path().replace("\\","\\\\"));
        statement.execute(String.format("update users set name = '%s', lastname = '%s', username = '%s'," +
                        " password = '%S', email = '%s', phoneNumber = '%s', adress = '%s', salary = %f, photo = '%s' where id = %d"
                ,emplo.getName(),emplo.getLastname(),emplo.getUsername(),emplo.getPassword(),emplo.getEmail()
                ,emplo.getPhoneNumber(),emplo.getAdress(),emplo.getSalary(),emplo.getProfile_photo_Path(),emplo.getId()));

        report( "employee " + emplo.getUsername() + " updated.");

        closeconection();
    }

    public static void deletmemployee(Employee user) throws SQLException {
        makeconnection();
        statement.execute(String.format("delete from users where id = %d",user.getId()));

        report("employee " + user.getUsername() + " deleted.");
        closeconection();
    }

    public static ObservableList<Employee> getemployees() throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery("select * from users where job like 2");
        ObservableList<Employee> users = FXCollections.observableArrayList();
        while (re.next()){
                users.add(new Employee(re.getInt(1), re.getString(2), re.getString(3),
                        re.getString(4), re.getString(5), re.getString(6),
                        re.getString(7), re.getString(8), re.getDouble(9),
                        re.getInt(10),re.getString(11)));

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
                        re.getString(7), re.getString(8), re.getDouble(9),
                        re.getInt(10),re.getString(11));
                closeconection();
                return user;
            }
        }
        closeconection();
        return user;
    }

    public static Employee searchForemployee(int id) throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery(String.format("select * from users where id like %d and job = 2",id));
        Employee user = null;
        if(re.next()){
            user = new Employee(re.getInt(1), re.getString(2), re.getString(3),
                    re.getString(4), re.getString(5), re.getString(6),
                    re.getString(7), re.getString(8), re.getDouble(9),
                    re.getInt(10),re.getString(11));
            closeconection();
            return user;
        }
        closeconection();
        return user;
    }

    public static boolean checkregisrerOfemoloyees(String username) throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery(String.format("select * from users where username like '%s' and job = 2",username));
        if(re.next()){
            closeconection();
            return false;
        }
        closeconection();
        return true;
    }

    /////////////////////////  passengar database

    public static int creatpassenger(Passenger user) throws SQLException {
        makeconnection();  // making connection to database
        user.setProfile_photo_Path(user.getProfile_photo_Path().replace("\\","\\\\"));
        statement.execute(String.format("insert into passengers (name,lastname,username,password,email ,phoneNumber,mony,photo)" +
                        " values ( '%s', '%s', '%s', '%s', '%s', '%s', %f,'%s')",
                user.getName(),user.getLastname(),user.getUsername(),user.getPassword(),user.getEmail()
                ,user.getPhoneNumber(),user.getMoney(),user.getProfile_photo_Path())
                ,Statement.RETURN_GENERATED_KEYS); //writing into database
        ResultSet rs = statement.getGeneratedKeys(); // returning the id of user
        rs.next();
        int id = rs.getInt(1);
          report("passenger " + user.getUsername() + " created.");
        closeconection(); // closing the connection
        return id;
    }

    public static void updatpassenger(Passenger user) throws SQLException {
        makeconnection();
        user.setProfile_photo_Path(user.getProfile_photo_Path().replace("\\","\\\\"));
        statement.execute(String.format("update passengers set name = '%s', lastname = '%s', username = '%s'," +
                        " password = '%S', email = '%s', phoneNumber = '%s', mony = '%f', photo = '%s' where id = %d"
                ,user.getName(),user.getLastname(),user.getUsername(),user.getPassword(),user.getEmail()
                ,user.getPhoneNumber(),user.getMoney(),user.getProfile_photo_Path(),user.getId()));
         report( "passenger " + user.getUsername() + " updated.");

        closeconection();
    }

    public static void deletpassenger(Passenger user) throws SQLException {
        makeconnection();
        statement.execute(String.format("delete from passengers where id = %d",user.getId()));
        report( "passenger " + user.getUsername() + " deleted.");
        closeconection();
    }

    public static ObservableList<Passenger> getpassengers() throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery("select * from passengers");

        ObservableList<Passenger> users = FXCollections.observableArrayList();
        while (re.next()){
                users.add(new Passenger(re.getInt(1), re.getString(2), re.getString(3),
                        re.getString(4), re.getString(5), re.getString(6),
                        re.getString(7), re.getDouble(8),re.getString(9)));
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
        ResultSet re = statement.executeQuery(String.format("select * from passengers where username like '%s'",username));
        if(re.next()){
            if (password.equals(re.getString(5))){
                passenger = new Passenger(re.getInt(1), re.getString(2), re.getString(3),
                        re.getString(4), re.getString(5), re.getString(6),
                        re.getString(7), re.getDouble(8),re.getString(9));
                closeconection();
                return passenger;
            }
        }
        closeconection();
        return passenger;
    }
    public static boolean checkregisrerOfpassenger(String username) throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery(String.format("select * from passengers where username like '%s'",username));
        if(re.next()){
            closeconection();
            return false;
        }
        closeconection();
        return true;
    }
    public static Passenger searchFrompassenger(int id) throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery(String.format("select * from passengers where id like %d", id));
        Passenger passenger = null;
        if (re.next()) {
            passenger = new Passenger(re.getInt(1), re.getString(2), re.getString(3),
                    re.getString(4), re.getString(5), re.getString(6),
                    re.getString(7), re.getDouble(8), re.getString(9));
            closeconection();
            return passenger;
        }
        closeconection();
        return passenger;
    }
    ///////////////////////////////////////////////////////////// ticket database

    public static int createticket(Ticket ticket) throws SQLException {
        makeconnection();  // making connection to database
        statement.execute(String.format("insert into ticket (price,loss,flight_id,passengers_id) values ( '%f', '%f'," +
                        " '%d', '%d')",ticket.getPrice(),ticket.getLoss(),ticket.getFlight_id(),ticket.getPassengers_id())
                ,Statement.RETURN_GENERATED_KEYS); //writing into database
        ResultSet rs = statement.getGeneratedKeys(); // returning the id of user
        rs.next();
        ticket.setId(rs.getInt(1));
        report("ticket " + ticket.getId() + " created.");
        closeconection(); // closing the connection
        return ticket.getId();
    }

    public static void updatticket(Ticket ticket) throws SQLException {
        makeconnection();
        statement.execute(String.format("update ticket set price = %f, loss = %f, flight_id = %d," +
                        " passengers_id = %d where id = %d",ticket.getPrice(),ticket.getLoss(),ticket.getFlight_id(),ticket.getPassengers_id(),ticket.getId()));
        report( "ticket " + ticket.getId() + " updated.");

        closeconection();
    }

    public static void deletticket(Ticket ticket) throws SQLException {
        makeconnection();
        statement.execute(String.format("delete from ticket where id = %d",ticket.getId()));
        report( "ticket " + ticket.getId() + " deleted.");
        closeconection();
    }

    public static ObservableList<Ticket> gettickets() throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery("select * from ticket");

        ObservableList<Ticket> tickets = FXCollections.observableArrayList();
        while (re.next()){
            tickets.add(new Ticket(re.getInt(1), re.getDouble(2), re.getDouble(3),
                    re.getInt(4), re.getInt(5)));
        }
        closeconection();
        return tickets;
    }

    public static ObservableList<Ticket> searchflight_idIntickets(int flight_id) throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery(String.format("select * from ticket where flight_id like %d", flight_id));
        ObservableList<Ticket> tickets = FXCollections.observableArrayList();
        if (re.next()) {
              tickets.add(new Ticket(re.getInt(1), re.getDouble(2), re.getDouble(3),
                    re.getInt(4), re.getInt(5)));
            closeconection();
            return tickets;
        }
        closeconection();
        return tickets;
    }
    public static ObservableList<Ticket> searchpassenger_idIntickets(int passenger_id) throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery(String.format("select * from ticket where passengers_id like %d", passenger_id));
        ObservableList<Ticket> tickets = FXCollections.observableArrayList();
        if (re.next()) {
            tickets.add(new Ticket(re.getInt(1), re.getDouble(2), re.getDouble(3),
                    re.getInt(4), re.getInt(5)));
            closeconection();
            return tickets;
        }
        closeconection();
        return tickets;
    }
//////////////////////////////////////////////////////////////////////////////// Airplains database

    public static int createAirplane(Airplane airplane) throws SQLException {
        makeconnection();  // making connection to database
        statement.execute(String.format("insert into airplane (seats) values (%d)",airplane.getSeats())
                ,Statement.RETURN_GENERATED_KEYS); //writing into database
        //ResultSet rs = statement.getGeneratedKeys(); // returning the id of user
        //rs.next();
       // airplane.setId(rs.getInt(1));
        ResultSet re = statement.getGeneratedKeys();
        re.next();
        airplane.setId(re.getInt(1));
        report("Airplane " + airplane.getId() + " created.");
        closeconection(); // closing the connection
        return airplane.getId();
    }

    public static void updateairplane(Airplane airplane) throws SQLException {
        makeconnection();
        statement.execute(String.format("update airplane set seats = %d where id = %d",airplane.getSeats(),
                airplane.getId()));
        report( "Airplane " + airplane.getId() + " updated.");

        closeconection();
    }

    public static void deletairplane(Airplane airplane) throws SQLException {
        makeconnection();
        statement.execute(String.format("delete from airplane where id = %d",airplane.getId()));
        report( "Airplane " + airplane.getId() + " deleted.");
        closeconection();
    }

    public static ObservableList<Airplane> getAirplanes() throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery("select * from airplane");

        ObservableList<Airplane> airplanes = FXCollections.observableArrayList();
        while (re.next()){
            airplanes.add(new Airplane(re.getInt(1), re.getInt(2)));
        }
        closeconection();
        return airplanes;
    }

///////////////////////////////////////////////////////////////flight daatabase

    public static int createflight(Flight flight) throws SQLException {
        makeconnection();  // making connection to database
        statement.execute(String.format("insert into flight (airplane_id,origin,destination,date,time,sold_ticket,longs" +
                        ",status) values ( '%d', '%s', '%s', '%tF','%tT','%d','%s','%s')",flight.getAirplaine_id(),
                flight.getOrigin(),flight.getDestination(),flight.getDate(),flight.getTime(),flight.getSold_tickets(),
                flight.getLongs(),flight.getStatus()),Statement.RETURN_GENERATED_KEYS); //writing into database
        ResultSet rs = statement.getGeneratedKeys(); // returning the id of user
        rs.next();
        flight.setId(rs.getInt(1));
        report("flight " + flight.getId() + " created.");
        closeconection(); // closing the connection
        return flight.getId();
    }

    public static void updateflight(Flight flight) throws SQLException {
        makeconnection();
      //LocalDate ld = LocalDate.parse(new SimpleDateFormat("yyyy-mm-dd").format(flight.getDate()));
      Date ld = Date.valueOf(flight.getDate());
        statement.execute(String.format("update flight set airplane_id = %d, origin = '%s', destination = '%s'," +
                        " date = '%tF', time = '%tT', sold_ticket = %d, longs = '%s', status = '%s' where id = %d"
                ,flight.getAirplaine_id(),flight.getOrigin(),flight.getDestination()
                ,ld,flight.getTime(),flight.getSold_tickets(),flight.getLongs(),flight.getStatus(),flight.getId()));
        report( "flight " + flight.getId() + " updated.");
        closeconection();
    }

    public static void deleteflight(Flight fligjt) throws SQLException {
        makeconnection();
        statement.execute(String.format("delete from flight where id = %d",fligjt.getId()));
        report( "Flight " + fligjt.getId() + " deleted.");
        closeconection();
    }

    public static ObservableList<Flight> getflights() throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery("select * from flight");

        ObservableList<Flight> flights = FXCollections.observableArrayList();
        while (re.next()){
            String date = String.valueOf(re.getDate(5));
            String[] dt = date.split("-");
            int[] dtn = new int[3];
            for (int i = 0 ; i < dtn.length ; i++){
                dtn[i] = Integer.parseInt(dt[i]);
            }
            LocalDate ldate = LocalDate.of(dtn[0],dtn[1],dtn[2]);
            String time = String.valueOf(re.getTime(6));
            flights.add(new Flight(re.getInt(1), re.getInt(2), re.getString(3),
                    re.getString(4), ldate, time, re.getInt(7), re.getString(8),
                    Status.valueOf(re.getString(9))));
        }
        closeconection();
        return flights;
    }

    public static ObservableList<Flight> searchidInflights(int flight_id) throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery(String.format("select * from flight where id like %d", flight_id));
        ObservableList<Flight> flights = FXCollections.observableArrayList();
        if (re.next()) {
            String date = String.valueOf(re.getDate(5));
            String[] dt = date.split("-");
            int[] dtn = new int[3];
            for (int i = 0 ; i < dtn.length ; i++){
                dtn[i] = Integer.parseInt(dt[i]);
            }
            LocalDate ldate = LocalDate.of(dtn[0],dtn[1],dtn[2]);
            String time = String.valueOf(re.getTime(6));
            flights.add(new Flight(re.getInt(1), re.getInt(2), re.getString(3),
                    re.getString(4), ldate, time, re.getInt(7), re.getString(8),
                    Status.valueOf(re.getString(9))));
            closeconection();
            return flights;
        }
        closeconection();
        return flights;
    }
    public static ObservableList<Flight> searchairplane_idIntickets(int airplaine_id) throws SQLException {
        makeconnection();
        ResultSet re = statement.executeQuery(String.format("select * from flight where airplane_id like %d", airplaine_id));
        ObservableList<Flight> flights = FXCollections.observableArrayList();
        if (re.next()) {
            String date = String.valueOf(re.getDate(5));
            String[] dt = date.split("-");
            int[] dtn = new int[3];
            for (int i = 0 ; i < dtn.length ; i++){
                dtn[i] = Integer.parseInt(dt[i]);
            }
            LocalDate ldate = LocalDate.of(dtn[0],dtn[1],dtn[2]);
            String time = String.valueOf(re.getTime(6));
            flights.add(new Flight(re.getInt(1), re.getInt(2), re.getString(3),
                    re.getString(4), ldate, time, re.getInt(7), re.getString(8),
                    Status.valueOf(re.getString(9))));
            closeconection();
            return flights;
        }
        closeconection();
        return flights;
    }

}
