package com.train.day38_01surfaceview;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {
	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置自定义的SurfaceView控件
		setContentView(R.layout.activity_main);
		listView=(ListView) findViewById(R.id.listView);
		TimerAdapter adapter=new TimerAdapter(this);
		listView.setAdapter(adapter);
//		setContentView(new MySurfaceView(this));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
