package mk.ukim.finki.vsa.dto;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public class UserLogin {
    private String username;
    private String password;

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
}
