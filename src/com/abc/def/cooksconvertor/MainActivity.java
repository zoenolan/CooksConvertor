package com.abc.def.cooksconvertor;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;
import java.lang.Double;
import java.text.DecimalFormat;

public class MainActivity extends Activity {
	public enum UnitsToConvert {FEET, METRES, POUNDS, KILOGRAMMES};
	
	double         inputValue = 0;
	UnitsToConvert inputUnits = UnitsToConvert.FEET;
	
	protected void convertValue() {
			double convertedValue = 0;
			String outputUnits = "Metres";
		
			switch (inputUnits) {
			case FEET:
				convertedValue = inputValue * 0.3048;
				outputUnits = "metres";
				break;

			case METRES:
				convertedValue = inputValue * (1/0.3048);
				outputUnits = "feet";
				break;
				
			case POUNDS:
				convertedValue = inputValue * (1/2.2);
				outputUnits = "kilogrammes";
				break;
				
			case KILOGRAMMES:
				convertedValue = inputValue * 2.2;
				outputUnits = "pounds";
				break;        							
			}		
		
			if (convertedValue != 0) {
	        	DecimalFormat twoDForm = new DecimalFormat("#.##");                	
	        	TextView result = (TextView) findViewById(R.id.resultsView);
	            result.setText("is " + twoDForm.format(convertedValue) + " " + outputUnits);							
			}	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Value input
		EditText inputField = (EditText) findViewById(R.id.inputInFeet);
        inputField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	TextView inputData = (TextView) findViewById(R.id.inputInFeet);
            	String text = inputData.getText().toString();

            	if (text.length() > 0) {          		
            		inputValue  = Double.parseDouble(text);
            		convertValue();
            	}
           }
        }); 
        
        inputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable  s) {
            	if (s.length() > 0) {          		
            		inputValue  = Double.parseDouble(s.toString());
            		convertValue();
            	}            	         	
            }
        });            
	
        // Units input
        Spinner spinner = (Spinner) findViewById(R.id.unitsSelector);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
      			switch (position) {
      			case 0:
      				inputUnits = UnitsToConvert.FEET;
      				break;

      			case 1:
      				inputUnits = UnitsToConvert.METRES;
      				break;
      				
      			case 2:
      				inputUnits = UnitsToConvert.POUNDS;
      				break;
      				
      			case 3:
      				inputUnits = UnitsToConvert.KILOGRAMMES;
      				break;        				
      				
      			}
      			
      			convertValue();
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {        
            }            

        });       
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

