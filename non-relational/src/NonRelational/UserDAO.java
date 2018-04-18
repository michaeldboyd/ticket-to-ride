package NonRelational;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.interfaces.persistence.IUserDAO;
import com.example.sharedcode.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {

    private final String usersFolderPath = "jsonDB" + File.separator + "users";
    private final String gamesFolderPath = "jsonDB" + File.separator + "games";

    @Override
    public User getUser(String userName) {

        String path = usersFolderPath + File.separator + userName;
        File userFile = new File(path);

        return loadUserFromFolder(userFile);
    }

    @Override
    public List<User> getAllUsers() {

        String path = usersFolderPath + File.separator;
        File userDirectory = new File(path);

        File[] foldersInDirectory = userDirectory.listFiles();

        ArrayList<User> allUsers = new ArrayList<>();

        if (foldersInDirectory == null)
            return allUsers;

        for (File userFolder : foldersInDirectory) {
            User user = loadUserFromFolder(userFolder);
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

        writeUserToFolder(user, path);

        return user;
    }

    @Override
    public void updateUser(String userName, User user, String gameID) {

        // Update their overall instance
        String path = usersFolderPath + File.separator + userName;
        writeUserToFolder(user, path);

        if (gameID != null && gameID.length() > 0) {
            // Update their instance in the local game
            path = gamesFolderPath + File.separator + gameID + File.separator + "users" + File.separator + userName;
            writeUserToFolder(user, path);
        }
    }

    @Override
    public String login(String userName, String authToken) {

        String path = usersFolderPath + File.separator + userName;

        File userFile = new File(path);
        User user = loadUserFromFolder(userFile);
        user.setAuthtoken(authToken);

        writeUserToFolder(user, path);

        return authToken;
    }

    @Override
    public boolean logout(String userName) {

        String path = usersFolderPath + File.separator + userName;

        File userFile = new File(path);
        User user = loadUserFromFolder(userFile);
        user.setAuthtoken("");

        writeUserToFolder(user, path);

        return true;
    }

    @Override
    public boolean joinGame(String userName, String gameID) {


        String userPath = usersFolderPath + File.separator + userName;
        File userFile = new File(userPath);
        User user = loadUserFromFolder(userFile);

        String userGamePath = gamesFolderPath + File.separator + gameID + File.separator + "users" + File.separator + userName;
        writeUserToFolder(user,userGamePath);

        return false;
    }

    @Override
    public boolean leaveGame(String userName, String gameID) {

        String gamePath = gamesFolderPath + File.separator + gameID + File.separator + "users" + File.separator + userName;
        File userGameFile = new File(gamePath);

        return userGameFile.delete();
    }

    private User loadUserFromFolder(File userFolder) {
        String userFilePath = userFolder.getPath() + File.separator + userFolder.getName() + ".json";
        File userFile = new File(userFilePath);
        User user = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(userFile);
            user = (User) JsonReader.jsonToJava(fileInputStream, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return user;
    }

    private void writeUserToFolder(User user, String path) {
        File newUserFile = new File(path + File.separator + user.getUsername() + ".json");
        newUserFile.getParentFile().mkdirs();

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