package com.abc.def.cooksconvertor;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class UStoMetric extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usto_metric);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.usto_metric, menu);
		return true;
	}

}
