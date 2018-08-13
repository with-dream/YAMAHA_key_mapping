package jp.kshoji.driver.midi.sample.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import jp.kshoji.driver.midi.sample.R;

/**
 * Created by ms on 2018/8/11.
 */

public class WuView extends View {
    private float offset = 30;
    private Paint paint, paintFu;
    private Bitmap bmGao, bmDi;
    private int yin, pos, diao;
    private boolean superHei;

    public WuView(Context context) {
        super(context);
        init();
    }

    public WuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(Color.WHITE);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3.0f);

        paintFu = new Paint();
        paintFu.setColor(Color.BLACK);
        paintFu.setStrokeWidth(3.0f);
        paintFu.setAntiAlias(true);
        paintFu.setStyle(Paint.Style.STROKE);

        bmGao = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.gao);
        bmDi = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.di);
    }

    /**
     * yin 0低音  其他高音
     * position 位置 第三线为0
     */
    public void setPos(int yin, int position, boolean superHei) {
        this.yin = yin;
        this.pos = position;
        this.superHei = superHei;
    }

    public void setPos(int yin, int position) {
        setPos(yin, position, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float w = getWidth() / 2f;
        float h = getHeight() / 2f;
        canvas.translate(w, h);
        for (int i = 0; i < 5; i++) {
            float y = (2 - i) * offset;
            canvas.drawLine(-w / 2, y, w / 2, y, paint);
        }

        drawBm(canvas, (int) w);

        canvas.drawCircle(0, offset / 2 * (this.pos), 10, paintFu);

        if (pos > 5) {
            int linNum = (this.pos - 4) / 2;
            for (int i = 0; i < linNum; i++) {
                float y = (3 + i) * offset;
                canvas.drawLine(-offset, y, offset, y, paint);
            }
        } else if (pos < -5) {
            int linNum = -(this.pos + 4) / 2;
            for (int i = 0; i < linNum; i++) {
                float y = -(3 + i) * offset;
                canvas.drawLine(-offset, y, offset, y, paint);
            }

            if(superHei) {
                canvas.drawLine(-offset*2, -9*offset, offset*2, -9*offset, paint);
            }
        }
    }

    private void drawBm(Canvas canvas, int w) {
        Bitmap bm = this.yin == 0 ? bmDi : bmGao;

        // 得到图像选区 和 实际绘制位置
        int h = (int) -offset * 2;
        Rect src = new Rect(-w / 2, h, -w / 2 + 60, h + 120);

        // 绘制
        canvas.drawBitmap(bm, null, src, null);
    }
}
