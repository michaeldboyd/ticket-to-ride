package e.mboyd6.tickettoride;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.GameInitializer;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;
import com.example.sharedcode.model.TrainCard;
import com.example.sharedcode.model.TrainType;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Views.Activities.GameActivity;
import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;
import e.mboyd6.tickettoride.Views.Fragments.WaitroomFragment;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GameActivityTest {

  @Rule
  public ActivityTestRule<GameActivity> activityActivityTestRule = new ActivityTestRule<>(GameActivity.class);

  private GameActivity testingActivity;
  private WaitroomFragment testFragment;
  private ArrayList<Game> fakeGames;
  private Game game1;
  private Game game2;
  private Game game3;
  private Game game4;

  public GameActivityTest() {
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
  public void GameActivityView(){
    ClientModel.getInstance().setPlayerName("001");
    generateFakeGames();
    ClientModel.getInstance().setGames(fakeGames);
    GameInitializer gameInitializer = new GameInitializer();
    Game currentGame = gameInitializer.initializeGame(fakeGames.get(0));
    ClientModel.getInstance().setCurrentGame(currentGame);
    Intent intent = new Intent(testingActivity, GameActivity.class);
    testingActivity.startActivity(intent);
    waitForSeconds(1);
    BoardFragment boardFragment = (BoardFragment) testingActivity.getSupportFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
    waitForSeconds(360);
  }

  @Test
  public void BoardFragmentTest(){
    ClientModel.getInstance().setPlayerName("001");
    generateFakeGames();
    GameInitializer gameInitializer = new GameInitializer();
    Game currentGame = gameInitializer.initializeGame(generateBoardFragmentFakeGame());

    ArrayList<DestinationCard> destinationCards = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      destinationCards.add(currentGame.getDestinationDeck().get(i));
    }

    currentGame.getPlayers().get(0).getDestinationCards().addAll(destinationCards);

    /*
    currentGame.getFaceUpDeck().set(0, new TrainCard(TrainType.BOX));
    currentGame.getFaceUpDeck().set(1, new TrainCard(TrainType.CABOOSE));
    currentGame.getFaceUpDeck().set(2, new TrainCard(TrainType.COAL));
    currentGame.getFaceUpDeck().set(3, new TrainCard(TrainType.FREIGHT));
    currentGame.getFaceUpDeck().set(4, new TrainCard(TrainType.HOPPER));
    */

    currentGame.getFaceUpDeck().set(0, TrainType.BOX);
    currentGame.getFaceUpDeck().set(1, TrainType.PASSENGER);
    currentGame.getFaceUpDeck().set(2, TrainType.REEFER);
    currentGame.getFaceUpDeck().set(3, TrainType.TANKER);
    currentGame.getFaceUpDeck().set(4, TrainType.LOCOMOTIVE);


    ClientModel.getInstance().setCurrentGame(currentGame);
    ClientModel.getInstance().setPlayerTurn("001");
    Intent intent = new Intent(testingActivity, GameActivity.class);
    testingActivity.startActivity(intent);
    waitForSeconds(4);
    System.out.println("BEFORE FRAGMENT");
    BoardFragment boardFragment = (BoardFragment) testingActivity.getSupportFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
    waitForSeconds(2);
    boardFragment.onUpdateTurn("002");
    waitForSeconds(2);
    boardFragment.onUpdateTurn("001");
    waitForSeconds(360);
  }


  public void waitForSeconds(float multiplier) {
    try{
      long amount = 1000 * (long) multiplier;
      Thread.sleep(amount);
    } catch (Exception e) {

    }
  }

  public Game generateBoardFragmentFakeGame() {
    Game fakeGame = new Game();
    fakeGame.setGameID("002");
    fakeGame.addPlayer(new Player("001", "Michael", PlayerColors.TURQUOISE));
    fakeGame.addPlayer(new Player("002", "Alli", PlayerColors.BLUE));
    fakeGame.addPlayer(new Player("003", "Eric", PlayerColors.RED));
    fakeGame.getPlayers().get(0).getHand().put(TrainType.BOX, 7);
    return fakeGame;
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
