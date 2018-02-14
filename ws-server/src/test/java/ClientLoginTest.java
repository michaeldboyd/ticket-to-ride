import com.example.sharedcode.interfaces.IClientLoginFacade;

public class ClientLoginTest implements IClientLoginFacade {

    @Override
    public void login(String authToken, String message) {
        testReply("Login got it!");
    }

    @Override
    public void register(String authToken, String message) {
        testReply("Register got it!");
    }

    @Override
    public void logout(String authToken, String message) {
        testReply("Logout got it!");
    }

    public void testReply(String message){
        String auth = "auth";
        ServerLoginFacadeTest test = new ServerLoginFacadeTest();
        test.didItWork(message);
    }
}
