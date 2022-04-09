package data;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.HashMap;

public interface Writer {

    //void appendUser(User u, String file);

    void appendUser(ArrayList<User> list, String file);
    void appendEncryptionKey(SecretKey key, String file);
}
