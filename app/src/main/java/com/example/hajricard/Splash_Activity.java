package com.example.hajricard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_Activity extends AppCompatActivity {

    TextView textView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //if (FirebaseUtil.isloggedin()){
                  //  startActivity(new Intent(Splash_Activity.this, MainActivity.class));
                //}else {
                    startActivity(new Intent(Splash_Activity.this, Login_Phone_Number_Activity.class));
                //}
                finish(); // Optional: Finish the current activity if needed
            }
        }, 1000); // Delay in milliseconds (e.g., 3000 milliseconds = 3 seconds)


        ImageView imageView2 = findViewById(R.id.imageView2);
        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);

    }
}