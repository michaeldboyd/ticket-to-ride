package e.mboyd6.tickettoride.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;

import java.util.ArrayList;

import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Fragments.LobbyFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IHandFragment;

/**
 * Created by hunte on 2/8/2018.
 */

public class DestinationCardAdapter extends ArrayAdapter<DestinationCard> {
    private View convertView;
    private ViewGroup parent;
    private TextView destinationAmountValue;
    private TextView destinationNameValue;
    private Context context;
    private IHandFragment handFragment;
    private int position;

    public DestinationCardAdapter(Context context, ArrayList<DestinationCard> destinationCards, IHandFragment handFragment) {
        super(context, 0, destinationCards);
        this.context = context;
        this.handFragment = handFragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.position = position;
        this.convertView = convertView;
        this.parent = parent;
        // Get the data item for this position
        DestinationCard destinationCard = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_destination, parent, false);
        }
        destinationAmountValue = convertView.findViewById(R.id.destination_amount_value);
        destinationNameValue = convertView.findViewById(R.id.destination_name_value);
        String destinationCardValueText = "" + destinationCard.getPoints();
        destinationAmountValue.setText(destinationCardValueText);
        String destinationNameValueText = destinationCard.getStartCity() + " to " + destinationCard.getEndCity();
        destinationNameValue.setText(destinationNameValueText);
        return convertView;
    }
}