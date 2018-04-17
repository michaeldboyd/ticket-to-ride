package NonRelational;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.interfaces.persistence.IUserDAO;
import com.example.sharedcode.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {

    private final String usersFolderPath = "users";
    private final String gamesFolderPath = "games";

    @Override
    public User getUser(String userName) {

        String path = usersFolderPath + File.separator + userName;
        File userFile = new File(path);

        return loadUserFromFile(userFile);
    }

    @Override
    public List<User> getAllUsers() {

        String path = usersFolderPath + File.separator;
        File userDirectory = new File(path);

        File[] filesInDirectory = userDirectory.listFiles();

        ArrayList<User> allUsers = new ArrayList<>();

        for (File userFile : filesInDirectory) {
            User user = loadUserFromFile(userFile);
            if (user != null) {
                allUsers.add(user);
            }
        }

        return allUsers;
    }

    @Override
    public User addUser(User user) {

        String path = usersFolderPath + File.separator + user.getUsername();

//        User user = new User();
//        user.setUserID(userName);
//        user.setPassword(password);

        writeUserToFile(user, path);

        return user;
    }

    @Override
    public String login(String userName, String authToken) {

        String path = usersFolderPath + File.separator + userName;

        File userFile = new File(path);
        User user = loadUserFromFile(userFile);
        user.setAuthtoken(authToken);

        writeUserToFile(user, path);

        return authToken;
    }

    @Override
    public boolean logout(String userName) {

        String path = usersFolderPath + File.separator + userName;

        File userFile = new File(path);
        User user = loadUserFromFile(userFile);
        user.setAuthtoken("");

        writeUserToFile(user, path);

        return true;
    }

    @Override
    public boolean joinGame(String userName, String gameID) {


        String userPath = usersFolderPath + File.separator + userName;
        File userFile = new File(userPath);
        User user = loadUserFromFile(userFile);

        String userGamePath = gamesFolderPath + File.separator + gameID + File.separator + "users" + File.separator + userName;
        writeUserToFile(user,userGamePath);

        return false;
    }

    @Override
    public boolean leaveGame(String userName, String gameID) {

        String gamePath = gamesFolderPath + File.separator + gameID + File.separator + "users" + File.separator + userName;
        File userGameFile = new File(gamePath);

        return userGameFile.delete();
    }

    private User loadUserFromFile(File userFile) {
        User user = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(userFile);
            user = (User) JsonReader.jsonToJava(fileInputStream, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return user;
    }

    private void writeUserToFile(User user, String path) {
        File newUserFile = new File(path);
        newUserFile.mkdirs();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(newUserFile);
            JsonWriter jw = new JsonWriter(fileOutputStream);
            jw.write(user);
            jw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}