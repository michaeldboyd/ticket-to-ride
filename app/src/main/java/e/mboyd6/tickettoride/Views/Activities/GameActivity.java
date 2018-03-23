package e.mboyd6.tickettoride.Views.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.sharedcode.model.ChatMessage;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import e.mboyd6.tickettoride.Communication.SocketManager;
import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Presenters.ChatPresenter;
import e.mboyd6.tickettoride.Presenters.LobbyPresenter;
import e.mboyd6.tickettoride.Presenters.LoginPresenter;
import e.mboyd6.tickettoride.Presenters.RegisterPresenter;
import e.mboyd6.tickettoride.Presenters.WaitroomPresenter;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;
import e.mboyd6.tickettoride.Views.Fragments.GameChatFragment;
import e.mboyd6.tickettoride.Views.Fragments.HandFragment;
import e.mboyd6.tickettoride.Views.Fragments.HistoryFragment;
import e.mboyd6.tickettoride.Views.Fragments.LobbyFragment;
import e.mboyd6.tickettoride.Views.Fragments.LoginFragment;
import e.mboyd6.tickettoride.Views.Fragments.RegisterFragment;
import e.mboyd6.tickettoride.Views.Fragments.ScoreFragment;
import e.mboyd6.tickettoride.Views.Fragments.WaitroomFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IBoardFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IChatFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IGameActivity;
import e.mboyd6.tickettoride.Views.Interfaces.ILobbyFragment;
import e.mboyd6.tickettoride.Views.Interfaces.ILoginFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IMainActivity;
import e.mboyd6.tickettoride.Views.Interfaces.IRegisterFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IWaitroomFragment;

public class GameActivity extends AppCompatActivity
        implements IGameActivity {

    private FragmentManager mFragmentManager;
    private View mBar;
    private boolean uiLocked;
    private boolean startGame;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        mFragmentManager = getSupportFragmentManager();
        mBar = findViewById(R.id.game_activity_bar);

        Intent intent = getIntent();
        startGame = intent.getBooleanExtra("START_GAME", false);

        BoardFragment boardFragment = new BoardFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("START_GAME", startGame);
        boardFragment.setArguments(bundle);
        startGame = false;

        mFragmentManager
                .beginTransaction()
                .replace(R.id.game_fragment_container, boardFragment, "CURRENT_FRAGMENT")
                .commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadBoardFragment() {

        mFragmentManager
                .beginTransaction()
                .replace(R.id.game_fragment_container, new BoardFragment(), "CURRENT_FRAGMENT")
                .commit();
    }

    private void loadHandFragment() {

        mFragmentManager
                .beginTransaction()
                .replace(R.id.game_fragment_container, new HandFragment(), "CURRENT_FRAGMENT")
                .commit();
    }
    private void loadChatFragment() {

        mFragmentManager
                .beginTransaction()
                .replace(R.id.game_fragment_container, new GameChatFragment(), "CURRENT_FRAGMENT")
                .commit();
    }

    private void loadScoreFragment() {

        mFragmentManager
                .beginTransaction()
                .replace(R.id.game_fragment_container, new ScoreFragment(), "CURRENT_FRAGMENT")
                .commit();
    }

    private void loadHistoryFragment() {

        mFragmentManager
                .beginTransaction()
                .replace(R.id.game_fragment_container, new HistoryFragment(), "CURRENT_FRAGMENT")
                .commit();
    }

    @Override
    public void onIsTypingChanged(boolean isTyping) {

    }

    @Override
    public void onChatReceived(ArrayList<ChatMessage> chatMessages, int unreadMessages) {

    }

    //This is connected through the layout attribute "onClick" in the XML file
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onTabClicked(View view) {
        if (uiLocked) {
            Toast.makeText(this, "Finish your action before switching tabs.", Toast.LENGTH_SHORT).show();
            return;
        }
            // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        Class typeOfFragment = mFragmentManager.findFragmentByTag("CURRENT_FRAGMENT").getClass();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.game_tab_board:
                if (checked && !typeOfFragment.equals(BoardFragment.class)) {
                    mBar.setBackgroundResource(R.drawable.tabs_off_white_bar);
                    loadBoardFragment();
                }
                break;
            case R.id.game_tab_hand:
                if (checked && !typeOfFragment.equals(HandFragment.class)) {
                    mBar.setBackgroundResource(R.drawable.tabs_salmon_bar);
                    loadHandFragment();
                }
                break;
            case R.id.game_tab_chat:
                if (checked && !typeOfFragment.equals(GameChatFragment.class)) {
                    mBar.setBackgroundResource(R.drawable.tabs_goldenrod_bar);
                    loadChatFragment();
                }
                break;
            case R.id.game_tab_score:
                if (checked && !typeOfFragment.equals(ScoreFragment.class)) {
                    mBar.setBackgroundResource(R.drawable.tabs_teal_bar);
                    loadScoreFragment();
                }
                break;
            case R.id.game_tab_history:
                if (checked && !typeOfFragment.equals(HistoryFragment.class)) {
                    mBar.setBackgroundResource(R.drawable.tabs_babyblue_bar);
                    loadHistoryFragment();
                }
                break;
        }
    }

    @Override
    public void setUiLocked(boolean uiLocked) {
        this.uiLocked = uiLocked;
    }

    @Override
    public void changeToVictoryActivity(List<Player> playerListByScore){
        //TODO: HUNTER, close this activity and open the VictoryActivity
    }
}