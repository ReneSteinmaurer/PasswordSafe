package data;

import javax.crypto.SecretKey;
import java.io.*;
import java.util.ArrayList;

public class CSVWriter implements Writer {

    /*@Override
    public void appendUser(User u, String file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write("\n");
            bw.write(u.getName() + ";");
            bw.write(u.getPwd() + ";");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void appendUser(ArrayList<User> list, String file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeInt(list.size());
            for (int i = 0; i < list.size(); i++) {
                oos.writeObject(list.get(i));
            }
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void appendEncryptionKey(SecretKey key, String file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file, true))) {
            oos.writeObject(key);
            oos.writeChar(';');
            oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
