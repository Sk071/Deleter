package com.shubham.mydeleter;
import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.util.*;
import java.io.*;
import android.os.*;

public class CustomAdapter extends ArrayAdapter 
{
	ArrayList<String> modelItems;
	ArrayList<String> selectedFiles;
	boolean[] checkBoxState;
	Context context;
	int layoutResourceId;	
	ViewHolder viewholder;
	boolean isDeleted;
	String path;
    CustomAdapter adapter;
	List<String> allfiles;


	public CustomAdapter(Context context, int layoutResourceId, ArrayList<String> modelItems)
	{
		super(context, layoutResourceId, modelItems);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.modelItems = modelItems;
		checkBoxState = new boolean[modelItems.size()];
	    selectedFiles = new ArrayList<String>();
		allfiles = new ArrayList<String>();
		path = Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public void selectAll()
	{
		for (int i=0;i < checkBoxState.length;i++)
		{
			checkBoxState[i] = true;
			selectedFiles.add(modelItems.get(i));
			Log.d("Adding into ArrayList:", "" + modelItems.get(i));
		}
		notifyDataSetChanged();		
	}

	public void unSelectAll()
	{
		for (int i=0;i < checkBoxState.length;i++)
		{
			checkBoxState[i] = false;
			selectedFiles.remove(modelItems.get(i));
			Log.d("Removing:", "" + modelItems.get(i));
		}
		notifyDataSetChanged();
	}

	public void resetSelectedItems()
	{
		selectedFiles.clear();
	}

	public void delete()
	{
		for (int i=0;i < selectedFiles.size();i++)
		{
			String s= selectedFiles.get(i);
			Log.d("selectedFiles.get(i)", "" + s);
		    searchFileForDelete(s);		
            modelItems.remove(s);
		}
        resetSelectedItems();
		notifyDataSetChanged();
	}

	public void displayDirectory(File dir)
	{
		File[] files = dir.listFiles();

		for (File file : files) 
		{
			allfiles.add(file.getAbsolutePath());

			if (file.isDirectory())
			{
				displayDirectory(file);
				allfiles.add(file.getAbsolutePath());
			}
		}

	}

	public void searchFileForDelete(String substring)
    {
		File f = new File(path);
		displayDirectory(f);
		String searchString = substring;
		for (int i=0;i < allfiles.size();i++)
		{
			String tempVal = allfiles.get(i);

			if (tempVal.contains(searchString))
			{				
				Log.d("tempVal", "" + tempVal);
				Toast.makeText(context, "Deleting" + tempVal, Toast.LENGTH_SHORT).show();
				File dlt = new File(tempVal);
				if (dlt != null && dlt.exists())
				{
					dlt.delete();
				}
			}
		}
	}

	private class ViewHolder
	{
		CheckBox checkbox;
		TextView texview;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
		convertView = inflater.inflate(R.layout.row, parent, false); 
		viewholder = new ViewHolder();
		viewholder.checkbox = (CheckBox) convertView.findViewById(R.id.checkBox1);
		viewholder.texview = (TextView) convertView.findViewById(R.id.textView1);
		viewholder.texview.setText(modelItems.get(position));
		viewholder.checkbox.setChecked(checkBoxState[position]);

		viewholder.checkbox.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if (((CheckBox) p1).isChecked())
					{
						checkBoxState[position] = true;

						if (checkBoxState[position] == true)
						{
							Log.d("modelItems.get(position):", "" + modelItems.get(position));
							selectedFiles.add(modelItems.get(position));
						}
					}
					else
					{
						checkBoxState[position] = false;
						if (selectedFiles.contains(modelItems.get(position)))
						{  
							selectedFiles.remove(modelItems.get(position));
						}
					}
				}
			});
		return convertView;
	}

}
