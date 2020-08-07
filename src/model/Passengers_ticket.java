package model;

public class Passengers_ticket {

    private int ticket_id;
    private int passenger_id;

    public Passengers_ticket(int ticket_id, int passenger_id) {
        this.ticket_id = ticket_id;
        this.passenger_id = passenger_id;
    }

    public int getTicket_id() { return ticket_id; }

    public void setTicket_id(int ticket_id) { this.ticket_id = ticket_id; }

    public int getPassenger_id() { return passenger_id; }

    public void setPassenger_id(int passenger_id) { this.passenger_id = passenger_id; }
}
