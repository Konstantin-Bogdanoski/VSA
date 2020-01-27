package mk.ukim.finki.vsa.dto;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public class UserLogin {
    private String email;
    private String password;

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
}
