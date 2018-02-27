package e.mboyd6.tickettoride.Communication;

import com.example.sharedcode.interfaces.IUtility;
import com.example.sharedcode.model.UpdateType;

/**
 * Created by mboyd6 on 2/26/2018.
 */

public class UtilityFacade implements IUtility{


    private static UtilityFacade instance = new UtilityFacade();

    public static UtilityFacade getInstance() {
        return instance;
    }

    public static void _handleError(UpdateType type, String message) {
        instance.handleError(type, message);
    }

    public static void _handleLoginError(UpdateType type, String message) {
       instance.handleLoginError(type, message);
    }

    @Override
    public void handleError(UpdateType type, String message) {
            System.out.println("HandleError called");
    }

    @Override
    public void handleLoginError(UpdateType type, String message) {
            System.out.println("HandleLoginError called");
    }
}
