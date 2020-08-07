package model;

public class Report_to_managmers implements Showable {
    private int id;
    private int idof;
    private String massage;
    private String date;
    private String time;
    private String who_is;

    public Report_to_managmers(int idof, String massage, String date, String time, String who_is) {
        this.idof = idof;
        this.massage = massage;
        this.date = date;
        this.time = time;
        this.who_is = who_is;
    }

    public Report_to_managmers(int id, int idof, String massage, String date, String time, String who_is) {
        this.id = id;
        this.idof = idof;
        this.massage = massage;
        this.date = date;
        this.time = time;
        this.who_is = who_is;
    }

    @Override
    public void show() {
        System.out.println("id : " + this.getId()+"\n"+
                "id of : "+ this.getIdof() + "\n"+
                "Massage : " + this.getMassage() + "\n"+
                "date : " + this.getDate() + "\n"+
                "time : " + this.getTime() + "\n"+
                "who is : " + this.getWho_is());
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getIdof() { return idof; }

    public void setIdof(int idof) { this.idof = idof; }

    public String getMassage() { return massage; }

    public void setMassage(String massage) { this.massage = massage; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public String getWho_is() { return who_is; }

    public void setWho_is(String who_is) { this.who_is = who_is; }
}
