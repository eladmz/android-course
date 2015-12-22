package eladmizrahi.ex2home;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by il731158 on 15/12/2015.
 */
public class Circle
{
    private static int radius = 70;
    private int centerX, centerY;

    public Circle(int x, int y)
    {
        centerX = x;
        centerY = y;
    }

    public void drawCircle(Canvas canvas, Paint paint)
    {
        canvas.drawCircle(centerX, centerY, radius, paint);
    }

    public boolean isCollide(Circle c)
    {
        double distance = Math.pow(centerX - c.getCenterX(),2) + Math.pow(centerY - c.getCenterY(), 2);
        if (Math.sqrt(distance) <= 2*radius)
        {
            return true;
        }
        return false;
    }

    public boolean isCollide(int x, int y, int width, int height)
    {
        if (centerY + radius < y || centerY - radius > y + height || centerX + radius < x || centerX - radius > x + width)
            return false;
        return true;
    }

    public static int getRadius()
    {
        return radius;
    }

    public int getCenterX()
    {
        return centerX;
    }

    public int getCenterY()
    {
        return centerY;
    }
}
