package com.example.uta_hosuing;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class StatusActivity extends Activity {

	String waitno;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		TextView t;
		t=(TextView)findViewById(R.id.textView3);
			
		Bundle extras = getIntent().getExtras(); 
		if(extras !=null)
		{
		   
		    waitno = extras.getString("waitno");
		    t.setText(waitno);
//		    Toast.makeText(getBaseContext(),"hi"+utaid, Toast.LENGTH_SHORT).show();
	/*	    if(waitno>0)
		    	t.setText(waitno);
		    else 
		    	if(waitno<1)
		    		t.setText("Appartment is Available.Contact Housing Department");
		    	else
		    		t.setText("Latest Move in Date is passed. Reapply");
*/		    
		    
		    
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status, menu);
		return true;
	}

}
