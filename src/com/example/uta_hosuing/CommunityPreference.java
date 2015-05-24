/*
 * File Name: CommunityPreference.java
 * 
 * UTA Housing Application
 * 
 * Input: This class is invoked after user enters/modifies the earliest move-in and/or move-out date 
 * 
 * Output: User should read the information given. accept it by checking the check box and and 
 *         then click on continue button to proceed to OnlineAgreement screen.
 * 
 * Functional Description: This screen allows the user to select earliest moving date. 
 * 
 * Update History
 * 
 * Date				Author				Changes
 * ------------------------------------------------------------------------------
 * 10/21/2013		Arun,Vivek		  Original
 * 
 * 
 */
 
package com.example.uta_hosuing;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


public class CommunityPreference extends Activity implements View.OnClickListener{
    
	
	private DatePicker datePicker1;
//	private DatePicker datePicker2;
	Button button1;
	private int year;
	private int month;
	private int day;
	int cc=0;
	String utaid;
	String apt_name;
	
	
	
	int eyear;
	int emonth;
	int eday;
	
	int deyear;
	int demonth;
	int deday;
	
	String dlyear;
	String dlmonth;
	String dlday;
	
	String dedate;
	String dldate;
	
	@Override
	/* 
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * This method calls the related xml file to display the screen on the android phone.
	 */

	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_community_preference);
		button1=(Button)findViewById(R.id.button1);
		button1.setOnClickListener(this);
		
		
		
		Bundle extras = getIntent().getExtras(); 
		if(extras !=null)
		{
		   
		    utaid = extras.getString("utaid");
		    apt_name = extras.getString("apt_name");
		    
		     deday= extras.getInt("deday");
		     demonth= extras.getInt("demonth");
		     deyear= extras.getInt("deyear");
		     
//		     Toast.makeText(getBaseContext(),"hi"+deday, Toast.LENGTH_SHORT).show();
		     
		     dlday = extras.getString("dlday");
		     dlmonth = extras.getString("dlmonth");
		     dlyear= extras.getString("dlyear");
		     
		     dedate = extras.getString("dedate");
		     dldate = extras.getString("dldate");
		    
		     setCurrentDateOnView();
		    
//		    Toast.makeText(getBaseContext(),"hi"+utaid, Toast.LENGTH_SHORT).show();
	    }
		
/*	

		datePicker1 = (DatePicker) findViewById(R.id.datePicker1);
		datePicker2 = (DatePicker) findViewById(R.id.datePicker2);
		
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		datePicker1.init(year, month, day,null);
		datePicker2.init(year, month, day,null);
*/		

		
	}

	
	@Override
	/* 
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * Inflate the menu; this adds items to the action bar if it is present.
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.community_preference, menu);
		return true;
	}

	/*
	 * This method implements datePicker functionality and calls onDateChanged() 
	 *
	 */
	private void setCurrentDateOnView() {
		// TODO Auto-generated method stub
		
		datePicker1 = (DatePicker) findViewById(R.id.datePicker1);
	//	datePicker2 = (DatePicker) findViewById(R.id.datePicker2);
		
		
		
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		TextView txt=(TextView)findViewById(R.id.txt);
		
		if(dedate.equals(""))
			txt.setText(year+"-"+(month+1)+"-"+day);
		else
			txt.setText(dedate);
//		datePicker1.init(year, month, day,null);
	//	Toast.makeText(CommunityPreference.this,Integer.toString(day), Toast.LENGTH_LONG).show();;
		datePicker1.init(year, month, day,new DatePicker.OnDateChangedListener()
		{
			public void onDateChanged (DatePicker view, int year, int monthOfYear, int dayOfMonth)
			{
				
		//		 view.
				 cc++;
				 Integer.toString(cc);
				TextView txt1=(TextView)findViewById(R.id.txt);
				 txt1.setText(view.getYear()+"-"+(view.getMonth()+1)+"-"+view.getDayOfMonth());
//				Toast.makeText(CommunityPreference.this,Integer.toString(cc), Toast.LENGTH_LONG).show();;
			    
				 eyear=view.getYear();
				 emonth=view.getMonth()+1;
				 eday=view.getDayOfMonth();
			     
			}
		}
		); 
	/*	datePicker2.init(year, month, day,new OnDateChangedListener()
		{
			public void onDateChanged (DatePicker view, int year, int monthOfYear, int dayOfMonth)
			{
				;
			}
		}
		);
	
		*/
	}


	@Override
	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 * This method compares the earliest move-in date with the current date 
	 * Also looks for button items on activity_community_preference.xml and calls buttonclick()
	 */
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case  R.id.button1:
//			Toast.makeText(CommunityPreference.this,Integer.toString(day)+"/"+Integer.toString(year), Toast.LENGTH_LONG).show();
			
			if ((eyear>year) ||	((eyear==year) && (emonth>(month+1))) || ((eyear==year) && (emonth==(month+1)) && (eday>day)))
				buttonclick();
			else 
				Toast.makeText(CommunityPreference.this,"Earliest Move In Date Must be later than Current Date", Toast.LENGTH_LONG).show();
		}  

	}

	/*
	 * This method does the button activity that leads us to LatestDateActivity.java
	 */
	private void buttonclick() {
		Intent intent = new Intent(CommunityPreference.this,LatestDateActivity.class);
	//	Intent intent = new Intent(CommunityPreference.this,MeningitisActivity.class);
		intent.putExtra("eday", eday);
		intent.putExtra("emonth", emonth);
		intent.putExtra("eyear", eyear);
		intent.putExtra("utaid", utaid);
		intent.putExtra("apt_name",apt_name);
		intent.putExtra("dedate", dedate);
		intent.putExtra("dldate", dldate);
        startActivity(intent);
		
	}
	
}
