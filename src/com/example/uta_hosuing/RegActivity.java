/*
 * File Name: RegActivity.java
 * 
 * UTA Housing Application
 * 
 * Input: This class is invoked when the user want to do his/her registration.
 * 
 * Output: User will get his/her user name and password and would able to fill up apartment details.
 *    
 * Functional Description: This screen allows the user to do his/her registration.
 * 
 * Update History
 * 
 * Date           Author          Changes
 * ----------------------------------------------------------------------
 * 09/10/2013     Ankit, Sagar      Original
 * 11/12/2013     Sagar,Meet        DB Connectivity feature was updated.
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

public class RegActivity extends Activity implements View.OnClickListener{

	String utaid2;
	Button button1;
	String fname,mname,lname,gender;
	
	HttpClient httpclient;
	HttpPost httppost;
	ArrayList<NameValuePair> nameValuePairs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);
		button1=(Button)findViewById(R.id.button1);
		button1.setOnClickListener(this);
		Bundle extras = getIntent().getExtras(); 
		if(extras !=null)
		{
		   
		    utaid2 = extras.getString("utaid");
		    fname = extras.getString("fname");
		    mname = extras.getString("mname");
		    lname = extras.getString("lname");
		    gender = extras.getString("gender");
//		    Toast.makeText(getBaseContext(),"hi"+utaid, Toast.LENGTH_SHORT).show();
	    }
		EditText fname2= (EditText) findViewById(R.id.editText1);
        EditText mname2 = (EditText) findViewById(R.id.editText2);
        EditText lname2  = (EditText) findViewById(R.id.editText3);
        EditText utaid3 = (EditText) findViewById(R.id.editText4);
        
		fname2.setText(fname);
		mname2.setText(mname);
		lname2.setText(lname);
		utaid3.setText(utaid2);
		RadioButton msex1,fsex1;
		msex1=(RadioButton)findViewById(R.id.radioButton1);
		fsex1=(RadioButton)findViewById(R.id.radioButton2);
		if(gender.equals("Male"))
			msex1.setChecked(true);
		else
			fsex1.setChecked(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reg, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case  R.id.button1:
			
			
			EditText fname= (EditText) findViewById(R.id.editText1);
            EditText mname = (EditText) findViewById(R.id.editText2);
            EditText lname  = (EditText) findViewById(R.id.editText3);
            EditText utaid = (EditText) findViewById(R.id.editText4);
            
            
    		final String fname1 = fname.getText().toString(); 
            final String mname1 = mname.getText().toString(); 
            final String lname1= lname.getText().toString(); 
            final String utaid1 = utaid.getText().toString();
            final String sex;
            
            RadioButton msex,fsex;
    		msex=(RadioButton)findViewById(R.id.radioButton1);
    		fsex=(RadioButton)findViewById(R.id.radioButton2);
            if(msex.isChecked())
            		sex=msex.getText().toString();
            else
            	sex=fsex.getText().toString();
            
            
            
            if ((fname.length()<1) || (lname.length()<1) || (utaid.length()<1) )
			{	
            	Toast.makeText(RegActivity.this,"Excpet Middele Name Other Fields can't be Empty", Toast.LENGTH_LONG).show();;
            	  
			}
            else
            { 	
            	httpclient = new DefaultHttpClient();
    			httppost = new HttpPost("http://utahousing.comoj.com/reg.php?");
            	
            	
            	
            	
    			Thread thread = new Thread(new Runnable(){
    			    @Override
    			    public void run() {
    			
    			try{
            	
    				nameValuePairs = new ArrayList<NameValuePair>();
    				
    				nameValuePairs.add(new BasicNameValuePair("fname", fname1));
    				nameValuePairs.add(new BasicNameValuePair("mname", mname1));
    				nameValuePairs.add(new BasicNameValuePair("lname", lname1));
    				nameValuePairs.add(new BasicNameValuePair("utaid", utaid1));
    				nameValuePairs.add(new BasicNameValuePair("utaid2", utaid2));
    				nameValuePairs.add(new BasicNameValuePair("gender", sex));
    				
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
    						  							
    							Intent intent = new Intent(RegActivity.this,HousingInfo.class);
    							intent.putExtra("utaid", utaid1);
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
//    						Toast.makeText(getBaseContext(), "connection error", Toast.LENGTH_SHORT).show();
    					}
    					
    					    }
    					});

    					thread.start(); 

       	
           	    }
           	break;
		
		
    		}
		
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


}
