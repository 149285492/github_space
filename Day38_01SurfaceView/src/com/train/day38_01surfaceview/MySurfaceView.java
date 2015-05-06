package com.train.day38_01surfaceview;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

/**
 * 双缓冲的高性能UI控件,子线程中可以直接访问
 * 
 * @author Administrator
 */
public class MySurfaceView extends SurfaceView {
	// 操作SrufaceView的对象
	private SurfaceHolder mHolder;

	public MySurfaceView(Context context) {
		super(context);
		init();
	}

	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		// 获取当前SurfaceView的操作对象
		mHolder = getHolder();
		setZOrderOnTop(true);
		mHolder.setFormat(PixelFormat.TRANSPARENT);
		// 设置获取当前控件的操作回调,判断操作对象是否获取成功
		mHolder.addCallback(new Callback() {
			@Override
			// 创建SurfaceView成功,创建了一个图层,可以让用户来绘画
			public void surfaceCreated(SurfaceHolder holder) {
				// 绘制一个圆,调用方法
				// drawCircle();
				// 画方框
				
				drawLineAndText(Color.CYAN);

				// Rect rect=new Rect(100,100,400,300);
				// drawRegion(rect);
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
			}
		});
	}
	// 画4条线,中间画文本
	private void drawLineAndText(final int bgColor) {
		new Thread() {
			@Override
			public void run() {
				while(true){
					// 当前线程锁定画布
					Canvas canvas = mHolder.lockCanvas();
					canvas.drawColor(Color.WHITE);
					Paint paint = new Paint();
					paint.setAntiAlias(true);
					paint.setColor(Color.WHITE);
					paint.setTextSize(30);// 绘制文本的字体大小
					paint.setStrokeWidth(10f);
					paint.setTextAlign(Align.CENTER);// 字体的对齐方式
					paint.setStyle(Paint.Style.FILL);// 实心
//					// 画水平的两条线
//					canvas.drawLine(0, 100, 600, 100, paint);
//					canvas.drawLine(0, 300, 600, 300, paint);
//					// 画垂直的亮条先
//					canvas.drawLine(100, 0, 100, 1000, paint);
//					canvas.drawLine(400, 0, 400, 1000, paint);

					// 画文字
					// 画一个区域 作为文字背景
//					Paint paintRect = new Paint();
//					paintRect.setColor(bgColor);
//					Rect rect = new Rect(0, 0,1000, 1000);// 文字所在的区域
//					canvas.drawRect(rect, paintRect);
					// 获取字体测量的工具类
					FontMetrics fm = paint.getFontMetrics();
					int offset = (int) Math.abs(((fm.ascent + fm.descent) / 2));
					paint.setColor(Color.BLACK);
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					int wight=getMeasuredWidth();
					int height=getMeasuredHeight();
					canvas.drawText("剩余时间"+sdf.format(new Date()), wight/2, height/2 + offset, paint);

					// 当前线程解锁画布
					mHolder.unlockCanvasAndPost(canvas);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

//	@Override//模仿点击事件 
//	public boolean onTouchEvent(MotionEvent event) {
//		int action = event.getAction();
//		int ex = (int) event.getX();
//		int ey = (int) event.getY();
//		Rect rect = new Rect(100, 100, 400, 300);
//		if (rect.contains(ex, ey)) {
//			Toast.makeText(getContext(), "点到我了", Toast.LENGTH_LONG).show();
//			if (action == MotionEvent.ACTION_DOWN|action==MotionEvent.ACTION_MOVE) {
//				// 重新绘制内容
//				drawLineAndText(Color.RED);
//			} else {
//				// 重新绘制成开始的内容
//				drawLineAndText(Color.CYAN);
//			}
//		}
//		return true;
//	}
	

	@Override 
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
		// 这里要计算一下控件的实际大小，然后调用setMeasuredDimension来设置
	  int width = this.getMeasuredSize(widthMeasureSpec, true);
	  int height = this.getMeasuredSize(heightMeasureSpec, false);
	  setMeasuredDimension(width, height);
	} 

	/**
		* 计算控件的实际大小
		* @param length onMeasure方法的参数，widthMeasureSpec或者heightMeasureSpec
		* @param isWidth 是宽度还是高度
		* @return int 计算后的实际大小
		*/
	private int getMeasuredSize(int length, boolean isWidth){
		// 模式
		int specMode = MeasureSpec.getMode(length);
		// 尺寸
		int specSize = MeasureSpec.getSize(length);
		// 计算所得的实际尺寸，要被返回
		int retSize = 0;        
		// 得到两侧的padding（留边）
		int padding = (isWidth? getPaddingLeft()+getPaddingRight():getPaddingTop()+getPaddingBottom());
	        
		// 对不同的指定模式进行判断
		if(specMode==MeasureSpec.EXACTLY){  // 显式指定大小，如40dp或fill_parent
			retSize = specSize;
		}else{                              // 如使用wrap_content
			retSize = (isWidth? this.getWidth()+padding : this.getHeight()+padding);
			if(specMode==MeasureSpec.UNSPECIFIED){
			retSize = Math.min(retSize, specSize);
			}
		}        

		return retSize;
	}
}
