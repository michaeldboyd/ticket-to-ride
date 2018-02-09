package e.mboyd6.tickettoride;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import e.mboyd6.tickettoride.Presenters.Interfaces.ILoginPresenter;
import e.mboyd6.tickettoride.Presenters.LoginPresenter;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginPresenterTest {

    private ILoginPresenter loginPresenter;

    @Before
    public void setup(){
        loginPresenter = new LoginPresenter();
    }

    @Test
    public void validUsernameTest() throws Exception {
        String s = "THIS_is.A_valid.PASSWORD.1234567890";
        assertTrue(loginPresenter.validUsername(s));

        s = "THIS-is[]AN+invalid.PASSWORD!@#$%^&*()<>?:";
        assertFalse(loginPresenter.validUsername(s));

        s = "This username has spaces";
        assertFalse(loginPresenter.validUsername(s));

        s = null;
        assertFalse(loginPresenter.validUsername(s));

        s = "invalid..username";
        assertFalse(loginPresenter.validUsername(s));

        s = "invalid__username";
        assertFalse(loginPresenter.validUsername(s));

        s = "valid.username";
        assertTrue(loginPresenter.validUsername(s));

        s = "valid_username";
        assertTrue(loginPresenter.validUsername(s));

        s = "";
        assertFalse(loginPresenter.validUsername(s));

        s = " ";
        assertFalse(loginPresenter.validUsername(s));
    }

    @Test
    public void validPassowrdTest() throws Exception {
        String s = "ValidPassword!@#$%^&*()1234567890-=";
        assertTrue(loginPresenter.validPassword(s));

        s = "invalid password";
        assertFalse(loginPresenter.validPassword(s));

        s = "";
        assertFalse(loginPresenter.validPassword(s));

        s = " ";
        assertFalse(loginPresenter.validPassword(s));

        s = null;
        assertFalse(loginPresenter.validPassword(s));
    }

    @After
    public void tearDown(){
        loginPresenter = null;
    }
}