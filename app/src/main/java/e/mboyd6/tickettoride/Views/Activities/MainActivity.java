package e.mboyd6.tickettoride.Views.Activities;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;

import java.util.ArrayList;

import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Presenters.LobbyPresenter;
import e.mboyd6.tickettoride.Presenters.LoginPresenter;
import e.mboyd6.tickettoride.Presenters.RegisterPresenter;
import e.mboyd6.tickettoride.Presenters.WaitroomPresenter;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Fragments.LobbyFragment;
import e.mboyd6.tickettoride.Views.Fragments.LoginFragment;
import e.mboyd6.tickettoride.Views.Fragments.RegisterFragment;
import e.mboyd6.tickettoride.Views.Fragments.WaitroomFragment;
import e.mboyd6.tickettoride.Views.Interfaces.ILobbyFragment;
import e.mboyd6.tickettoride.Views.Interfaces.ILoginFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IMainActivity;
import e.mboyd6.tickettoride.Views.Interfaces.IRegisterFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IWaitroomFragment;

public class MainActivity extends AppCompatActivity
        implements IMainActivity
{
  private static final long MOVE_DEFAULT_TIME = 1000;
  private static final long SLIDE_DEFAULT_TIME = 500;
  private FragmentManager mFragmentManager;
  private Handler mDelayedTransactionHandler = new Handler();
  private Runnable mRunnableTransitionToRegister = new Runnable() {
    public void run() {
      //transitionToRegister();
    }
  };
  private Runnable mRunnableTransitionToLogin = new Runnable() {
    public void run() {
      //transitionToLogin();
    }
  };
  private Runnable mRunnableTransitionToLobby = new Runnable() {
    public void run() {
      //transitionToLobby();
    }
  };

  private LoginPresenter mLoginPresenter = new LoginPresenter(this);
  private RegisterPresenter mRegisterPresenter = new RegisterPresenter(this);
  private LobbyPresenter mLobbyPresenter;
  private WaitroomPresenter waitroomPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null)
      actionBar.hide();

    mFragmentManager = getSupportFragmentManager();
    loadLoginFragmentFirstTime();

  }

  public void loadLoginFragmentFirstTime() {
    String usernameData = "";//getResources().getString(R.string.Username);
    String passwordData = "";//getResources().getString(R.string.Password);
    loadLoginFragment(usernameData, passwordData);
  }

  public void loadLoginFragment(String usernameData, String passwordData) {
    Fragment initialFragment = LoginFragment.newInstance(usernameData, passwordData);
    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.main_activity_fragment_container, initialFragment);
    fragmentTransaction.commit();
  }

  @TargetApi(21)
  private void transitionToRegisterFromLogin(String usernameData, String passwordData) {
    if (isDestroyed()) {
      return;
    }

    Fragment previousFragment = mFragmentManager.findFragmentById(R.id.main_activity_fragment_container);
    String confirmData = getResources().getString(R.string.Password);
    Fragment nextFragment = RegisterFragment.newInstance(usernameData, passwordData, confirmData);

    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

    // 1. Exit for Previous Fragment

    Fade exitFade = new Fade();
    exitFade.setDuration(SLIDE_DEFAULT_TIME);
    previousFragment.setExitTransition(exitFade);

    // 2. Shared Elements Transition

    TransitionSet enterTransitionSet = new TransitionSet();
    enterTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));
    enterTransitionSet.setDuration(MOVE_DEFAULT_TIME);
    enterTransitionSet.setStartDelay(SLIDE_DEFAULT_TIME);
    nextFragment.setSharedElementEnterTransition(enterTransitionSet);

    // 3. Enter Transition for New Fragment

    Slide enterSlide = new Slide(Gravity.LEFT);
    enterSlide.setStartDelay(SLIDE_DEFAULT_TIME);
    enterSlide.setDuration(SLIDE_DEFAULT_TIME);
    nextFragment.setEnterTransition(enterSlide);

    //TODO: Make the elements be shared between the two fragments
    View logo = previousFragment.getView().findViewById(R.id.login_fragment_logo);
    View usernameField = previousFragment.getView().findViewById(R.id.login_fragment_username_field);
    View passwordField = previousFragment.getView().findViewById(R.id.login_fragment_password_field);

    fragmentTransaction.addSharedElement(logo, logo.getTransitionName());
    fragmentTransaction.addSharedElement(usernameField, usernameField.getTransitionName());
    fragmentTransaction.addSharedElement(passwordField, passwordField.getTransitionName());
    fragmentTransaction.replace(R.id.main_activity_fragment_container, nextFragment, "CURRENT_FRAGMENT");
    fragmentTransaction.commitAllowingStateLoss();
  }

  @TargetApi(21)
  private void transitionToLoginFromRegister(String usernameData, String passwordData) {
    if (isDestroyed()) {
      return;
    }

    Fragment previousFragment = mFragmentManager.findFragmentById(R.id.main_activity_fragment_container);
    Fragment nextFragment = LoginFragment.newInstance(usernameData, passwordData);

    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

    // 1. Exit for Previous Fragment

    Fade exitFade = new Fade();
    exitFade.setDuration(SLIDE_DEFAULT_TIME);
    previousFragment.setExitTransition(exitFade);

    // 2. Shared Elements Transition

    TransitionSet enterTransitionSet = new TransitionSet();
    enterTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));
    enterTransitionSet.setDuration(MOVE_DEFAULT_TIME);
    enterTransitionSet.setStartDelay(SLIDE_DEFAULT_TIME);
    nextFragment.setSharedElementEnterTransition(enterTransitionSet);

    // 3. Enter Transition for New Fragment

    Slide enterSlide = new Slide(Gravity.RIGHT);
    enterSlide.setStartDelay(SLIDE_DEFAULT_TIME);
    enterSlide.setDuration(SLIDE_DEFAULT_TIME);
    nextFragment.setEnterTransition(enterSlide);

    //TODO: Make the elements be shared between the two fragments
    View logo = previousFragment.getView().findViewById(R.id.register_fragment_logo);
    View usernameField = previousFragment.getView().findViewById(R.id.register_fragment_username_field);
    View passwordField = previousFragment.getView().findViewById(R.id.register_fragment_password_field);

    fragmentTransaction.addSharedElement(logo, logo.getTransitionName());
    fragmentTransaction.addSharedElement(usernameField, usernameField.getTransitionName());
    fragmentTransaction.addSharedElement(passwordField, passwordField.getTransitionName());
    fragmentTransaction.replace(R.id.main_activity_fragment_container, nextFragment, "CURRENT_FRAGMENT");
    fragmentTransaction.commitAllowingStateLoss();
  }

  @TargetApi(21)
  private void transitionToLobbyFromLoginAndRegister() {
    if (isDestroyed()) {
      return;
    }

    Fragment previousFragment = mFragmentManager.findFragmentById(R.id.main_activity_fragment_container);
    Fragment nextFragment = LobbyFragment.newInstance();

    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

    // 1. Exit for Previous Fragment

    Fade exitFade = new Fade();
    exitFade.setDuration(SLIDE_DEFAULT_TIME);
    previousFragment.setExitTransition(exitFade);

    // 2. Shared Elements Transition

    TransitionSet enterTransitionSet = new TransitionSet();
    enterTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));
    enterTransitionSet.setDuration(MOVE_DEFAULT_TIME);
    enterTransitionSet.setStartDelay(SLIDE_DEFAULT_TIME);
    enterTransitionSet.removeTarget(R.id.lobby_fragment_list_view);
    nextFragment.setSharedElementEnterTransition(enterTransitionSet);

    // 3. Enter Transition for New Fragment

    Slide enterSlide = new Slide(Gravity.TOP);
    enterSlide.setStartDelay(SLIDE_DEFAULT_TIME);
    enterSlide.setDuration(SLIDE_DEFAULT_TIME);
    enterSlide.removeTarget(R.id.lobby_fragment_list_view);
    nextFragment.setEnterTransition(enterSlide);


    fragmentTransaction.replace(R.id.main_activity_fragment_container, nextFragment, "CURRENT_FRAGMENT");
    fragmentTransaction.commitAllowingStateLoss();
  }

  @TargetApi(21)
  private void transitionToLoginFromLobby() {
    if (isDestroyed()) {
      return;
    }

    Fragment previousFragment = mFragmentManager.findFragmentById(R.id.main_activity_fragment_container);
    String usernameData = "";//getResources().getString(R.string.Username);
    String passwordData = "";//getResources().getString(R.string.Password);
    Fragment nextFragment = LoginFragment.newInstance(usernameData, passwordData);

    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

    // 1. Exit for Previous Fragment

    Fade exitFade = new Fade();
    exitFade.setDuration(SLIDE_DEFAULT_TIME);
    previousFragment.setExitTransition(exitFade);

    // 2. Shared Elements Transition

    TransitionSet enterTransitionSet = new TransitionSet();
    enterTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));
    enterTransitionSet.setDuration(MOVE_DEFAULT_TIME);
    enterTransitionSet.setStartDelay(SLIDE_DEFAULT_TIME);
    enterTransitionSet.removeTarget(R.id.lobby_fragment_list_view);
    nextFragment.setSharedElementEnterTransition(enterTransitionSet);

    // 3. Enter Transition for New Fragment

    Slide enterSlide = new Slide(Gravity.BOTTOM);
    enterSlide.setStartDelay(SLIDE_DEFAULT_TIME);
    enterSlide.setDuration(SLIDE_DEFAULT_TIME);
    enterSlide.removeTarget(R.id.lobby_fragment_list_view);
    nextFragment.setEnterTransition(enterSlide);

    fragmentTransaction.replace(R.id.main_activity_fragment_container, nextFragment, "CURRENT_FRAGMENT");
    fragmentTransaction.commitAllowingStateLoss();
  }

  @TargetApi(21)
  private void transitionToWaitroomFromLobby() {
    if (isDestroyed()) {
      return;
    }

    Fragment previousFragment = mFragmentManager.findFragmentById(R.id.main_activity_fragment_container);
    Fragment nextFragment = WaitroomFragment.newInstance();

    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

    // 1. Exit for Previous Fragment

    Fade exitFade = new Fade();
    exitFade.setDuration(SLIDE_DEFAULT_TIME);
    previousFragment.setExitTransition(exitFade);

    // 2. Shared Elements Transition

    TransitionSet enterTransitionSet = new TransitionSet();
    enterTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));
    enterTransitionSet.setDuration(MOVE_DEFAULT_TIME);
    enterTransitionSet.setStartDelay(SLIDE_DEFAULT_TIME);
    nextFragment.setSharedElementEnterTransition(enterTransitionSet);

    // 3. Enter Transition for New Fragment

    Slide enterSlide = new Slide(Gravity.RIGHT);
    enterSlide.setStartDelay(SLIDE_DEFAULT_TIME);
    enterSlide.setDuration(SLIDE_DEFAULT_TIME);
    nextFragment.setEnterTransition(enterSlide);

    fragmentTransaction.replace(R.id.main_activity_fragment_container, nextFragment, "CURRENT_FRAGMENT");
    fragmentTransaction.commitAllowingStateLoss();
  }

  @TargetApi(21)
  private void transitionToLobbyFromWaitroom() {
    if (isDestroyed()) {
      return;
    }

    Fragment previousFragment = mFragmentManager.findFragmentById(R.id.main_activity_fragment_container);
    Fragment nextFragment = LobbyFragment.newInstance();

    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

    // 1. Exit for Previous Fragment

    Fade exitFade = new Fade();
    exitFade.setDuration(SLIDE_DEFAULT_TIME);
    previousFragment.setExitTransition(exitFade);

    // 2. Shared Elements Transition

    TransitionSet enterTransitionSet = new TransitionSet();
    enterTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));
    enterTransitionSet.setDuration(MOVE_DEFAULT_TIME);
    enterTransitionSet.setStartDelay(SLIDE_DEFAULT_TIME);
    enterTransitionSet.removeTarget(R.id.lobby_fragment_list_view);
    nextFragment.setSharedElementEnterTransition(enterTransitionSet);

    // 3. Enter Transition for New Fragment

    Slide enterSlide = new Slide(Gravity.LEFT);
    enterSlide.setStartDelay(SLIDE_DEFAULT_TIME);
    enterSlide.setDuration(SLIDE_DEFAULT_TIME);
    enterSlide.removeTarget(R.id.lobby_fragment_list_view);
    nextFragment.setEnterTransition(enterSlide);

    fragmentTransaction.replace(R.id.main_activity_fragment_container, nextFragment, "CURRENT_FRAGMENT");
    fragmentTransaction.commitAllowingStateLoss();
  }

  private void GuestLogin() {
    // Implement code that adds a bunch of fake games so you can go to the lobby
    ArrayList<Game> fakeGames = new ArrayList<Game>();
    Game game2 = new Game();
    game2.addPlayer(new Player("001", "Michael", PlayerColors.TURQUOISE));
    game2.addPlayer(new Player("002", "Michael", PlayerColors.BLUE));
    game2.addPlayer(new Player("003", "Eric", PlayerColors.RED));
    fakeGames.add(game2);
    Game game1 = new Game();
    game1.addPlayer(new Player("001", "Alli", PlayerColors.BLUE));
    game1.addPlayer(new Player("002", "Michael", PlayerColors.RED));
    fakeGames.add(game1);
    Game game3 = new Game();
    fakeGames.add(game3);
    Game game4 = new Game();
    game4.addPlayer(new Player("001", "Michael", PlayerColors.RED));
    game4.addPlayer(new Player("002", "Alli", PlayerColors.TURQUOISE));
    game4.addPlayer(new Player("003", "Eric", PlayerColors.ORANGE));
    game4.addPlayer(new Player("004", "Hunter", PlayerColors.BLUE));
    game4.addPlayer(new Player("005", "Jonny", PlayerColors.PURPLE));
    fakeGames.add(game4);
    ClientModel.getInstance().setGames(fakeGames);
    transitionToLobbyFromLoginAndRegister();
  }

  @Override

  protected void onDestroy()

  {
    super.onDestroy();
    mDelayedTransactionHandler.removeCallbacks(mRunnableTransitionToRegister);
  }

  public boolean handleError(String message)
  {
    if (message == null || message.equals(""))
      return false;

    Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
    toast.show();
    return true;
  }

  @Override
  public void onRegisterFragmentBackButton(String usernameData, String passwordData) {
    //mDelayedTransactionHandler.post(mRunnableTransitionToLogin);
    transitionToLoginFromRegister(usernameData, passwordData);
  }

  @Override
  public void onRegisterFragmentSignUpButton(String usernameData, String passwordData) {
    String message = null;
    if(mRegisterPresenter == null)
      message = "Something went wrong on our end.";
    else if(!mRegisterPresenter.validUsername(usernameData)) {
      message = "Invalid username";
    }
    else if(!mRegisterPresenter.validPassword(passwordData)) {
      message = "Invalid password";
    }
    else {
      mRegisterPresenter.register(usernameData, passwordData);
    }

    if (handleError(message)) {
      return;
    } else {
      onRegisterSent();
    }
  }

  @Override
  public void onRegisterSent() {
    if (mFragmentManager != null) {
      Fragment currentFragment = mFragmentManager.findFragmentByTag("CURRENT_FRAGMENT");
      if (currentFragment != null && currentFragment instanceof IRegisterFragment) {
        IRegisterFragment registerFragment = (IRegisterFragment) mFragmentManager.findFragmentByTag("CURRENT_FRAGMENT");
        registerFragment.onRegisterSent();
      }
    }
  }

  @Override
  public void onRegisterResponse(String message) {
    if (!handleError(message)) {
      transitionToLobbyFromLoginAndRegister();
    } else {
      Fragment currentFragment = mFragmentManager.findFragmentByTag("CURRENT_FRAGMENT");
      if (currentFragment != null && currentFragment instanceof IRegisterFragment) {
        IRegisterFragment registerFragment = (IRegisterFragment) mFragmentManager.findFragmentByTag("CURRENT_FRAGMENT");
        registerFragment.onRegisterResponse(message);
      }
    }
  }

  @Override
  public void onLoginFragmentSignUpButton(String usernameData, String passwordData) {
    //mDelayedTransactionHandler.post(mRunnableTransitionToRegister);
    transitionToRegisterFromLogin(usernameData, passwordData);
  }

  @Override
  public void onLoginFragmentLoginButton(String usernameData, String passwordData) {
    String message = "";
    if (usernameData.equals("Guest") && passwordData.equals("Password")) {
      GuestLogin();
    }
    else if(mLoginPresenter == null)
      message = "Something went wrong on our end";
    else if(!mLoginPresenter.validUsername(usernameData)) {
      message = "Invalid username";
    }
    else if(!mLoginPresenter.validPassword(passwordData)) {
      message = "Invalid password";
    }
    else {
      mLoginPresenter.login(usernameData, passwordData);
    }
    if (handleError(message)) {
      return;
    } else {
      onLoginSent();
    }
  }

  @Override
  public void onLoginSent() {
    if (mFragmentManager != null) {
      Fragment currentFragment = mFragmentManager.findFragmentByTag("CURRENT_FRAGMENT");
      if (currentFragment != null && currentFragment instanceof ILoginFragment)
      {
        ILoginFragment loginFragment = (ILoginFragment) mFragmentManager.findFragmentByTag("CURRENT_FRAGMENT");
        loginFragment.onLoginSent();
      }
    }
  }

  @Override
  public void onLoginResponse(String message) {
    if (!handleError(message)) {
      transitionToLobbyFromLoginAndRegister();
    } else {
      Fragment currentFragment = mFragmentManager.findFragmentByTag("CURRENT_FRAGMENT");
      if (currentFragment != null && currentFragment instanceof ILoginFragment)
      {
        ILoginFragment loginFragment = (ILoginFragment) mFragmentManager.findFragmentByTag("CURRENT_FRAGMENT");
        loginFragment.onLoginResponse(message);
      }
    }
  }

  @Override
  public void updateGameList(ArrayList<Game> newList) {
    if (mFragmentManager != null) {
      Fragment currentFragment = mFragmentManager.findFragmentByTag("CURRENT_FRAGMENT");
      if (currentFragment != null && currentFragment instanceof ILobbyFragment)
      {
        ILobbyFragment lobbyFragment = (ILobbyFragment) mFragmentManager.findFragmentByTag("CURRENT_FRAGMENT");
        lobbyFragment.updateGameList(newList);
      }
    }
  }

  @Override
  public void onLobbyFragmentLogOutButton() {

    transitionToLoginFromLobby();
  }

  @Override
  public void onLobbyFragmentStartNewGameButton() {

  }

  @Override
  public void onStartNewGameSent() {

  }

  @Override
  public void onLobbyFragmentJoinGameButton(Game game) {
    ClientModel.getInstance().setCurrentGame(game);
    transitionToWaitroomFromLobby();
  }

  @Override
  public void onGameJoinedSent() {

  }

  @Override
  public void onGameJoinedResponse() {

  }

  @Override
  public void updatePlayerList(ArrayList<Player> newList) {
    if (mFragmentManager != null) {
      IWaitroomFragment waitroomFragment = (IWaitroomFragment) mFragmentManager.findFragmentByTag("CURRENT_FRAGMENT");
      if (waitroomFragment != null) {
        waitroomFragment.updatePlayerList(newList);
      }
    }
  }

  //TODO: Implement this function correctly
  @Override
  public void updateChat() {
    if (mFragmentManager != null) {
      IWaitroomFragment waitroomFragment = (IWaitroomFragment) mFragmentManager.findFragmentByTag("CURRENT_FRAGMENT");
      if (waitroomFragment != null) {
        waitroomFragment.updateChat();
      }
    }
  }

  @Override
  public void onWaitroomFragmentColorPicked(int color) {
    //Calls waitroomPresenter
  }

  @Override
  public void onWaitroomFragmentBackoutButton() {
    ClientModel.getInstance().setCurrentGame(null);
    transitionToLobbyFromWaitroom();
  }
}