/*
 * File Name: HousingInfo.java
 * 
 * UTA Housing Application
 * 
 * Input: This class is invoked when the user enters his personal and contact details.
 * 
 * Output: User selects his apartment preferences and on clicking apply gets the 
 * screen to select move-in date.
 * 
 * Functional Description: This screen will allow user to select his preferred apartment
 * from the drop down list. 
 * 
 * Update History
 * 
 * Date				Author				Changes
 * ------------------------------------------------------------------------------
 * 10/09/2013		Sagar, Meet		Original
 * 
 * 
 */
package com.example.uta_hosuing;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class HousingInfo extends Activity implements View.OnClickListener {
	
	HttpClient httpclient;
	HttpPost httppost;
	ArrayList<NameValuePair> nameValuePairs;
	
	
	String utaid;
	Button button1;
	private Spinner spinner1;
	String apt_name;
	String dapt_name;
	
	String deday;
	String demonth;
	String deyear;
	
	String dlday;
	String dlmonth;
	String dlyear;
	
	String dedate;
	String dldate;
	
	
	
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 *  This method calls the associated xml layout. It also calls addItemsOnSpinner1() 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_housing_info);
		addItemsOnSpinner1();
		
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		
		button1=(Button)findViewById(R.id.button1);
		button1.setOnClickListener(this);
		Bundle extras = getIntent().getExtras(); 
		if(extras !=null)
		{
		   
			 utaid= extras.getString("utaid");
//		    Toast.makeText(getBaseContext(),"hi"+utaid, Toast.LENGTH_SHORT).show();
	    }
		
		
		httpclient = new DefaultHttpClient();
		httppost = new HttpPost("http://utahousing.comoj.com/select_aptallocation.php?");
    	
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		
		try{
    	
			nameValuePairs = new ArrayList<NameValuePair>();
			
			nameValuePairs.add(new BasicNameValuePair("utaid", utaid));
			
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			HttpResponse response = httpclient.execute(httppost);
			
			System.out.println("Response: " + response);
			
			System.out.println("Response Status Code: " + response.getStatusLine().getStatusCode());
		
			if(response.getStatusLine().getStatusCode()==200)
			{
				HttpEntity entity = response.getEntity();
				if(entity!= null)
				{
					
					
					InputStream instream = entity.getContent();
					
					JSONObject jsonResponse = new JSONObject(convertStreamToString(instream));
					
					
	//				final String retNetid = jsonResponse.getString("success");
	//				final String retPass = jsonResponse.getString("message");
	//				final String waitno = jsonResponse.getString("waitno");
		
					final String dapt_name1=jsonResponse.getString("apt_name");	// apt_name previously saved in database = 
					final String deday1=jsonResponse.getString("eday");
					final String demonth1=jsonResponse.getString("emonth");
					final String deyear1=jsonResponse.getString("eyear");
					
					final String dlday1=jsonResponse.getString("lday");
					final String dlmonth1=jsonResponse.getString("lmonth");
					final String dlyear1=jsonResponse.getString("lyear");
					
					final String dedate1=jsonResponse.getString("edate");
					final String dldate1=jsonResponse.getString("ldate");
					runOnUiThread(new Runnable() {
					    public void run() {
					    	Toast.makeText(getBaseContext(), dedate1, Toast.LENGTH_SHORT).show();			    }
					});
					
					deday=deday1;
					demonth=demonth1;
					deyear=deyear1;
					
					dlday=dlday1;
					dlmonth=dlmonth1;
					dlyear=dlyear1;
					
					dedate=dedate1;
					dldate=dldate1;
					dapt_name=dapt_name1;
					
					
				
	/*				runOnUiThread(new Runnable() {
					    public void run() {
					    	Toast.makeText(getBaseContext(), deyear+"-"+demonth+"-"+deday, Toast.LENGTH_SHORT).show();			    }
					});
					
		*/			
					

				}
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
//----------			
			runOnUiThread(new Runnable() {
			    public void run() {
			    	Toast.makeText(getBaseContext(), "connection error", Toast.LENGTH_SHORT).show();			    }
			});
//--------------	
			//Toast.makeText(getBaseContext(), "nohiii", Toast.LENGTH_SHORT).show();
//			Toast.makeText(getBaseContext(), "connection error", Toast.LENGTH_SHORT).show();
		}
		    }
		});

		thread.start(); 
		
		
	}
	private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        , "iso-8859-1"), 8
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


	
		 /*
		 * This method adds items into the spinner list
		 */

	private void addItemsOnSpinner1() {
		// TODO Auto-generated method stub
		spinner1 = (Spinner)findViewById(R.id.spinner1);
		List<String> list = new ArrayList<String>();
		list.add("Arbor Oaks 1BHK");
		list.add("Arbor Oaks 2BHK");
		list.add("Autumn Hollow EFF");
		list.add("Center Point 1BHK");
		list.add("Cooper Chase 1BHK");
		list.add("Cooper Chase 2BHK");
		list.add("Cottonwood Ridge North 1BHK");
		list.add("Cottonwood Ridge North 2BHK");
		list.add("Creek Bend 1BHK");
		list.add("Garden Club 1BHK");
		list.add("Garden Club 2BHK");
		list.add("Maple Square 1BHK");
		
		list.add("Meadow Run 1BHK");
		list.add("Meadow Run 2BHK");
		list.add("Oak Landing 1BHK");
		list.add("Pecan Place 1BHK");
		list.add("The Heights on Pecan 1BHK");
		list.add("The Heights on Pecan 2BHK");
		list.add("The Heights on Pecan 4BHK");
		
		list.add("The Lofts at College Park 1BHK");
		list.add("The Lofts at College Park 2BHK");
		
		list.add("Timber Brook 1BHK");
		list.add("Timber Brook 2BHK");
		
		list.add("University Village 1BHK");
		
		list.add("West Crossing 1BHK");
		list.add("West Crossing 2BHK");
		
		list.add("Woodland Springs");
		
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(dataAdapter);

		
	}
	
		
	/*
	 * This method will invoke the spinner method 
	 */
	
	public void addListenerOnSpinnerItemSelection() {
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	  }

	@Override
	/* 
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * Inflate the menu; this adds items to the action bar if it is present.
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.housing_info, menu);
		return true;
	}

	@Override
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 * This method looks for the buttons items on the activity_housing_info.xml page 
	 * identifies the button and calls buttonclick()
	 */
	public void onClick(View v) {
/*		RadioButton r0,r1,r2,r3;
		r0=(RadioButton)findViewById(R.id.radio0);
		r1=(RadioButton)findViewById(R.id.radio1);
		r2=(RadioButton)findViewById(R.id.radio2);
		r3=(RadioButton)findViewById(R.id.radio3);
*/
		switch(v.getId())
		{
		case  R.id.button1:
			apt_name=String.valueOf(spinner1.getSelectedItem());
			Toast.makeText(HousingInfo.this,"You Selected: "+ apt_name,Toast.LENGTH_SHORT).show();
			buttonclick();
			
//			if(r0.isChecked()|| r1.isChecked() ||  r2.isChecked() || r3.isChecked())
				
//			else
//				Toast.makeText(HousingInfo.this,"You must choose something", Toast.LENGTH_LONG).show();
//			break;
			
		// TODO Auto-generated method stub
		
	}

}

/*	private void buttonclick() {
		// TODO Auto-generated method stub
		
		Intent intent = new Intent(HousingInfo.this,CommunityPreference.class);
		intent.putExtra("utaid", utaid);
		intent.putExtra("apt_name",apt_name);
        startActivity(intent);
	} */
	/* 
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * This method does the button click action that leads us to CommunityPreference.java
	 */
	private void buttonclick() {
		// TODO Auto-generated method stub
	
	Intent intent = new Intent(HousingInfo.this,CommunityPreference.class);

	intent.putExtra("deday",deday);
	intent.putExtra("demonth",demonth);
	intent.putExtra("deyear",deyear);
	intent.putExtra("dlday",dlday);
	intent.putExtra("dlmonth",dlmonth);
	intent.putExtra("dlyear",dlyear);
	intent.putExtra("dedate",dedate);
	intent.putExtra("dldate",dldate);
	
	intent.putExtra("utaid", utaid);
	intent.putExtra("apt_name",apt_name);
	
    startActivity(intent);
	}
}