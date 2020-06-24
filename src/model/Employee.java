package model;

public class Employee extends Person implements Showable {


    private String adress;
    private double salary;
    private int job;

    public Employee(String name,String lastname,String username,String password,String email,String adress,String phone,double salary,int job){
        this.setName(name);
        this.setLastname(lastname);
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setPhoneNumber(phone);
        this.setAdress(adress);
        this.setSalary(salary);
        this.setJob(job);
    }

    public Employee(int id,String name,String lastname,String username,String password,String email,String adress,String phone,double salary,int job){
        this.setName(name);
        this.setLastname(lastname);
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setPhoneNumber(phone);
        this.setAdress(adress);
        this.setSalary(salary);
        this.setJob(job);
        this.setId(id);
    }

    @Override
    public void show() {
        System.out.println("Name : " + this.getName()+"\n"+
                "Last name : "+ this.getLastname() + "\n"+
                "Username : " + this.getUsername() + "\n"+
                "Password : " + this.getPassword() + "\n"+
                "Email : " + this.getEmail() + "\n"+
                "ID : " + this.getId() + "\n"+
                "PoneNumber : " + this.getPhoneNumber() + "\n"+
               "Salary : " + this.getSalary());
    }

    public String getAdress() { return adress; }

    public void setAdress(String adress) { this.adress = adress; }

    public double getSalary() { return salary; }

    public void setSalary(double salary) { this.salary = salary; }

    public int getJob() { return job; }

    public void setJob(int job) { this.job = job; }
}
