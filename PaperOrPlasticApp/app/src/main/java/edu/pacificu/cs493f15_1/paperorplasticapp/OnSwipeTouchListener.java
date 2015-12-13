package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * Created by sull0678 on 11/16/2015.
 */
//https://github.com/sohambannerjee8/SwipeListView/blob/master/app/src/main/java/com/nisostech/soham/MainActivity.java
public class OnSwipeTouchListener implements View.OnTouchListener
{
    ListView list;
    private GestureDetector gestureDetector;
    protected MotionEvent mLastOnDownEvent = null;

    private Context context;

    public OnSwipeTouchListener(Context ctx, ListView list) {
        gestureDetector = new GestureDetector(ctx, new GestureListener());
        context = ctx;
        this.list = list;
    }

    public OnSwipeTouchListener() {
        super();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public void onSwipeRight(int pos) {

    }

    public void onSwipeLeft(int pos) {

    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {

            mLastOnDownEvent = e;
            return super.onDown(e);

        }

        private int getPosition(MotionEvent e1) {
            return list.pointToPosition((int) e1.getX(), (int) e1.getY());
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if (e1==null) {
                e1 = mLastOnDownEvent;
            }
            if (e1==null || e2==null) {
                return false;
            }
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0)
                    onSwipeRight(getPosition(e1));
                else
                    onSwipeLeft(getPosition(e1));
                return true;
            }
            return false;
        }

    }
}
