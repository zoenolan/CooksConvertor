package com.abc.def.cooksconvertor;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;
import java.lang.Double;
import java.text.DecimalFormat;

public class MainActivity extends Activity {
	public enum UnitsToConvert {FANHRENHEIT, FLOZ, OZ, CELSIUS, ML, GRAMMES};
	
	private static final String UNITS_KEY = "unitsMode";	
	
	int			   unitPosition     = 0;
	double         inputValue       = 0;
	UnitsToConvert inputUnits       = UnitsToConvert.FANHRENHEIT;
	boolean        imperialToMetric = true;
	
	protected void convertValue() {
			switch (unitPosition) {
			case 0:
				if (imperialToMetric) {
					inputUnits = UnitsToConvert.FANHRENHEIT;
				} else {
					inputUnits = UnitsToConvert.CELSIUS;					
				}
				break;

			case 1:
				if (imperialToMetric) {
					inputUnits = UnitsToConvert.FLOZ;
				} else {
					inputUnits = UnitsToConvert.ML;					
				}
				break;
				
			case 2:
				if (imperialToMetric) {
					inputUnits = UnitsToConvert.OZ;
				} else {
					inputUnits = UnitsToConvert.GRAMMES;					
				}
				break;      				
			}		
		
		
			double convertedValue = 0;
			String outputUnits = "Metres";
		
			switch (inputUnits) {
			case FANHRENHEIT:
				convertedValue = inputValue * 0.3048;
				outputUnits = "celcius";
				break;

			case FLOZ:
				convertedValue = inputValue * (1/0.3048);
				outputUnits = "ml";
				break;
				
			case OZ:
				convertedValue = inputValue * (1/2.2);
				outputUnits = "g";
				break;
				
			case CELSIUS:
				convertedValue = inputValue * 0.3048;
				outputUnits = "fahrenheit";
				break;

			case ML:
				convertedValue = inputValue * (1/0.3048);
				outputUnits = "fl oz";
				break;
				
			case GRAMMES:
				convertedValue = inputValue * (1/2.2);
				outputUnits = "oz";
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
		
		// Check for previously saved state
		if (savedInstanceState != null) {
			imperialToMetric  = savedInstanceState.getBoolean(UNITS_KEY);
		}		
		
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
      			position = unitPosition;
      			
      			convertValue();
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {        
            }            

        });
        
        // Units type
        Spinner directionSpinner = (Spinner) findViewById(R.id.conversionDirection);
        directionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
            	//ArrayAdapter<String> unitsAdapter;
            	//Spinner spinner = (Spinner) findViewById(R.id.unitsSelector);
            	
       			switch (position) {
      			case 0:
      				imperialToMetric = true;   				
      				
      				//unitsAdapter = new ArrayAdapter<String>(this, R.array.units_list, spinner);
      				
      				break;

      			case 1:
      				imperialToMetric = false;
      				
      				//unitsAdapter = new ArrayAdapter<String>(this, R.array.metric_units_list, spinner);
      				
      				break;  				
      			}

      			// set the spinner to the correct set of units
       			//spinner.setAdapter(unitsAdapter);
      			
      			// and force the values to be converted
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
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putBoolean(UNITS_KEY, imperialToMetric);
	}	

}

