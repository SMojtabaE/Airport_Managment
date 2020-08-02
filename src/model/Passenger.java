package model;

public class Passenger extends Person implements Showable {

    private double money;

    public Passenger(String name,String lastname,String username,String password,String email,String phone,double money){
        this.setName(name);
        this.setLastname(lastname);
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setPhoneNumber(phone);
        this.setMoney(money);
        this.setProfile_photo_Path("H:\\programs code\\Java\\Airport_Managment\\src\\view\\Picturs\\passengers_defult_photo.png");
     //   this.setProfile_photo_Path("H:\\programs code\\Java\\Airport_Managment\\src\\view\\Picturs\\passengers_defult_photo.png");
    }
    public Passenger(int id,String name,String lastname,String username,String password,String email,String phone,double money,String path){
        this.setName(name);
        this.setLastname(lastname);
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setPhoneNumber(phone);
        this.setMoney(money);
        this.setId(id);
        this.setProfile_photo_Path(path);
        //this.setProfile_photo_Path("H:\\programs code\\Java\\Airport_Managment\\src\\view\\Picturs\\passengers_defult_photo.png");
    }

    @Override
    public void show() {
        System.out.println("Name : " + this.getName()+"\n"+
                "Last name : "+ this.getLastname() + "\n"+
                "Username : " + this.getUsername() + "\n"+
                "Password : " + this.getPassword() + "\n"+
                "Email : " + this.getEmail() + "\n"+
                "ID : " + this.getId() + "\n"+
                "PoneNumber : " + this.getPhoneNumber()+ "\n"+
                "Money : " + this.getMoney());
    }


    public double getMoney() { return money; }

    public void setMoney(double money) { this.money = money; }
}
