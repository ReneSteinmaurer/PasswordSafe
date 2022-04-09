package data;

import java.util.HashMap;

public interface Reader {

    HashMap<String, User> readUsers(String file);
    HashMap<String, SavedEntry> readEntries(String file);
}
