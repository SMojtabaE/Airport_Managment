package model;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Flight implements Showable {
    private int id;
    private int airplaine_id;
    private String origin;
    private String destination;
    private LocalDate date;
    private LocalTime time;
    private int sold_tickets;
    private String longs;
    private Status status;

    public Flight(int airplaine_id,String origin,String destination,LocalDate date,String time,int sold_tickets,String longs){
        this.setAirplaine_id(airplaine_id);
        this.setOrigin(origin);
        this.setDestination(destination);
        this.setDate(date);
        this.setTime(LocalTime.parse(time));
        this.setSold_tickets(sold_tickets);
        this.setLongs(longs);
        this.setStatus(Status.open);
    }public Flight(int id ,int airplaine_id,String origin,String destination,LocalDate date,String time,int sold_tickets,String longs,Status status){
        this.setAirplaine_id(airplaine_id);
        this.setOrigin(origin);
        this.setDestination(destination);
        this.setDate(date);
        this.setTime(LocalTime.parse(time));
        this.setSold_tickets(sold_tickets);
        this.setLongs(longs);
        this.setStatus(Status.open);
        this.setStatus(status);
        this.setId(id);
    }

    @Override
    public void show() {
        System.out.println("Id : " + this.getId()+"\n"+
                "origin : "+ this.getOrigin() + "\n"+
                "Sestination : " + this.getDestination() + "\n"+
                "Date : " + this.getDate() + "\n"+
                "time : " + this.getTime() + "\n"+
                "Status : " + this.getStatus() + "\n"+
                "Sold Tickets : " + this.getSold_tickets());
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getAirplaine_id() { return airplaine_id; }

    public void setAirplaine_id(int airplaine_id) { this.airplaine_id = airplaine_id; }

    public String getOrigin() { return origin; }

    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }

    public void setDestination(String destination) { this.destination = destination; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }

    public void setTime(LocalTime time) { this.time = time; }

    public int getSold_tickets() { return sold_tickets; }

    public void setSold_tickets(int sold_tickets) { this.sold_tickets = sold_tickets; }

    public String getLongs() { return longs; }

    public void setLongs(String longs) { this.longs = longs; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }
}
