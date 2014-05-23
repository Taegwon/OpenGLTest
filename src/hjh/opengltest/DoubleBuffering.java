package hjh.opengltest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

public class DoubleBuffering extends Activity {

	public void onCreate(Bundle b) {
		super.onCreate(b);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new Panel(this));
	}

	class Panel extends SurfaceView implements SurfaceHolder.Callback {
		public Panel(Context context) {
			super(context);
			getHolder().addCallback(this);
			vThread = new MyThread(getHolder(), this);
		}

		private MyThread vThread;
		private int x, y, lcdWidth, lcdHeight;
		private int xMovingLength = 20, yMovingLength = 20;
		Paint vPaint = new Paint(0);
		Bitmap backGroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.my_report);
		Bitmap movedImage = BitmapFactory.decodeResource(getResources(), R.drawable.icon36);
		
		protected void onDraw(Canvas c) {
			x += xMovingLength;
			y += yMovingLength;
			if (x < 0 || x > lcdWidth)
				xMovingLength = -xMovingLength;
			if (y < 0 || y > lcdHeight)
				yMovingLength = -yMovingLength;
			c.drawRect(0, 0, lcdWidth, lcdHeight, vPaint);
			c.drawBitmap(backGroundImage, 0, 0, null);
			c.drawBitmap(movedImage, x, y, null);
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			vThread.setRunning(true);
			vThread.start();
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			lcdWidth = width;
			lcdHeight = height;
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			boolean retry = true;
			vThread.setRunning(false);
			while (retry) {
				try {
					vThread.join();
					retry = false;
				} catch (InterruptedException e) {

				}
			}
		}
	}

	class MyThread extends Thread {

		private SurfaceHolder _surfaceHolder;
		private Panel _panel;
		private boolean _run = false;

		public MyThread(SurfaceHolder holder, Panel panel) {
			_surfaceHolder = holder;
			_panel = panel;
		}

		public void setRunning(boolean b) {
			_run = b;
		}

		public void run() {
			Canvas c;

			while (_run) {
				c = null;
				try {
					c = _surfaceHolder.lockCanvas(null);
					synchronized (_surfaceHolder) {
						_panel.onDraw(c);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (c != null) {
						_surfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}
	}
}
