import com.example.sharedcode.interfaces.IUtility;

public class Utility implements IUtility{

    public static void _clearServer(String pass)
    {
        ServerModel.instance().getTestInstance(pass);
    }

    @Override
    public void clearServer(String superSecretPassword) {

    }
}
