package data;

import java.io.*;
import java.util.HashMap;

public class ObjectReader implements Reader {

    @Override
    public HashMap<String, User> readUsers(String file) {
        HashMap<String, User> users = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            int length = ois.readInt();

            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    User u = (User) ois.readObject();
                    users.put(u.getName(), u);
                }
            }
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }

        return users;
    }

    @Override
    public HashMap<String, SavedEntry> readEntries(String file) {
        HashMap<String, SavedEntry> entries = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            int length = ois.readInt();

            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    SavedEntry se = (SavedEntry) ois.readObject();
                    entries.put(se.getName(), se);
                }
            }
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }

        return entries;
    }

}
