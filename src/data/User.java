package data;

import javax.crypto.SecretKey;
import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String pwd;
    private boolean isRegistered;

    public User() {
        this (null,null, false);
    }

    public User(String name, String pwd, boolean isRegistered) {
        this.name = name;
        this.pwd = pwd;
        this.isRegistered = isRegistered;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
