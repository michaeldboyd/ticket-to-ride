package e.mboyd6.tickettoride.Views.Interfaces;

import com.example.sharedcode.model.Game;

import java.util.ArrayList;

/**
 * Created by hunte on 2/7/2018.
 */

public interface IMainActivity extends ILoginFragment, IRegisterFragment{

    void updateGameList(ArrayList<Game> newList);
}
