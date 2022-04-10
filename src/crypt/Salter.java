package crypt;

import java.util.Random;

public class Salter {

    public String retrieveCustomSalt() {
        String key = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqerstuvwxyz123456789";

        char[] chars = key.toCharArray();
        StringBuilder sb = new StringBuilder();
        Random r = new Random();

        for (int i = 0; i < 10; i++) {
            sb.append(chars[r.nextInt(key.length())]);
        }

        return sb.toString();
    }
}
