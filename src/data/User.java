package data;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String pwd;
    private SecretKey key;
    private Cipher encryptionCipher;

    public User() {
        this (null,null,null,null);
    }

    public User(String name, String pwd, SecretKey key, Cipher encryptionCipher) {
        this.name = name;
        this.pwd = pwd;
        this.key = key;
        this.encryptionCipher = encryptionCipher;
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

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public Cipher getEncryptionCipher() {
        return encryptionCipher;
    }

    public void setEncryptionCipher(Cipher encryptionCipher) {
        this.encryptionCipher = encryptionCipher;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", key=" + key +
                '}';
    }
}
