package com.invention.planner;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TaskEdit extends Activity {

	private Long rowId;
	private TasksDbAdapter dbHelper;
	private Button doneButton;
	private EditText mTitle;
	private EditText mDesc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		dbHelper = new TasksDbAdapter(this);
		dbHelper.open();
		
		setContentView(R.layout.task_edit);
		
		rowId = ( (savedInstanceState==null) ? null : 
			(Long) savedInstanceState.getSerializable(TasksDbAdapter.KEY_ROWID));
		
		if (rowId == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				rowId = extras.getLong(TasksDbAdapter.KEY_ROWID);
			} 
		}
		
		mTitle = (EditText) findViewById(R.id.taskTitle);
		mDesc = (EditText) findViewById(R.id.description);

		populateFields();
		
		doneButton = (Button) findViewById(R.id.doneButton);
		doneButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});
	}
	
	private void populateFields() {
		if (rowId != null) {
			Cursor currentTask = dbHelper.fetchTask(rowId);

			mTitle.setText(currentTask.getString(currentTask.getColumnIndexOrThrow(TasksDbAdapter.KEY_TITLE)));
			mDesc.setText(currentTask.getString(currentTask.getColumnIndexOrThrow(TasksDbAdapter.KEY_BODY)));
		}
	}
	
	public void doneEditingItem(View view) {
		 // Do something in response to button
		String title = mTitle.getText().toString();
		String desc = mDesc.getText().toString();
		
		Bundle bundle = new Bundle();
		bundle.putString(TasksDbAdapter.KEY_TITLE, title);
		bundle.putString(TasksDbAdapter.KEY_BODY, desc);
		bundle.putLong(TasksDbAdapter.KEY_ROWID, rowId);
		Intent mIntent = new Intent();
		mIntent.putExtras(bundle);
		setResult(RESULT_OK, mIntent);
		
		finish();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		saveState();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		populateFields();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		saveState();
	}
	
	private void saveState() {
		String title = mTitle.getText().toString();
		String body = mDesc.getText().toString();
		
		if(rowId == null) {
			if (title.isEmpty() && body.isEmpty()) return;
			long id = dbHelper.createTask(title, body);
			if (id > 0) {
				rowId = id;
			}
		} else {
			dbHelper.updateTask(rowId.longValue(), title, body);
		}
	}	

	

}
