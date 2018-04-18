package e.mboyd6.tickettoride.Views.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.Button;
import android.widget.Toast;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;

import java.util.ArrayList;

import e.mboyd6.tickettoride.Communication.SocketManager;
import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Presenters.ChatPresenter;
import e.mboyd6.tickettoride.Presenters.LobbyPresenter;
import e.mboyd6.tickettoride.Presenters.LoginPresenter;
import e.mboyd6.tickettoride.Presenters.RegisterPresenter;
import e.mboyd6.tickettoride.Presenters.WaitroomPresenter;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Fragments.LobbyFragment;
import e.mboyd6.tickettoride.Views.Fragments.LoginFragment;
import e.mboyd6.tickettoride.Views.Fragments.RegisterFragment;
import e.mboyd6.tickettoride.Views.Fragments.WaitroomFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IChatFragment;
import e.mboyd6.tickettoride.Views.Interfaces.ILobbyFragment;
import e.mboyd6.tickettoride.Views.Interfaces.ILoginFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IMainActivity;
import e.mboyd6.tickettoride.Views.Interfaces.IRegisterFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IWaitroomFragment;

public class MainActivity extends AppCompatActivity
        implements IMainActivity {
  private static final long MOVE_DEFAULT_TIME = 1000;
  private static final long SLIDE_DEFAULT_TIME = 200;
  private FragmentManager mFragmentManager;
  private Handler mDelayedTransactionHandler = new Handler();
  private boolean straightToLobby = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null)
      actionBar.hide();

    Intent intent = getIntent();
    straightToLobby = intent.getBooleanExtra("STRAIGHT_TO_LOBBY", false);


    mFragmentManager = getSupportFragmentManager();

    if (straightToLobby) {
      loadLobbyFragment();
    } else {
      loadLoginFragmentFirstTime();
    }

    SocketManager.ConnectSocket(); //ws://192.168.255.178:8080/echo/
  }

  public void loadLoginFragmentFirstTime() {
    String usernameData = "";//getResources().getString(R.string.Username);
    String passwordData = "";//getResources().getString(R.string.Password);
    loadLoginFragment(usernameData, passwordData);
  }

  public void loadLoginFragment(String usernameData, String passwordData) {
    Fragment initialFragment = LoginFragment.newInstance(usernameData, passwordData);
    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.main_activity_fragment_container, initialFragment, "CURRENT_FRAGMENT");
    fragmentTransaction.commit();
  }

  public void loadLobbyFragment() {
    Fragment initialFragment = LobbyFragment.newInstance();
    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.main_activity_fragment_container, initialFragment, "CURRENT_FRAGMENT");
    fragmentTransaction.commit();
  }

  @TargetApi(21)
  public void transitionToRegisterFromLogin(String usernameData, String passwordData) {
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
  public void transitionToLoginFromRegister(String usernameData, String passwordData) {
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
  public void transitionToLobbyFromLoginAndRegister() {
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
  public void transitionToLoginFromLobby() {
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
  public void transitionToWaitroomFromLobby() {
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
  public void transitionToLobbyFromWaitroom() {
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

  @Override
  protected void onDestroy()
  {
    super.onDestroy();
  }

  public boolean handleError(String message)
  {
    if (message == null || message.equals(""))
      return false;

    Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
    toast.show();
    return true;
  }

}