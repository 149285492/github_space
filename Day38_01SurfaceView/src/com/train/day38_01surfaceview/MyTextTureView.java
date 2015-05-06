package com.train.day38_01surfaceview;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.TextureView;

public class MyTextTureView extends TextureView implements TextureView.SurfaceTextureListener{
	public MyTextTureView(Context context) {
		super(context);
		setSurfaceTextureListener(this);
	}
	public MyTextTureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setSurfaceTextureListener(this);
	}
	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
			int height) {
		drawLineAndText(Color.CYAN);
	}
	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
			int height) {
	}
	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		return false;
		
	}
	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
	}
	// 画4条线,中间画文本
	private void drawLineAndText(final int bgColor) {
		new Thread() {
			@Override
			public void run() {
				while(true){
					// 当前线程锁定画布
					Canvas canvas = lockCanvas();
					canvas.drawColor(Color.WHITE);
					Paint paint = new Paint();
					paint.setAntiAlias(true);
					paint.setColor(Color.WHITE);
					paint.setTextSize(30);// 绘制文本的字体大小
					paint.setStrokeWidth(10f);
					paint.setTextAlign(Align.CENTER);// 字体的对齐方式
					paint.setStyle(Paint.Style.FILL);// 实心

					// 获取字体测量的工具类
					FontMetrics fm = paint.getFontMetrics();
					int offset = (int) Math.abs(((fm.ascent + fm.descent) / 2));
					paint.setColor(Color.BLACK);
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					int wight=getMeasuredWidth();
					int height=getMeasuredHeight();
					canvas.drawText("剩余时间"+sdf.format(new Date()), wight/2, height/2 + offset, paint);

					// 当前线程解锁画布
					unlockCanvasAndPost(canvas);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

}
