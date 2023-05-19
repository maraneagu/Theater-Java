package Theater;

import java.util.*;

public class User {
    private String username;
    private String email;
    private String password;
    private String name;
    private List<Ticket> tickets;

    public User() {
        tickets = new ArrayList<>();
    }

    public User(String username, String email, String password, String name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.tickets = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "\uF0B2 Profile \uF0B2" + '\n' +
                "The username @: " + username + '\n' +
                "The name of the profile: " + name + '\n' +
                "The email: " + email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Ticket> getTickets() {
        return tickets;
    }
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
