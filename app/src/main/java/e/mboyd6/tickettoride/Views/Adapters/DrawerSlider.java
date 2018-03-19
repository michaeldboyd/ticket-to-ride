package e.mboyd6.tickettoride.Views.Adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import e.mboyd6.tickettoride.R;

import static android.view.MotionEvent.INVALID_POINTER_ID;

/**
 * Created by hunte on 3/3/2018.
 */

public class DrawerSlider extends LinearLayout {

    private float mLastTouchX;
    private float mLastTouchY;
    private float mPosX;
    private float mPosY;
    private LinearLayout mLayout;
    private LinearLayout.MarginLayoutParams mMarginParams;
    private LayoutParams mLayoutParams;
    private int mMaxSliderDistance = 0;
    private ScaleGestureDetector mDetector;
    //private ScaleGestureDetector mScaleDetector = new ScaleGestureDetector();
    private boolean locked;

    class mListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    }


    public DrawerSlider(Context context) {
        super(context);
        mMarginParams = (MarginLayoutParams) getLayoutParams();
        mLayoutParams = (LayoutParams) getLayoutParams();
    }

    public DrawerSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDrawSlider(context, attrs, 0);
    }

    public DrawerSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDrawSlider(context, attrs, defStyleAttr);
    }


    private void initDrawSlider(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawerSlider, defStyle, 0);
        if (a == null) return;

        mDetector = new ScaleGestureDetector(context, new mListener());
        mMaxSliderDistance = (int) a.getDimension(R.styleable.DrawerSlider_sliderMax, 0);
        mMarginParams = (MarginLayoutParams) getLayoutParams();
        mLayoutParams = (LayoutParams) getLayoutParams();
    }

    // The ‘active pointer’ is the one currently moving our object.
    private int mActivePointerId = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return onTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        mDetector.onTouchEvent(ev);

//        System.out.println("TOUCHEVENT: x=" + mPosX + " y=" + mPosY + " maxSlider=" + mMaxSliderDistance);

        final int action = MotionEventCompat.getActionMasked(ev);

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                System.out.println("Action_down");
                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                final float x = MotionEventCompat.getX(ev, pointerIndex);
                final float y = MotionEventCompat.getY(ev, pointerIndex);

                // Remember where we started (for dragging)
                mLastTouchX = x;
                mLastTouchY = y;
                // Save the ID of this pointer (for dragging)
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);

                mPosX = 0;
                mPosY = 0;
                break;
            }

            case MotionEvent.ACTION_MOVE: {

                System.out.println("Action_move");
                // Find the index of the active pointer and fetch its position
                final int pointerIndex =
                        MotionEventCompat.findPointerIndex(ev, mActivePointerId);

                final float x = MotionEventCompat.getX(ev, pointerIndex);
                final float y = MotionEventCompat.getY(ev, pointerIndex);

                // Calculate the distance moved
                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;

                mPosX += dx;
                mPosY += dy;

                setSliderMargin();

                invalidate();

                // Remember this touch position for the next move event
                mLastTouchX = x;
                mLastTouchY = y;

                break;
            }

            case MotionEvent.ACTION_UP: {

                System.out.println("Action_up");
                mActivePointerId = INVALID_POINTER_ID;
                releaseSlider();

                mPosX = 0;
                mPosY = 0;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {

                System.out.println("Action_cancel");
                mActivePointerId = INVALID_POINTER_ID;
                releaseSlider();

                mPosX = 0;
                mPosY = 0;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {


                System.out.println("Action_up");
                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);

                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = MotionEventCompat.getX(ev, newPointerIndex);
                    mLastTouchY = MotionEventCompat.getY(ev, newPointerIndex);
                    mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                }

                releaseSlider();

                mPosX = 0;
                mPosY = 0;
                break;
            }
        }
        return true;
    }

    private void setSliderMargin() {
        if (locked)
            return;

        int slideValue = (int) mPosY;

        ConstraintLayout.LayoutParams result = (ConstraintLayout.LayoutParams) getLayoutParams();

        int newBottomMargin = result.bottomMargin - slideValue;

        if (newBottomMargin <= mMaxSliderDistance) {
            result.setMargins(0,0,0,newBottomMargin);
        } else {
            result.setMargins(0,0,0,mMaxSliderDistance);
        }

        setLayoutParams(result);
    }

    private void releaseSlider() {
        if (locked)
            return;

        int slideValue = (int) mPosY;

        ConstraintLayout.LayoutParams result = (ConstraintLayout.LayoutParams) getLayoutParams();

        int newBottomMargin = result.bottomMargin - slideValue;

        if (newBottomMargin >= mMaxSliderDistance * 0.5) {
            result.setMargins(0,0,0,mMaxSliderDistance);
        } else {
            result.setMargins(0,0,0,0);
        }

        setLayoutParams(result);
    }

    public void open() {
        ConstraintLayout.LayoutParams result = (ConstraintLayout.LayoutParams) getLayoutParams();
        result.setMargins(0,0,0,mMaxSliderDistance);
    }

    public void close() {
        ConstraintLayout.LayoutParams result = (ConstraintLayout.LayoutParams) getLayoutParams();
        result.setMargins(0,0,0,0);
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}