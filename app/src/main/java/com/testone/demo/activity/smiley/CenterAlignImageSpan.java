package com.testone.demo.activity.smiley;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.style.ImageSpan;

public class CenterAlignImageSpan extends ImageSpan {

    public CenterAlignImageSpan(Context context, Bitmap b) {
        super(context, b);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                     @NonNull Paint paint) {
//        Log.d("CenterAlignImageSpan", "1, draw() called with: top = [" + top + "], y = [" + y + "], bottom = [" + bottom + "]");
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
//        Log.d("CenterAlignImageSpan", "3, fm:" + fm);
        final Drawable d = getDrawable();
//        Log.d("CenterAlignImageSpan", "4, d.getBounds():" + d.getBounds());
        canvas.save();
        int transY = (y + fm.descent + y + fm.ascent) / 2 - d.getBounds().bottom / 2;//计算y方向的位移
        canvas.translate(x, transY); // 绘制图片位移一段距离
        d.draw(canvas);
        canvas.restore();
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        final Drawable d = getDrawable();
        final Rect rect = d.getBounds();
        if (fm != null) {
            final Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
            final int fontHeight = fontMetricsInt.bottom - fontMetricsInt.top;
            final int drawableHeight = rect.bottom - rect.top;
            final int top = drawableHeight / 2 - fontHeight / 4;
            final int bottom = drawableHeight / 2 + fontHeight / 4;
            fm.ascent = -bottom;
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;
        }
        return rect.right;
    }
}
