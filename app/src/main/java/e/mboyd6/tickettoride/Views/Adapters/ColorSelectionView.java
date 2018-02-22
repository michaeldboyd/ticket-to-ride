package e.mboyd6.tickettoride.Views.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * Created by hunte on 2/16/2018.
 */

public class ColorSelectionView extends android.support.v7.widget.AppCompatButton {
    private int backgroundID = 0;
    Context context;

    public ColorSelectionView(Context context) {
        super(context);
        this.context = context;
    }
    public ColorSelectionView(Context context, AttributeSet attrs) { // this constructor will be called by inflater for creating object instance
        super(context, attrs);
        this.context = context;
    }

    @Override
    public void setBackgroundResource(int id) {
        backgroundID = id;
        super.setBackgroundResource(id);
    }

    public int getBackgroundID() {
        return backgroundID;
    }

    public void setBackgroundID(int backgroundID) {
        this.backgroundID = backgroundID;
    }
}
