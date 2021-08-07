package Model.Requests;

public class UserPassData {
    private final String userName;
    private final String password;

    public UserPassData(String userName,String password){
        this.userName = userName;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}
