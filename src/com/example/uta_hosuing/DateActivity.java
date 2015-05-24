/*
 * File Name: DateActivity.java
 * 
 * UTA Housing Application
 * 
 * Input: This class is invoked after user enters/modifies the earliest move-in and/or move-out date 
 * 
 * Output: User should read the information given. accept it by checking the check box and and 
 *         then click on continue button to proceed to OnlineAgreement screen.
 * 
 * Functional Description: This screen shows the user meningitis information, 
 * 
 * Update History
 * 
 * Date				Author				Changes
 * ------------------------------------------------------------------------------
 * 10/21/2013		Vivek,Divya		  Original
 * 
 * 
 */
package com.example.uta_hosuing;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;



import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
 
public class DateActivity extends Activity implements View.OnClickListener{
 
	
	
	private TextView tvDisplayDate;
	private DatePicker dpResult;
	private Button button1;
	private Button btnChangeDate;
	
	EditText e1 = (EditText) findViewById(R.id.editText2);
	private int year;
	private int month;
	private int day;
	
	private int eyear;
	private int emonth;
	private int eday;
	
	
	static final int DATE_DIALOG_ID = 999;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date);
		button1=(Button)findViewById(R.id.button1);
		button1.setOnClickListener(this);
		Bundle extras = getIntent().getExtras(); 
		if(extras !=null)
		{
		    System.out.println("Let's get the values");
		    eyear = extras.getInt("eyear");
		    eday =  extras.getInt("eday");
		    emonth= extras.getInt("emonth");
		}
		setCurrentDateOnView();
		addListenerOnButton();
 
	}
 
	// display current date
	public void setCurrentDateOnView() {
 
		tvDisplayDate = (TextView) findViewById(R.id.tvDate);
		dpResult = (DatePicker) findViewById(R.id.dpResult);
		
 
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		TextView txt=(TextView)findViewById(R.id.txt);
		txt.setText(day+"/"+(month+1)+"/"+year);
 
		// set current date into textview
		tvDisplayDate.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(month + 1).append("-").append(day).append("-")
			.append(year).append(" "));
/*		e1.setText(new StringBuilder()
		// Month is 0 based, just add 1
		.append(month + 1).append("-").append(day).append("-")
		.append(year).append(" "));
 */
		// set current date into datepicker
		dpResult.init(year, month, day, null);
 
	}
 
	public void addListenerOnButton() {
 
		btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
 
		btnChangeDate.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
 
				showDialog(DATE_DIALOG_ID);
 
			}
 
		});
 
	}
 
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener, 
                         year, month,day);
		}
		return null;
	}
 
	private DatePickerDialog.OnDateSetListener datePickerListener 
                = new DatePickerDialog.OnDateSetListener() {
 
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
 
			// set selected date into textview
			tvDisplayDate.setText(new StringBuilder().append(month + 1)
			   .append("-").append(day).append("-").append(year)
			   .append(" "));
 
/*			e1.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(month + 1).append("-").append(day).append("-")
			.append(year).append(" "));
	*/
			// set selected date into datepicker also
			dpResult.init(year, month, day, null);
 
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case  R.id.button1:
//			Toast.makeText(CommunityPreference.this,Integer.toString(day)+"/"+Integer.toString(year), Toast.LENGTH_LONG).show();
			
		
				buttonclick();
		
		}
	}


	private void buttonclick() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(DateActivity.this,MeningitisActivity.class);
		
        startActivity(intent);
		
	}
}