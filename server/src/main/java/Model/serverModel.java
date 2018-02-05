package Model;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.User;
import com.example.sharedcode.communication.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class serverModel {

    public static serverModel _instance;

    public static serverModel instance() {

        if (_instance == null){
            _instance = new serverModel();
        }

        return _instance;
    }

    private serverModel() {}

    public ArrayList<Command> queuedCommands = new ArrayList<>();
    public ArrayList<User> loggedInUsers = new ArrayList<>();
    public Map<String, String> allUsers = new HashMap<>();
    public ArrayList<Game> Games = new ArrayList<>();



}
