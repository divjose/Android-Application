/*
 * File Name: LastDateActivity.java
 * 
 * UTA Housing Application
 * 
 * Input: This class is invoked when the user enters his/her choice for latest moving date and car.
 * 
 * Output: User will get his/her latest moving date for the apartment and besides this he/she will
 * also get a parking token if they 'car' option.
 * 
 * Functional Description: This screen allows the user to select latest moving date.
 * 
 * Update History
 * 
 * Date           Author          Changes
 * ----------------------------------------------------------------------
 * 10/21/2013     Meet,Arun      Original
 * 
 * 
 */
 package com.example.uta_hosuing;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class LatestDateActivity extends Activity implements View.OnClickListener
{

	String utaid;
	String apt_name;
	String appdate;
	String edate;
	String ldate;
	
	
	Button button2;
	private DatePicker datePicker2;
	
	private int year_;
	private int month_;
	private int day_;
	int cc=0;
	
	int eyear;
	int emonth;
	int eday;
	int lyear;
	int lmonth;
	int lday;
	String dldate;

	@Override
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * This method calls the related xml file to display the screen on the android phone.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_latest_datee);
		button2=(Button)findViewById(R.id.button2);
		button2.setOnClickListener(this);
		Bundle extras = getIntent().getExtras(); 
		if(extras !=null)
		{
		   
		    eyear = extras.getInt("eyear");
		    eday =  extras.getInt("eday");
		    emonth= extras.getInt("emonth");
		    utaid = extras.getString("utaid");
		    dldate = extras.getString("dldate");
		    
		    apt_name = extras.getString("apt_name");
		    edate=eyear+"-"+emonth+"-"+eday;
		}
		setCurrentDateOnView();	
	}

/*This method implements datePicker functionality and calls onDateChanged()*/
	private void setCurrentDateOnView() {
		// TODO Auto-generated method stub
		datePicker2 = (DatePicker) findViewById(R.id.datePicker2);
		
		
		final Calendar c = Calendar.getInstance();
		year_ = c.get(Calendar.YEAR);
		month_ = c.get(Calendar.MONTH);
		day_ = c.get(Calendar.DAY_OF_MONTH);
		TextView txt1=(TextView)findViewById(R.id.txt1);
		
		if(dldate.equals(""))
			txt1.setText(year_+"-"+(month_+1)+"-"+day_);
		else
			txt1.setText(dldate);
		
//		txt1.setText(day_+"/"+(month_+1)+"/"+year_);
		
		appdate= year_+"-"+(month_+1)+"-"+day_;
		datePicker2.init(year_, month_, day_,new DatePicker.OnDateChangedListener()
		{
			public void onDateChanged (DatePicker view, int year, int monthOfYear, int dayOfMonth)
			{
				 cc++;
				 Integer.toString(cc);
				TextView txt1=(TextView)findViewById(R.id.txt1);
				 txt1.setText(view.getYear()+"-"+(view.getMonth()+1)+"-"+view.getDayOfMonth());
//				Toast.makeText(CommunityPreference.this,Integer.toString(cc), Toast.LENGTH_LONG).show();;
			    
				 lyear=view.getYear();
				 lmonth=view.getMonth()+1;
				 lday=view.getDayOfMonth();
	//		     Toast.makeText(LatestDateActivity.this,Integer.toString(lday), Toast.LENGTH_LONG).show();;
			}
		}
		);
		
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 * Inflate the menu; this adds items to the action bar if it is present
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.latest_date, menu);
		return true;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 * This method compares the earliest move in date with the date entered by the user.
	 */
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case  R.id.button2:
//			Toast.makeText(CommunityPreference.this,Integer.toString(day)+"/"+Integer.toString(year), Toast.LENGTH_LONG).show();
			ldate=lyear+"-"+lmonth+"-"+lday;
			if ((lyear>eyear) ||	((lyear==eyear) && (lmonth>(emonth))) || ((lyear==eyear) && (lmonth==(emonth)) && (lday>eday)))
				buttonclick();
		else 
		{
			
			
			Toast.makeText(LatestDateActivity.this,"Earliest Move In Date"+":"+edate +"-"+ ldate, Toast.LENGTH_LONG).show();
				Toast.makeText(LatestDateActivity.this,"Latest Move In Date Must be later than Earliest Move In Date", Toast.LENGTH_LONG).show();
		}
		}  
	}
/*This method does the button activity that leads us to MeningitisActivity.java
 * 
 */

	private void buttonclick() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(LatestDateActivity.this,MeningitisActivity.class);
		intent.putExtra("utaid", utaid);
		intent.putExtra("apt_name",apt_name);
		intent.putExtra("appdate", appdate);
		intent.putExtra("edate", edate);
		intent.putExtra("ldate",ldate);
        startActivity(intent);
		
	}

}
