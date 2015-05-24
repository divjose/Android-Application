/*
 * File Name: MainActivity.java
 * 
 * UTA Housing Application
 * 
 * Input: This class is invoked when the user wants to see his/her account.
 * 
 * Output: User will get access to his/her account. 
 * 
 * Functional Description: This screen allows the user to log in his/her account.
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



import android.os.AsyncTask;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener
{
	EditText netid,password;
	Button button1;
	String netid1,password1;
	HttpClient httpclient;
	HttpPost httppost;
	ArrayList<NameValuePair> nameValuePairs;
	HttpResponse response;
	HttpEntity entity;
	
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * This method calls the related xml file to display the screen on the android phone.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button1=(Button)findViewById(R.id.button1);
		netid=(EditText)findViewById(R.id.editText1);
		password=(EditText)findViewById(R.id.editText2);
		
		button1.setOnClickListener(this);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 * Inflate the menu; this adds items to the action bar if it is present
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 * This method checks whether the entered password is valid or not.
	 */

	public void onClick(View v) {

		EditText e1 = (EditText) findViewById(R.id.editText1);
        EditText e2 = (EditText) findViewById(R.id.editText2); 
        String f1 = e1.getText().toString(); 
        String f2 = e2.getText().toString();
        
       if ((f1.length()<1) && (f2.length()<1))
       	Toast.makeText(MainActivity.this,"Net ID and Password can't be Empty", Toast.LENGTH_LONG).show();
       else if ((f1.length()<1))
	    		Toast.makeText(MainActivity.this,"Please enter the Net ID", Toast.LENGTH_LONG).show();
       else if ((f2.length()<1))
			Toast.makeText(MainActivity.this,"Please enter the Password", Toast.LENGTH_LONG).show();
		else if ((f2.length()<6))
				Toast.makeText(MainActivity.this,"Password must be atleast 6 characters long", Toast.LENGTH_LONG).show();
		else if (f2.length()>25)
			Toast.makeText(MainActivity.this,"Password must not exceed more than 25 characters", Toast.LENGTH_LONG).show();
		else if ((f1.length()!=7))
				Toast.makeText(MainActivity.this,"Net ID must contain 7 characters", Toast.LENGTH_LONG).show();
		else
{	    
		
		netid1=netid.getText().toString();
		password1=password.getText().toString();
		
		System.out.println("Net ID: " + netid1);
		System.out.println("Password: " + password1);
		
		httpclient = new DefaultHttpClient();
		httppost = new HttpPost("http://utahousing.comoj.com/login.php?");//http://www.utahousing.comoj.com/login.php  aa lakhi nakhje ahi
		
		
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		
		try{
			
			//Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
			nameValuePairs = new ArrayList<NameValuePair>();
			
			nameValuePairs.add(new BasicNameValuePair("netid1", netid1));
			nameValuePairs.add(new BasicNameValuePair("password1", password1));
			
			System.out.println("Name Value Pairs: " + nameValuePairs);
			
			//Toast.makeText(getBaseContext(), "hiii1111", Toast.LENGTH_SHORT).show();
			//Toast.makeText(getBaseContext(), netid1, Toast.LENGTH_SHORT).show();
			//Toast.makeText(getBaseContext(), password1, Toast.LENGTH_SHORT).show();
			
			
			
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	/*		
			runOnUiThread(new Runnable() {
			    public void run() {
			    	Toast.makeText(getBaseContext(), "HTTP POST " + httppost.getRequestLine(), Toast.LENGTH_SHORT).show();			    }
			});
	*/		
			response = httpclient.execute(httppost);
			
			System.out.println("Response: " + response);
			
			System.out.println("Response Status Code: " + response.getStatusLine().getStatusCode());
			
			if(response.getStatusLine().getStatusCode()==200)
			{
				entity=response.getEntity();
				if(entity!= null)
				{
					
					
					InputStream instream = entity.getContent();
					
					JSONObject jsonResponse = new JSONObject(convertStreamToString(instream));
					final String retNetid = jsonResponse.getString("success");
					final String retPass = jsonResponse.getString("message");
					final String firsttime = jsonResponse.getString("firsttime");
	/*				runOnUiThread(new Runnable() {
					    public void run() {
					Toast.makeText(getBaseContext(), "firsttime", Toast.LENGTH_SHORT).show();
					    }
					});
	*/				String t="1";
					
					if(t.equals(retNetid))
					{
						String utaid = jsonResponse.getString("utaid");
							
						if(t.equals(firsttime))
						{
							Intent intent = new Intent(MainActivity.this,ButtonActivitys.class);
							intent.putExtra("utaid", utaid);
							startActivity(intent);
						}
						else
						{
							Intent intent = new Intent(MainActivity.this,RegActivity1.class);
						//	intent.putExtra("utaid", utaid);
							startActivity(intent);
						}
					    
					}
					else
					{
						
						runOnUiThread(new Runnable() {
						    public void run() {
						Toast.makeText(getBaseContext(),retPass, Toast.LENGTH_SHORT).show();
						    }
						});
					}
					
/*					runOnUiThread(new Runnable() {
					    public void run() {
					Toast.makeText(getBaseContext(),retNetid, Toast.LENGTH_SHORT).show();
					    }
					});
					
*/			
					
/*					if(netid1.equals(retNetid) && password1.equals(retPass)){
						
						SharedPreferences sp = getSharedPreferences("logindeatils", 0);
						SharedPreferences.Editor spedit = sp.edit();
						spedit.commit();
	//					runOnUiThread(new Runnable() {
	//					    public void run() {
	//					Toast.makeText(getBaseContext(), "Sucess login yeaaaa", Toast.LENGTH_SHORT).show();
	//					    }
	//					});	
	
						Intent intent = new Intent(MainActivity.this,RegActivity.class);
					    startActivity(intent);
					    //Toast.makeText(getBaseContext(), retNetid, Toast.LENGTH_SHORT).show();
						
					}else{
						System.out.println("Else");
						runOnUiThread(new Runnable() {
						    public void run() {
						Toast.makeText(getBaseContext(), "Id Password does not match", Toast.LENGTH_SHORT).show();
						    }
						});
					}	// end else
						
*/					
				}	// end second if
				
				
				
			}	// end first if
			
			
			
			
		}catch(Exception e){
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
}        /*
	         * To convert the InputStream to String we use the BufferedReader.readLine()
	         * method. We iterate until the BufferedReader return null which means
	         * there's no more data to read. Each line will appended to a StringBuilder
	         * and returned as String.
	         */
		private static String convertStreamToString(InputStream is) {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//	        , "iso-8859-1"), 8
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