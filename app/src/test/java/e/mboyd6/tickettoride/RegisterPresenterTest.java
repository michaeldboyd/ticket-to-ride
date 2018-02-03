package e.mboyd6.tickettoride;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import e.mboyd6.tickettoride.Presenters.RegisterPresenter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jonathanlinford on 2/3/18.
 */

public class RegisterPresenterTest{

    RegisterPresenter registerPresenter;

    @Before
    public void setup(){
        registerPresenter = new RegisterPresenter();
    }

    @Test
    public void validUsernameTest() throws Exception {
        String s = "THIS_is.A_valid.PASSWORD.1234567890";
        assertTrue(registerPresenter.validUsername(s));

        s = "THIS-is[]AN+invalid.PASSWORD!@#$%^&*()<>?:";
        assertFalse(registerPresenter.validUsername(s));

        s = "This username has spaces";
        assertFalse(registerPresenter.validUsername(s));

        s = null;
        assertFalse(registerPresenter.validUsername(s));

        s = "invalid..username";
        assertFalse(registerPresenter.validUsername(s));

        s = "invalid__username";
        assertFalse(registerPresenter.validUsername(s));

        s = "valid.username";
        assertTrue(registerPresenter.validUsername(s));

        s = "valid_username";
        assertTrue(registerPresenter.validUsername(s));

        s = "";
        assertFalse(registerPresenter.validUsername(s));

        s = " ";
        assertFalse(registerPresenter.validUsername(s));
    }

    @Test
    public void validPassowrdTest() throws Exception {
        String s = "ValidPassword!@#$%^&*()1234567890-=";
        assertTrue(registerPresenter.validPassword(s));

        s = "invalid password";
        assertFalse(registerPresenter.validPassword(s));

        s = "";
        assertFalse(registerPresenter.validPassword(s));

        s = " ";
        assertFalse(registerPresenter.validPassword(s));

        s = null;
        assertFalse(registerPresenter.validPassword(s));
    }

    @Test
    public void passwordMatchTest() throws Exception{

        String s1 = "thesePasswordsMatch";
        String s2 = "thesePasswordsMatch";
        assertTrue(registerPresenter.passwordsMatch(s1,s2));

        s1 = "casesensative";
        s2 = "CASESENSATIVE";
        assertFalse(registerPresenter.passwordsMatch(s1,s2));

        s1 = null;
        s2 = "nullDoesntwork";
        assertFalse(registerPresenter.passwordsMatch(s1,s2));

        s1 = "nullDoesntwork";
        s2 = null;
        assertFalse(registerPresenter.passwordsMatch(s1,s2));

        s1 = null;
        s2 = null;
        assertFalse(registerPresenter.passwordsMatch(s1,s2));

        s1 = "spacesmatter";
        s2 = "spaces matter";
        assertFalse(registerPresenter.passwordsMatch(s1,s2));

        s1 = " donttrim ";
        s2 = "donttrim";
        assertFalse(registerPresenter.passwordsMatch(s1,s2));

    }

    @After
    public void tearDown(){
        registerPresenter = null;
    }

}
