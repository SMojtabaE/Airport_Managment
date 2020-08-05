package model;

public class Airplane implements Showable {

    private int id;
    private int seats;
    public Airplane(int seats){
        this.setSeats(seats);
    }
    public Airplane(int id , int seats){
        this.setSeats(seats);
        this.setId(id);
    }

    @Override
    public void show() {
        System.out.println("Id : " + this.getId()+"\n"+
                "seats : "+ this.getSeats());
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getSeats() { return seats; }

    public void setSeats(int seats) { this.seats = seats; }
}
