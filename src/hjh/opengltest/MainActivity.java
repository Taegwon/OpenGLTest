package hjh.opengltest;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;


public class MainActivity extends ListActivity {
	private String[] menuTitles = { "Drawing 2D first", "Double Buffering", "ES Double Buffering" };

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuTitles);

		setListAdapter(adapter);
		
		
		getListView().setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// soras_000 2014. 5. 18. ¿ÀÀü 7:46:55
				switch(position) {
				case 0 : startActivity(new Intent(getApplicationContext(), TwoDActivity.class)); break;
				case 1 : startActivity(new Intent(getApplicationContext(), DoubleBuffering.class)); break;
				case 2 : startActivity(new Intent(getApplicationContext(), ESDoubleBuffering.class)); break;
				}
			}});

	}
}

	