package data;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String pwd;
    private String salt;
    private boolean isRegistered;

    public User() {
        this (null,null,null, false);
    }

    public User(String name, String pwd, String salt, boolean isRegistered) {
        this.name = name;
        this.pwd = pwd;
        this.salt = salt;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
