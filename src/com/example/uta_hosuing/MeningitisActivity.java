/*
 * File Name: MeningitisActivity.java
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
 * 10/21/2013		Ankit,Vivek		  Original
 * 
 * 
 */
 
package com.example.uta_hosuing;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MeningitisActivity extends Activity implements View.OnClickListener {

	String utaid;
	String apt_name;
	String appdate;
	String edate;
	String ldate;
	
	Button button1;
	@Override
	/* 
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * This method calls the related xml files and displays the items on the screen. 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meningitis);
		TextView txt1=(TextView)findViewById(R.id.textView1);
		txt1.setMovementMethod(ScrollingMovementMethod.getInstance());
		txt1.setMovementMethod(LinkMovementMethod.getInstance());
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
		getMenuInflater().inflate(R.menu.meningitis, menu);
		return true;
	}

	@Override
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 * This method looks for the buttons items on the activity_meningitis.xml page 
	 * identifies the button and calls buttonclick() and also implements a checkbox 
	 */
	public void onClick(View v) {
		CheckBox c;
		c=(CheckBox)findViewById(R.id.checkBox1);
		switch(v.getId())
		{
		case  R.id.button1:
				 if(c.isChecked()==true)
					 buttonclick();
				 else
					 Toast.makeText(MeningitisActivity.this,"Meningitis Agreement Accepted must be ticked", Toast.LENGTH_LONG).show();
          	     break;
		
		
	    }

	}
	/* 
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * This method does the button click action that leads us to OnlineAgreement screen
	 */
	private void buttonclick() {
	// TODO Auto-generated method stub
	Intent intent = new Intent(MeningitisActivity.this,OnlineAgreementActivity.class);
	intent.putExtra("utaid", utaid);
	intent.putExtra("apt_name",apt_name);
	intent.putExtra("appdate", appdate);
	intent.putExtra("edate", edate);
	intent.putExtra("ldate",ldate);
    startActivity(intent);
	
}

}