package data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class ObjectWriter implements Writer {

    @Override
    public void appendUser(HashMap<String, User> list, String file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeInt(list.size());

            for (User value : list.values()) {
                oos.writeObject(value);
            }
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeEntries(HashMap<String, SavedEntry> map, String file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeInt(map.size());

            for (SavedEntry value : map.values()) {
                oos.writeObject(value);
            }
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
