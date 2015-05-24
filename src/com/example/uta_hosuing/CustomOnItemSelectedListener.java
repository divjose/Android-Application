/*
 * File Name: CustomOnItemSelectedListener.java
 * 
 * UTA Housing Application
 * 
 * Input: This class is invoked to create the stub with Adapter, view, position, and id.
 * 
 * Output: No Output, since this is a stub class.
 * 
 * Functional Description: This class is a stub class to implement the methods of OnItemSelectedListener.
 * There is no output, for this class, to the users of this class.
 * 
 * Update History
 * 
 * Date				Author				Changes
 * ------------------------------------------------------------------------------
 * 10/21/2013		Ankit,Meet			Original
 * 
 * 
 */
package com.example.uta_hosuing;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) 
	{
		// TODO Auto-generated method stub
		Toast.makeText(parent.getContext(),"OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
		

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) { 
		// TODO Auto-generated method stub

	}

}
