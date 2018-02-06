package e.mboyd6.tickettoride.Communication;

import com.example.sharedcode.communication.Command;

/**
 * Created by eric on 2/5/18.
 */

public class CommandFactory {

    // ***** Uncomment if we actually need the singleton *****
//    private static CommandFactory factory;
//
//    // Default initializer not accessible--must use singleton
//    private CommandFactory(){};
//
//    public static CommandFactory instance() {
//        if (factory == null) {
//            factory = new CommandFactory();
//        }
//
//        return factory;
//    }

    public static Command createCommand(String className, String methodName, String[] paramTypesStringNames, Object[] paramValues) {
        return new Command(className, methodName, paramTypesStringNames, paramValues);
    }
}
