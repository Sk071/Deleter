package com.shubham.mydeleter;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.content.*;

public class MainActivity extends Activity 
{
	String search;
	Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		final EditText et= (EditText) findViewById(R.id.editText1);
		b = (Button) findViewById(R.id.button1);
		
		b.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					search = et.getText().toString();
					Intent i = new Intent(MainActivity.this, DataFetcher.class);
					i.putExtra("mysearch", search);
					startActivity(i);
				}
			});
    }

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
	}
}
