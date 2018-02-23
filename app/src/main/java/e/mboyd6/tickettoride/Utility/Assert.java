package e.mboyd6.tickettoride.Utility;

import e.mboyd6.tickettoride.BuildConfig;

/**
 * Created by mboyd6 on 2/22/2018.
 */

public class Assert {
    public static void notNull(Object obj, String detailedError)
    {
        if(BuildConfig.DEBUG)
            if(obj == null)
                throw new AssertionError(detailedError);
    }

    public static void isEqual(Object a, Object b, String detailedError)
    {
        if(BuildConfig.DEBUG)
        {
            if(a == null && b == null) // they are the same, albeit both null
                return;
            if(a == null || b == null) // they are not equal, because one or the other is null
               throw new AssertionError(detailedError);

            if(a.getClass() != b.getClass())
                throw new AssertionError(detailedError);

        }
    }
}
