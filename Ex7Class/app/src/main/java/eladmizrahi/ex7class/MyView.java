package eladmizrahi.ex7class;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by il731158 on 8/12/2015.
 */
public class MyView extends View
{
    private int right, top, width, height;
    private Paint paint;
    private Path path;

    public MyView(Context context)
    {
        super(context);
        init(null, 0);
    }

    public MyView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        // initialize paint and path
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH)
    {
        super.onSizeChanged(w, h, oldW, oldH);
        right = getPaddingRight();
        top = getPaddingTop();
        width = w - (getPaddingLeft() + getPaddingRight());
        height = h - (getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.moveTo(width / 2, top);
        path.lineTo(width / 2, top + height/4);
        path.lineTo(width/2 + width/3, top+height/4);
        path.lineTo(width/2 + width/3, top+height/4+height/3);
        path.lineTo(width / 2, top + height / 4 + height / 3 + height / 5);
        path.lineTo(width/2 - width/3, top+height/4+height/3);
        path.lineTo(width/2 - width/3, top+height/4);
        path.lineTo(width/2 , top+height/4);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Random rand = new Random();
                paint.setColor(rand.nextInt(Integer.MAX_VALUE));
                invalidate();
                break;
        }
        return true;
    }
}
