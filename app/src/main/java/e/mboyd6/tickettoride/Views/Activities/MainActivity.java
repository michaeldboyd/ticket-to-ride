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

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

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
import e.mboyd6.tickettoride.Views.Interfaces.IMainActivity;
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

  private LoginPresenter mLoginPresenter = new LoginPresenter();
  private RegisterPresenter mRegisterPresenter = new RegisterPresenter();
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
    String usernameData = getResources().getString(R.string.Username);
    String passwordData = getResources().getString(R.string.Password);
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
    String usernameData = getResources().getString(R.string.Username);
    String passwordData = getResources().getString(R.string.Password);
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
  private void transitionToWaitroomFromLobby(Game game) {
    if (isDestroyed()) {
      return;
    }

    Fragment previousFragment = mFragmentManager.findFragmentById(R.id.main_activity_fragment_container);
    Fragment nextFragment = WaitroomFragment.newInstance(game);

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
    Game game1 = new Game();
    game1.addPlayer("001");
    game1.addPlayer("002");
    game1.addPlayer("003");
    fakeGames.add(game1);
    Game game2 = new Game();
    game2.addPlayer("003");
    game2.addPlayer("004");
    fakeGames.add(game2);
    Game game3 = new Game();
    fakeGames.add(game3);
    ClientModel.getInstance().setGames(fakeGames);
    transitionToLobbyFromLoginAndRegister();
  }

  @Override

  protected void onDestroy()

  {
    super.onDestroy();
    mDelayedTransactionHandler.removeCallbacks(mRunnableTransitionToRegister);
  }

  @Override
  public void onRegisterFragmentBackButton(String usernameData, String passwordData) {
    //mDelayedTransactionHandler.post(mRunnableTransitionToLogin);
    transitionToLoginFromRegister(usernameData, passwordData);
  }

  @Override
  public String onRegisterFragmentSignUpButton(String usernameData, String passwordData) {
    if(mRegisterPresenter == null)
      return "Something went wrong on our end.";
    if(!mRegisterPresenter.validUsername(usernameData)) {
      return "Invalid username";
    }
    if(!mRegisterPresenter.validPassword(passwordData)) {
      return "Invalid password";
    }
    if(!mRegisterPresenter.register(usernameData, passwordData))
    {
      return "Server error, or connection does not exist";
    }
    transitionToLobbyFromLoginAndRegister();
    return "Success!";
  }

  @Override
  public void onLoginFragmentSignUpButton(String usernameData, String passwordData) {
    //mDelayedTransactionHandler.post(mRunnableTransitionToRegister);
    transitionToRegisterFromLogin(usernameData, passwordData);
  }

  @Override
  public String onLoginFragmentLoginButton(String usernameData, String passwordData) {
    if (usernameData.equals("Guest") && passwordData.equals("Password"))
    {
      GuestLogin();
      return "Success!";
    }
    if(mLoginPresenter == null)
      return "Something went wrong on our end";
    if(!mLoginPresenter.validUsername(usernameData)) {
      return "Invalid username";
    }
    if(!mLoginPresenter.validPassword(passwordData)) {
      return "Invalid password";
    }
    if (!mLoginPresenter.login(usernameData, passwordData)) {
      return "Server error, or connection does not exist";
    }
    transitionToLobbyFromLoginAndRegister();
    return "Success!";
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
  public void onLobbyFragmentJoinGameButton(Game game) {
    transitionToWaitroomFromLobby(game);
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
    transitionToLobbyFromWaitroom();
  }
}