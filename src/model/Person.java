package model;

public abstract class Person {

    private int id ;
    private String name;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String profile_photo_Path;
    private String darkthem;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getLastname() { return lastname; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getProfile_photo_Path() { return profile_photo_Path; }

    public void setProfile_photo_Path(String profile_photo_Path) { this.profile_photo_Path = profile_photo_Path; }

    public String getDarkthem() { return darkthem; }

    public void setDarkthem(String darkthem) { this.darkthem = darkthem; }
}
