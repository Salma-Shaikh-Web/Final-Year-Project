package com.example.hajricard;

// Import necessary classes and packages

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;

public class Login_Phone_Number_Activity extends AppCompatActivity {

    // Declare UI components
    EditText Enter_mobile;
    Button mobile_button;
    CountryCodePicker login_countrycode;
    ProgressBar mobile_progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  // Call the superclass method
        setContentView(R.layout.activity_login_phone_number);  // Set the content view to the corresponding XML layout

        // Initialize UI components by finding them by their IDs
        Enter_mobile = findViewById(R.id.Enter_mobile);
        mobile_button = findViewById(R.id.mobile_button);
        mobile_progressBar = findViewById(R.id.mobile_progressBar);
        login_countrycode = findViewById(R.id.login_coutrycode);

        // Initially hide the progress bar
        mobile_progressBar.setVisibility(View.GONE);

        // Link the country code picker with the mobile number input field
        login_countrycode.registerCarrierNumberEditText(Enter_mobile);


        mobile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = null;
                // Validate the phone number using the country code picker
                if (!login_countrycode.isValidFullNumber()) {
                    Enter_mobile.setError("Phone number is invalid");  // Show an error if the phone number is invalid
                }




                phoneNumber = Enter_mobile.getText().toString();  // Get the entered phone number
                if (!phoneNumber.isEmpty()) {
                    // If the phone number is not empty, create an Intent to navigate to OTP_Activity
                    Intent intent = new Intent(Login_Phone_Number_Activity.this, OTP_Activity.class);
                    intent.putExtra("phone",login_countrycode.getFullNumberWithPlus());
                    startActivity(intent);// Start the OTP_Activity
                } else {
                    // Show a toast message if the phone number field is empty
                    Toast.makeText(Login_Phone_Number_Activity.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                }
            }
            public void buttonGoogleSignIn(View view){

            }

            // The commented-out code below sets padding for window insets, but it is not currently active
            // ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            //     Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //     v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            //     return insets;
            // });
        });
    }
}
