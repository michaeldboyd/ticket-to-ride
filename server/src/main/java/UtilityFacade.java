import com.example.sharedcode.interfaces.IUtility;

public class UtilityFacade implements IUtility{

    public static void _clearServer(String password)
    {
        new UtilityFacade().clearServer(password);
    }
    @Override
    public void clearServer(String password) {
        ServerModel.instance().clearServer(password);
    }
}
