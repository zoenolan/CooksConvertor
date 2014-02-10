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
		
			// Do the conversion
			double convertedValue = 0;
			String outputUnits = "celcius";
		
			switch (inputUnits) {
			case FANHRENHEIT:
				convertedValue =  (5.0 / 9.0) * (inputValue - 32);
				outputUnits = "celcius";
				break;

			case FLOZ:
				convertedValue = inputValue / 0.035195;
				outputUnits = "ml";
				break;
				
			case OZ:
				convertedValue = inputValue / 0.035274;
				outputUnits = "g";
				break;
				
			case CELSIUS:
				convertedValue = (9.0 / 5.0) * (inputValue + 32);
				outputUnits = "fahrenheit";
				break;

			case ML:
				convertedValue = inputValue * 0.035195;
				outputUnits = "fl oz";
				break;
				
			case GRAMMES:
				convertedValue = inputValue * 0.035274;
				outputUnits = "oz";
				break;      							
			}		

        	DecimalFormat twoDForm = new DecimalFormat("#.##");                	
        	TextView result = (TextView) findViewById(R.id.resultsView);
            result.setText("is " + twoDForm.format(convertedValue) + " " + outputUnits);							
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
            	unitPosition = position;
      			
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
       			switch (position) {
      			case 0:
      				imperialToMetric = true;   				     				
      				break;

      			case 1:
      				imperialToMetric = false;      				
      				break;  				
      			}
      			
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

