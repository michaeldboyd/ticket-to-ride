package e.mboyd6.tickettoride.Views.Adapters;

import android.widget.Button;

import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;

import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;

/**
 * Created by hunte on 3/4/2018.
 */

public class ClaimRouteButtonState {
    public Route selectedRoute = null;
    public Player player = null;
    public void enter(BoardFragment boardFragment, Button claimRouteButton) {}
    public void onClick(BoardFragment boardFragment) {}
    public void setRoute(BoardFragment boardFragment, Route route, Player player) {}
}
