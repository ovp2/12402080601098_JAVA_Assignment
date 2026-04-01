package onlineexam;

// This is the base class for any user in the system
// we are using this as the parent class (for inheritance)

public class User {
    // basic info that every user will have
    private String name;
    private String userId;
    private String password;

    // constructor to set the user details
    public User(String name, String userId, String password) {
        this.name = name;
        this.userId = userId;
        this.password = password;
    }

    // getters - we need these to access private fields from outside
    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    // setter for name in case we need to change it later
    public void setName(String name) {
        this.name = name;
    }

    // just a basic toString so we can print user info easily
    @Override
    public String toString() {
        return "User: " + name + " (ID: " + userId + ")";
    }
}
