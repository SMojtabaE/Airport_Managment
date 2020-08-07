package model;

public class Logs {

    private int id;
    private String time;
    private String date;
    private String report;

    public Logs(int id,String report , String date, String time) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.report = report;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getReport() { return report; }

    public void setReport(String report) { this.report = report; }
}
