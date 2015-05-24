/*
 * File Name: OnlineAgreementActivity.java
 * 
 * UTA Housing Application
 * 
 * Input: This class is invoked when the user accepts the meningtis activity.
 * 
 * Output: Once the user clicks on Apply, the next screen shows the completed applications status.
 * 
 * Functional Description: This screen would show the applicant the online agreement related information,
 * which the user is expected to follow.
 * 
 * Update History
 * 
 * Date				Author				Changes
 * ------------------------------------------------------------------------------
 * 10/21/2013		Sagar, Meet			Original
 * 
 * 
 */
package com.example.uta_hosuing;

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

public class OnlineAgreementActivity extends Activity implements View.OnClickListener{

	HttpClient httpclient;
	HttpPost httppost;
	ArrayList<NameValuePair> nameValuePairs;
	
	String utaid;
	String apt_name;
	String edate;
	String ldate;
	String appdate;
	
	
	Button button1;
	@Override
	/* 
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * This method calls the related xml files to disply the screen on the android phone.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_online_agreement);
		button1=(Button)findViewById(R.id.button1);
		button1.setOnClickListener(this);
		
		Bundle extras = getIntent().getExtras(); 
		if(extras !=null)
		{	   
		    utaid = extras.getString("utaid");
		    apt_name = extras.getString("apt_name");
		    appdate = extras.getString("appdate");
		    edate = extras.getString("edate");
		    ldate =  extras.getString("ldate");
//		    Toast.makeText(getBaseContext(), utaid, Toast.LENGTH_SHORT).show();
//		    Toast.makeText(getBaseContext(), apt_name, Toast.LENGTH_SHORT).show();
//		    Toast.makeText(getBaseContext(), appdate, Toast.LENGTH_SHORT).show();
//		    Toast.makeText(getBaseContext(), edate, Toast.LENGTH_SHORT).show();
//		    Toast.makeText(getBaseContext(), ldate, Toast.LENGTH_SHORT).show();
		    
		}
		
	}

	@Override
	/* 
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * Inflate the menu; this adds items to the action bar if it is present.
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.online_agreement, menu);
		return true;
	}

	@Override
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 * This method looks for the buttons items on the xml page and identifies the button and calls buttonclick()
	 */
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case  R.id.button1:
				httpclient = new DefaultHttpClient();
				httppost = new HttpPost("http://utahousing.comoj.com/apt_allocation.php?");
				

    			Thread thread = new Thread(new Runnable(){
    			    @Override
    			    public void run() {
    			
    			try{
            	
    				nameValuePairs = new ArrayList<NameValuePair>();
    				
    				nameValuePairs.add(new BasicNameValuePair("utaid", utaid));
    				nameValuePairs.add(new BasicNameValuePair("apt_name", apt_name));
    				nameValuePairs.add(new BasicNameValuePair("appdate", appdate));
    				nameValuePairs.add(new BasicNameValuePair("edate", edate));
    				nameValuePairs.add(new BasicNameValuePair("ldate", ldate));
    				
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
    						
    						final String retNetid = jsonResponse.getString("success");
    						final String retPass = jsonResponse.getString("message");
    						String t="1";
    						
    						if(t.equals(retNetid))
    						{
    						  							
    							Intent intent = new Intent(OnlineAgreementActivity.this,EndActivity.class);
    							
    						    startActivity(intent);
    						}
    						else
    						{
    							
    							runOnUiThread(new Runnable() {
    							    public void run() {
    							Toast.makeText(getBaseContext(),retPass, Toast.LENGTH_SHORT).show();
    							    }
    							});
    						}
    					
    					}
    				}
    				
    			}	catch(Exception e){
					e.printStackTrace();
		//----------			
					runOnUiThread(new Runnable() {
					    public void run() {
					    	Toast.makeText(getBaseContext(), "connection error", Toast.LENGTH_SHORT).show();			    }
					});
		//--------------	
					//Toast.makeText(getBaseContext(), "nohiii", Toast.LENGTH_SHORT).show();
//					Toast.makeText(getBaseContext(), "connection error", Toast.LENGTH_SHORT).show();
				}
				
				    }
				});

				thread.start(); 

	
   	    
   	break;


	}

}

/*
 * To convert the InputStream to String we use the BufferedReader.readLine()
 * method. We iterate until the BufferedReader return null which means
 * there's no more data to read. Each line will appended to a StringBuilder
 * and returned as String.
 */
private static String convertStreamToString(InputStream is) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//    , "iso-8859-1"), 8
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
