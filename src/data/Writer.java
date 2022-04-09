package data;

import java.util.HashMap;

public interface Writer {

    //void appendUser(User u, String file);

    void appendUser(HashMap<String, User> list, String file);
    void writeEntries(HashMap<String, SavedEntry> map, String file);
}
