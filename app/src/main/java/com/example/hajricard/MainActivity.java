package com.example.hajricard;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomnavigation;
    MaterialDivider divider;
    ImageButton search_button;
    Attendence_Fragment fragment_attendence;
    Payment_Fragment fragment_payment;
    FrameLayout frame_layout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bottomnavigation = findViewById(R.id.bottom_navigation);
        divider =findViewById(R.id.divider);
        search_button =findViewById(R.id.search_button);

        search_button.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,Search_User_Activity.class));

        });

        fragment_attendence= new Attendence_Fragment();
        fragment_payment= new Payment_Fragment();

        bottomnavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId()==R.id.attendence_menu){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main,fragment_attendence).commit();
                }
                if (menuItem.getItemId()==R.id.payment_menu){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main,fragment_payment).commit();
                }
                return true;
            }
        });
        bottomnavigation.setSelectedItemId(R.id.attendence_menu);


    }
}