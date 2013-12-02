package com.adk.touchevent;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends Activity {

    private SparseArray<PointF> mActivePointers;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mActivePointers = new SparseArray<PointF>();
        MultiTouchView multiTouchView = (MultiTouchView) findViewById(R.id.container);

        multiTouchView.setOnHoverListener(new View.OnHoverListener() {

            @Override
            public boolean onHover(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_HOVER_ENTER:
                        Log.i("ARMAN-Hover-Enter>>> ", event.getX() + "-" + event.getY());
                        new SendTask().execute(event.getX() + "-" + event.getY() + "-0");
                        break;
                    case MotionEvent.ACTION_HOVER_MOVE:
                        Log.i("ARMAN-Hover-Move>>> ", event.getX() + "-" + event.getY());
                        new SendTask().execute(event.getX() + "-" + event.getY() + "-0");
                        break;
                    case MotionEvent.ACTION_HOVER_EXIT:
                        Log.i("ARMAN-Hover-Exit>>> ", event.getX() + "-" + event.getY());
                        new SendTask().execute(event.getX() + "-" + event.getY() + "-0");
                        break;
                }
                return false;
            }
        });

        multiTouchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // get pointer index from the event object
                int pointerIndex = event.getActionIndex();

                // get pointer ID
                int pointerId = event.getPointerId(pointerIndex);

                // get masked (not specific to a pointer) action
                int maskedAction = event.getActionMasked();

                switch (maskedAction) {

                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN: {
                        // We have a new pointer. Lets add it to the list of pointers

                        PointF f = new PointF();
                        f.x = event.getX(pointerIndex);
                        f.y = event.getY(pointerIndex);
                        Log.i("ARMAN-TOUCH-CLICK", "at position " + f.x + "-" + f.y);
                        new SendTask().execute(f.x + "-" + f.y + "-1");
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: { // a pointer was moved
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        break;
                    }
                }

                return true;
            }
        });

        /*multiTouchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // get pointer index from the event object
                int pointerIndex = event.getActionIndex();

                // get pointer ID
                int pointerId = event.getPointerId(pointerIndex);

                // get masked (not specific to a pointer) action
                int maskedAction = event.getActionMasked();

                switch (maskedAction) {

                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN: {
                        // We have a new pointer. Lets add it to the list of pointers

                        PointF f = new PointF();
                        f.x = event.getX(pointerIndex);
                        f.y = event.getY(pointerIndex);
                        Log.i("ARMAN>>>> ", f.x + "-" + f.y);
                        mActivePointers.put(pointerId, f);
//                        new SendTask().execute(f.x + "-" + f.y);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: { // a pointer was moved
                        for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                            PointF point = mActivePointers.get(event.getPointerId(i));
                            if (point != null) {
                                point.x = event.getX(i);
                                point.y = event.getY(i);
                                Log.i("ARMAN>>>> ", point.x + "-" + point.y);
                                new SendTask().execute(point.x + "-" + point.y);
                            }
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        break;
                    }
                }

                return true;
            }
        });*/
    }


}
