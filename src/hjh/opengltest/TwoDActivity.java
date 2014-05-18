package hjh.opengltest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

@SuppressLint("DrawAllocation")
public class TwoDActivity extends Activity {
	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new Panel(this));
	}

	class Panel extends View {
		public Panel(Context context) {
			super(context);
		}

		@SuppressLint("DrawAllocation")
		public void onDraw(Canvas canvas) {

			Paint mPaint = new Paint();
			
			//icon
			Bitmap bm = BitmapFactory.decodeResource(getResources(),
					R.drawable.ic_launcher);
			
			//background
			canvas.drawColor(Color.parseColor("#000f00"));

			//text
			mPaint.setColor(Color.GREEN);
			canvas.drawText("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 20, 20, mPaint);

			canvas.drawBitmap(bm, 200, 600, null);
			
			//must have to make image clean
			mPaint.setAntiAlias(true);

			//line
			mPaint.setColor(Color.parseColor("#ffffff"));
			canvas.drawLine(20, 40, 300, 800, mPaint);
			
			mPaint.setARGB(128, 128, 255, 0);
			canvas.drawCircle(240, 340, 80, mPaint);


			mPaint.setARGB(128, 255, 0, 0);
			canvas.drawCircle(100, 250, 120, mPaint);
			
			mPaint.setARGB(128, 255, 255, 0);
			canvas.drawRoundRect(new RectF(15.0f, 60.0f, 250.0f, 160.0f),
					100.0f, 100.0f, mPaint);

			Path mPath = new Path();
			mPath.addCircle(100, 300, 100, Direction.CCW);

			mPaint.setTextSize(30);
			canvas.drawTextOnPath("ABCDEFGHIJKLMNOPQRSTUVWXYZ", mPath, 0.7f, -7.0f,
					mPaint);


		}
	}
}