package e.mboyd6.tickettoride;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Presenters.Interfaces.IWaitroomPresenter;
import e.mboyd6.tickettoride.Presenters.WaitroomPresenter;
import e.mboyd6.tickettoride.Views.Activities.GameActivity;
import e.mboyd6.tickettoride.Views.Activities.MainActivity;
import e.mboyd6.tickettoride.Views.Adapters.ColorSelectionView;
import e.mboyd6.tickettoride.Views.Fragments.WaitroomFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IWaitroomFragment;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class WaitroomFragmentTest {

  @Rule
  public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

  private MainActivity testingActivity;
  private WaitroomFragment testFragment;
  private ArrayList<Game> fakeGames;
  private Game game1;
  private Game game2;
  private Game game3;
  private Game game4;

  public WaitroomFragmentTest() {
    testingActivity = activityActivityTestRule.getActivity();
  }

  @Test
  public void useAppContext() throws Exception {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getTargetContext();

    assertEquals("e.mboyd6.tickettoride", appContext.getPackageName());
  }

  @Before
  public void setUp() throws Exception {

    // Starts the activity under test using
    // the default Intent with:
    // action = {@link Intent#ACTION_MAIN}
    // flags = {@link Intent#FLAG_ACTIVITY_NEW_TASK}
    // All other fields are null or empty.
    testingActivity = activityActivityTestRule.getActivity();

    testFragment = new WaitroomFragment();
    testingActivity.getSupportFragmentManager().beginTransaction().add(R.id.main_activity_fragment_container,testFragment,null).commit();
  }

  @After
  public void tearDown() throws Exception {
    if (ClientModel.getInstance().getCurrentGame() != null) {
      // SORRY HUNTER.. PLEAEE FORGIVE ME (editing your code)
      // ClientModel.getInstance().setLeaveGameResponse(ClientModel.getInstance().getCurrentGame().getGameID(), "");
      ClientModel.getInstance().setCurrentGame(null);
    }
    ClientModel.getInstance().clearInstance();
  }

  @Test
  public void testColorsGame1() {
    ClientModel.getInstance().setPlayerID("001");
    generateFakeGames();
    ClientModel.getInstance().setGames(fakeGames);
    waitForSeconds(1);

    //ClientModel.getInstance().setJoinGameResponse(game1.getGameID(),"001", "");
    testingActivity.getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_container, new WaitroomFragment(), "CURRENT_FRAGMENT").commit();
    waitForSeconds(1);

    Fragment currentFragment = testingActivity.getSupportFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");

    assertTrue(currentFragment != null);
    assertTrue(currentFragment instanceof WaitroomFragment);

    WaitroomFragment testWaitroomFragment = (WaitroomFragment) currentFragment;

    ClientModel.getInstance().setGames(fakeGames);
    waitForSeconds(1);

    ColorSelectionView colorSelection = testWaitroomFragment.getView().findViewById(R.id.color_selection_1);
    assertTrue(compareBackgrounds(colorSelection, R.drawable.color_turquoise_faded));

    changeFakeGames();
    ClientModel.getInstance().setGames(fakeGames);
    waitForSeconds(1);
    waitForSeconds(1);
    waitForSeconds(1);

    assertTrue(compareBackgrounds(colorSelection, R.drawable.color_orange_faded));
    waitForSeconds(1);


  }

  @Test
  public void GameActivityView(){
    Intent intent = new Intent(testingActivity, GameActivity.class);
    testingActivity.startActivity(intent);

    waitForSeconds(20);
  }

  public boolean compareBackgrounds(ColorSelectionView colorSelectionView, int resourceID) {
    if (colorSelectionView == null) return false;
    int backgroundID = colorSelectionView.getBackgroundID();
    int backgroundResourceID = resourceID;
    return backgroundID == backgroundResourceID;
  }

  public void waitForSeconds(float multiplier) {
    try{
      long amount = 1000 * (long) multiplier;
      Thread.sleep(amount);
    } catch (Exception e) {

    }
  }

  public void generateFakeGames() {
    fakeGames = new ArrayList<Game>();
    game1 = new Game();
    game1.setGameID("002");
    game1.addPlayer(new Player("001", "Michael", PlayerColors.TURQUOISE));
    game1.addPlayer(new Player("002", "Alli", PlayerColors.BLUE));
    game1.addPlayer(new Player("003", "Eric", PlayerColors.RED));
    fakeGames.add(game1);
    game2 = new Game();
    game2.setGameID("001");
    game2.addPlayer(new Player("001", "Alli", PlayerColors.BLUE));
    game2.addPlayer(new Player("002", "Michael", PlayerColors.RED));
    fakeGames.add(game2);
    game3 = new Game();
    game3.setGameID("003");
    fakeGames.add(game3);
    game4 = new Game();
    game4.setGameID("004");
    game4.addPlayer(new Player("001", "Michael", PlayerColors.RED));
    game4.addPlayer(new Player("002", "Alli", PlayerColors.TURQUOISE));
    game4.addPlayer(new Player("003", "Eric", PlayerColors.ORANGE));
    game4.addPlayer(new Player("004", "Hunter", PlayerColors.BLUE));
    game4.addPlayer(new Player("005", "Jonny", PlayerColors.PURPLE));
    fakeGames.add(game4);
  }

  public void changeFakeGames() {
    fakeGames = new ArrayList<Game>();
    game1 = new Game();
    game1.setGameID("002");
    game1.addPlayer(new Player("001", "Michael", PlayerColors.ORANGE));
    game1.addPlayer(new Player("002", "Alli", PlayerColors.BLUE));
    game1.addPlayer(new Player("003", "Eric", PlayerColors.RED));
    fakeGames.add(game1);
    game2 = new Game();
    game2.setGameID("001");
    game2.addPlayer(new Player("001", "Alli", PlayerColors.BLUE));
    game2.addPlayer(new Player("002", "Michael", PlayerColors.RED));
    fakeGames.add(game2);
    game3 = new Game();
    game3.setGameID("003");
    fakeGames.add(game3);
    game4 = new Game();
    game4.setGameID("004");
    game4.addPlayer(new Player("001", "Michael", PlayerColors.RED));
    game4.addPlayer(new Player("002", "Alli", PlayerColors.TURQUOISE));
    game4.addPlayer(new Player("003", "Eric", PlayerColors.ORANGE));
    game4.addPlayer(new Player("004", "Hunter", PlayerColors.BLUE));
    game4.addPlayer(new Player("005", "Jonny", PlayerColors.PURPLE));
    fakeGames.add(game4);
  }
}
