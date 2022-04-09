package crypt;

public interface Crypt {

    String encrypt(String s) throws Exception;
    String decrypt(String s) throws Exception;
}
