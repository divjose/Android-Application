/*
 * File Name: ButtonActivity.java
 * 
 * UTA Housing Application
 * 
 * Input: This class is invoked when the user logs-in
 * 
 * Output: User can choose to navigate to different screen by using home/application button
 * 
 * Functional Description: This screen is the home screen for the user. 
 * 
 * Update History
 * 
 * Date           Author          Changes
 * ----------------------------------------------------------------------
 * 09/30/2013     Sagar, Divya     Original
 * 
 * 
 */
package com.example.uta_hosuing;

import com.example.uta_hosuing.R.id;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class ButtonActivitys extends Activity implements View.OnClickListener {
	
	Button button2;  // button for edit appplication
	Button button3; // button for status
	String utaid;
	
	
	HttpClient httpclient;
	HttpPost httppost;
	ArrayList<NameValuePair> nameValuePairs;

	@Override
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * This method calls the related xml file to display the screen on the android phone.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_button_activitys);
		button2=(Button)findViewById(R.id.button2);
		button2.setOnClickListener(this);
		
		button3=(Button)findViewById(R.id.Button01);
		button3.setOnClickListener(this);
		
		Bundle extras = getIntent().getExtras(); 
		if(extras !=null)
		{
		   
		    utaid = extras.getString("utaid");
	   // Toast.makeText(getBaseContext(),"hi"+utaid, Toast.LENGTH_SHORT).show();
	    }
	}

	

	@Override
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 * Inflate the menu; this adds items to the action bar if it is present.
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.button_activitys, menu);
		return true;
	}

	@Override
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 * This method looks for the buttons items on the activity_button_activitys.xml page 
	 * identifies the button and calls buttonclick()
	 */
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case  R.id.button2:
			  buttonclick();
			  break;
		case  R.id.Button01:
		      buttonclick1();
		      break;
	    }
	
			
	}

    /*
     * To convert the InputStream to String we use the BufferedReader.readLine()
     * method. We iterate until the BufferedReader return null which means
     * there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     */
	private void buttonclick() {
		// TODO Auto-generated method stub
		
		httpclient = new DefaultHttpClient();
		httppost = new HttpPost("http://utahousing.comoj.com/select_studentinfo.php?");
    	
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		
		try{
    	
			nameValuePairs = new ArrayList<NameValuePair>();
			
			
			nameValuePairs.add(new BasicNameValuePair("utaid", utaid));
	/*		runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(getBaseContext(),utaid, Toast.LENGTH_SHORT).show();
				}
				});
			
	*/		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			HttpResponse response = httpclient.execute(httppost);
			
			System.out.println("Response: " + response);
			
			System.out.println("Response Status Code: " + response.getStatusLine().getStatusCode());
		
			if(response.getStatusLine().getStatusCode()==200)
			{
				HttpEntity entity = response.getEntity();
				if(entity!= null)
				{
					
	/*				runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getBaseContext(),utaid, Toast.LENGTH_SHORT).show();
						}
						});
	*/			
					InputStream instream = entity.getContent();
					
					JSONObject jsonResponse = new JSONObject(convertStreamToString(instream));
					
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getBaseContext(),utaid, Toast.LENGTH_SHORT).show();
						}
						});
	//				final String retNetid = jsonResponse.getString("success");
	//				final String retPass = jsonResponse.getString("message");
	//				final String waitno = jsonResponse.getString("waitno");
					final String fname = jsonResponse.getString("fname");
					final String mname = jsonResponse.getString("mname");
					final String lname = jsonResponse.getString("lname");
					final String gender = jsonResponse.getString("gender");
					
					
					Intent intent = new Intent(ButtonActivitys.this,RegActivity.class);
					intent.putExtra("utaid", utaid);
					intent.putExtra("fname", fname);
					intent.putExtra("mname", mname);
					intent.putExtra("lname", lname);
					intent.putExtra("gender",gender);
			        startActivity(intent);
			        
/*					String t="1";
					String t1="0";
					String t2="-1000";
					if(t.equals(retNetid))
					{	
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(getBaseContext(),waitno, Toast.LENGTH_SHORT).show();
							}
							});
					  						
						
						
						Intent intent = new Intent(ButtonActivitys.this,StatusActivity.class);
						intent.putExtra("waitno",waitno);
					    startActivity(intent);
					}
					else if(t1.equals(retNetid))
					{
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(getBaseContext(),waitno, Toast.LENGTH_SHORT).show();
							}
							});
					  						
						
						
						Intent intent = new Intent(ButtonActivitys.this,StatusActivity.class);
						intent.putExtra("waitno",waitno);
					    startActivity(intent);
					}
					else
					{
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(getBaseContext(),waitno, Toast.LENGTH_SHORT).show();
							}
							});
					  						
						
						
						Intent intent = new Intent(ButtonActivitys.this,StatusActivity.class);
						intent.putExtra("waitno",waitno);
					    startActivity(intent);
					}
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

	/* 
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * This method does the button click action that leads us to RegActivity screen
	 */
	
	private void buttonclick1() {
		// TODO Auto-generated method stub
		
		httpclient = new DefaultHttpClient();
		httppost = new HttpPost("http://utahousing.comoj.com/waitlist1.php?");
    	
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
					final String waitno = jsonResponse.getString("waitno");
					
/*					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getBaseContext(),waitno, Toast.LENGTH_SHORT).show();
						}
						});
*/					Intent intent = new Intent(ButtonActivitys.this,StatusActivity.class);
					intent.putExtra("waitno",waitno);
				    startActivity(intent);
					

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
	
	

	
    /*
     * To convert the InputStream to String we use the BufferedReader.readLine()
     * method. We iterate until the BufferedReader return null which means
     * there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     */
	private static String convertStreamToString(InputStream is) {
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

}
	

