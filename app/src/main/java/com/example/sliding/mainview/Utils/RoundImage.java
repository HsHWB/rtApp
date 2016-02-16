package com.example.sliding.mainview.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

/**
 * Created by Administrator on 2016/2/1.
 */
public class RoundImage {
    public static Bitmap getRoundBitmap(Bitmap bitmap){

        int width = bitmap.getWidth();//圆形图片的宽
        int height = bitmap.getHeight();//圆形图片的长
        int r = 0;//正方形的边长

        if(width > height)//取最短的作为边长
            r = height;
        else
            r = width;

        Bitmap backRound = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);//构建一个bitmap
        Canvas canvas = new Canvas(backRound);//一个新的canvas，在backRound上面画图
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置边缘光滑，去掉锯齿
        RectF rect = new RectF(0, 0, r, r);//宽和长相等，即为正方形
        canvas.drawRoundRect(rect, r/2, r/2, paint);//通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径且都为r/2时，画出来的圆角矩形及时圆形
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, null, rect, paint);
        return backRound;
    }
}
