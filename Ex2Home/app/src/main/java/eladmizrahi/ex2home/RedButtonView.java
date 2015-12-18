package eladmizrahi.ex2home;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by il731158 on 14/12/2015.
 */
public class RedButtonView extends View
{
    private int left, top, width, height;
    private int recX, recY, recWidth = 200, recHeight = 100;
    private LinkedList<Circle> circles;
    private int complexity;
    SharedPreferences prefs;
    private Paint paint;

    public RedButtonView(Context context)
    {
        super(context);
        init(null, 0, context);
    }

    public RedButtonView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0, context);
    }

    public RedButtonView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle, context);
    }

    private void init(AttributeSet attrs, int defStyle, Context context)
    {
        // initialize paint and path
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        complexity = prefs.getInt("complexity", 0);
        circles = new LinkedList<Circle>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH)
    {
        super.onSizeChanged(w, h, oldW, oldH);
        left = getPaddingLeft();
        top = getPaddingTop();
        width = w - (getPaddingLeft() + getPaddingRight());
        height = h - (getPaddingTop() + getPaddingBottom());
        generateCircles();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        drawRectangle(canvas);
        drawCircles(canvas);
    }

    private boolean generateCircle()
    {
        Random rand = new Random();
        int x = Circle.getRadius() + rand.nextInt(width - 2*Circle.getRadius());
        int y = Circle.getRadius() + rand.nextInt(height - 2*Circle.getRadius());
        Circle newCircle = new Circle(x,y);
        for (Circle c: circles)
        {
            if (c.isCollide(newCircle) || c.isCollide(recX, recY, recWidth, recHeight))
            {
                return false;
            }
        }
        circles.add(newCircle);
        return true;
    }

    private void drawRectangle(Canvas canvas)
    {
        if (!MainActivity.isOnHold)
        {
            Random rand = new Random();
            recX = rand.nextInt(width - recWidth);
            recY = rand.nextInt(height - recHeight);
            canvas.drawRect(recX, recY, recX +recWidth, recY+recHeight, paint);
        }

    }

    private void drawCircles(Canvas canvas)
    {
        if (!MainActivity.isOnHold)
        {
            for (Circle c : circles)
            {
                c.drawCircle(canvas, paint);
            }
        }

    }

    public void generateCircles()
    {
        complexity = prefs.getInt("complexity", 0);
        circles.clear();
        for (int i = 0; i < complexity; ++i)
        {
            while (!generateCircle());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() >= recX && event.getX() <= recX + recWidth && event.getY() >= recY && event.getY() <= recY + recHeight)
                {
                    invalidate();
                    generateCircles();
                    return true;
                }
                break;
        }
        return false;
    }
}
