import com.example.sharedcode.interfaces.IUtility;

public class Utility implements IUtility{

    @Override
    public void clearServer(String superSecretPassword) {
        ServerModel.instance().getTestInstance(superSecretPassword);
    }
}
