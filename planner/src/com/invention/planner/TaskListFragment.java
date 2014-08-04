package com.invention.planner;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

public class TaskListFragment extends ListFragment {
	private TasksDbAdapter dbHelper;
	public static final int INSERT_ID = Menu.FIRST;
	public static final int DELETE_ID = Menu.FIRST;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		dbHelper = new TasksDbAdapter(getActivity());
		dbHelper.open();
		fillData();
		
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.task_list_fragment, container, false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedState) {
    	super.onActivityCreated(savedState);
    	registerForContextMenu(getListView());
    }

    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, R.string.delete_item);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		boolean result = super.onContextItemSelected(item);
		
		switch(item.getItemId()) {
		case DELETE_ID: 
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			dbHelper.deleteTask(info.id);
			fillData();
			return true;
		}
		
		return result;
	}
	
	
	public void fillData() {
		Cursor allTasks = dbHelper.fetchAllTasks();
		
		String[] from = new String[] { TasksDbAdapter.KEY_TITLE, TasksDbAdapter.KEY_BODY };
	    int[] to = new int[] { R.id.text1, R.id.text2 };
	        
	    // Now create an array adapter and set it to display using our row
	    SimpleCursorAdapter notes =
	            new SimpleCursorAdapter(getActivity(), R.layout.tasks_row, allTasks, from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
			 
		this.setListAdapter(notes);
	}
	

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(getActivity(), TaskEdit.class);
		intent.putExtra(TasksDbAdapter.KEY_ROWID, id);
		startActivityForResult(intent, MainActivity.ACTIVITY_EDIT);
		
	}


}
