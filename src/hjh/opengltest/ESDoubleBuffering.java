package hjh.opengltest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Bundle;
import android.view.Window;

public class ESDoubleBuffering extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(new ESDoubleBufferingSurface(getBaseContext()));
	}
	
	class ESDoubleBufferingSurface extends GLSurfaceView {
		private final Renderer render;
		public ESDoubleBufferingSurface(Context context) {
			super(context);
			render = new ESDoubleBufferingRenderer();
			setRenderer(render);
			setFocusable(true);
			setFocusableInTouchMode(true);
		}
	}

	class ESDoubleBufferingRenderer implements GLSurfaceView.Renderer {
		
		FloatBuffer mBuffer;
		float angle;
		int n = 0;

		public ESDoubleBufferingRenderer() {
			float vtxbuf[] = { -1.0f, 0, 0, 1.0f, 0, 0 };
			ByteBuffer bb = ByteBuffer.allocateDirect(vtxbuf.length * 4);
			bb.order(ByteOrder.nativeOrder());
			mBuffer = bb.asFloatBuffer();
			mBuffer.put(vtxbuf);
			mBuffer.position(0);
		}

		public void onDrawFrame(GL10 gl) {
			if (n < 2) {
				gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
				n++;
			}

			GLU.gluLookAt(gl, 0, 0, 8.f, 0, 0, 0, 0, 1.f, 0);

			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glRotatef(angle++, 0, 0, 1);

			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mBuffer);
			gl.glColor4f(1, 1, 1, 1);
			gl.glDrawArrays(GL10.GL_POINTS, 0, 2);
		}

		public void onSurfaceChanged(GL10 gl, int w, int h) {
			gl.glViewport(0, 0, w, h);

			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();

			float ratio = (float) w / h;
			GLU.gluPerspective(gl, 45.f, ratio, 1.f, 100.f);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
			gl.glClearColor(0, 0, 0, 1);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		}
	}
}