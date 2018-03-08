package e.mboyd6.tickettoride.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sharedcode.model.Player;

import java.util.ArrayList;

import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Fragments.HistoryFragment;
import e.mboyd6.tickettoride.Views.Fragments.ScoreFragment;

/**
 * Created by hunte on 2/8/2018.
 */

public class HistoryListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final HistoryFragment historyFragment;
    private int position;
    private View convertView;
    private ViewGroup parent;

    public HistoryListAdapter(Context context, ArrayList<String> events, HistoryFragment historyFragment) {
        super(context, 0, events);
        this.context = context;
        this.historyFragment = historyFragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.position = position;
        this.convertView = convertView;
        this.parent = parent;

        // Get the data item for this position
        final String event_text = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_history, parent, false);
        }
        TextView event = convertView.findViewById(R.id.event);

        if (event_text != null)
            event.setText(event_text);

        return convertView;
    }
}