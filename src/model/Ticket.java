package model;

public class Ticket implements Showable{

    private int id;
    private int flight_id;
    private int passengers_id;
    private Double price;
    private Double loss;
    public Ticket (Double price,Double loss,int flight_id,int passengers_id){
        this.setPrice(price);
        this.setLoss(loss);
        this.setFlight_id(flight_id);
        this.setPassengers_id(passengers_id);
    }

    public Ticket (int id,Double price,Double loss,int flight_id,int passengers_id){
        this.setPrice(price);
        this.setLoss(loss);
        this.setFlight_id(flight_id);
        this.setPassengers_id(passengers_id);
        this.setId(id);
    }
    @Override
    public void show() {
        System.out.println("Id : " + this.getId()+"\n"+
                "Price : "+ this.getPrice() + "\n"+
                "Loss : " + this.getLoss() + "\n"+
                "Flight ID : " + this.getFlight_id() + "\n"+
                "passengers ID : " + this.getPassengers_id());
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getFlight_id() { return flight_id; }

    public void setFlight_id(int flight_id) { this.flight_id = flight_id; }

    public int getPassengers_id() { return passengers_id; }

    public void setPassengers_id(int passengers_id) { this.passengers_id = passengers_id; }

    public Double getPrice() { return price; }

    public void setPrice(Double price) { this.price = price; }

    public Double getLoss() { return loss; }

    public void setLoss(Double loss) { this.loss = loss; }
}
