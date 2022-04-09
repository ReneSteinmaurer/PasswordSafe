package data;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.HashMap;

public interface Reader {

    ArrayList<User> readUsers(String file);
}
