package model;

public class Subscriber {

    private String username;
    private String password;
    private String name;
    private String penalties;

    public Subscriber() {
    }

    public Subscriber(String username) {
        this.username = username;
    }

    public Subscriber(String username, String password, String name, String penalties) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.penalties = penalties;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPenalties() {
        return penalties;
    }

    public void setPenalties(String penalties) {
        this.penalties = penalties;
    }
}
