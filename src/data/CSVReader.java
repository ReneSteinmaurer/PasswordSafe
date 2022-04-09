package data;

import javax.crypto.SecretKey;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CSVReader implements Reader {

    @Override
    public ArrayList<User> readUsers(String file) {
        ArrayList<User> users = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            User u;
            int length = ois.readInt();

            for (int i = 0; i < length; i++) {
                users.add((User) ois.readObject());
            }

        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }

        return users;
    }
}
