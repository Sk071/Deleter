package com.shubham.mydeleter;
import android.app.*;
import android.os.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import android.util.*;
import android.view.*;
import android.widget.AdapterView.*;
import com.shubham.mydeleter.*;
import android.preference.*;

public class DataFetcher extends Activity
{
	TextView tv;
	ListView lv;
	String path = Environment.getExternalStorageDirectory().getAbsolutePath();
	File fileandpath = new File(path);
	String searchstring;

	List<String> mediaList = new ArrayList<String>();
    ArrayList<String> resList = new ArrayList<String>();
	CustomAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_results);

		tv = (TextView) findViewById(R.id.resulttext);
		lv = (ListView) findViewById(R.id.listview);

		Bundle receivedmsg = getIntent().getExtras();

		if (receivedmsg == null)
		{
			return;
		}

		searchstring = receivedmsg.getString("mysearch");
		tv.setText("Search Results for " + searchstring);	

		searchForFileNameContainingSubstring(searchstring);

	    adapter = new CustomAdapter(DataFetcher.this, android.R.layout.simple_list_item_1 , resList);
		lv.setAdapter(adapter);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO: Implement this method
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu_options, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		// TODO: Implement this method
		switch (item.getItemId())
		{
			case R.id.select_all:
				adapter.selectAll();
				return true;

			case R.id.deselect:
				adapter.unSelectAll();
				return true;

			case R.id.delete:
				adapter.delete();
				return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	public void searchForFileNameContainingSubstring(String substring)
    {
		displayDirectoryContents(fileandpath);
		resList = new ArrayList<String>();
		String searchString = substring;

		for (String curVal : mediaList)
		{
			if (curVal.contains(searchString))
			{
				resList.add(curVal);
			}

      	}
	}

    public void displayDirectoryContents(File dir)
	{
		File[] files = dir.listFiles();

		for (File file : files) 
		{
			String[] sb= file.list();
			Log.d("files.list", "" + file.list());

			if (file.isDirectory())
			{
				displayDirectoryContents(file);

				for (String s : sb)
				{
					mediaList.add(s);
				}
				try
				{
					Log.d("filespath:", "" + file.getAbsolutePath());
				}
				catch (Exception e)
				{}	
			}
			else
			{
				Log.e("msg", "No files");
			}		
		}		
	}
}
