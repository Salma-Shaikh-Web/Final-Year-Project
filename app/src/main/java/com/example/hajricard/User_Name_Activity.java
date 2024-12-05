package com.example.hajricard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hajricard.utils.FirebaseUtil;
import com.example.hajricard.utils.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;


public class User_Name_Activity extends AppCompatActivity {

    EditText user_input;
    Button user_btn;
    ProgressBar user_progressBar;
    String phoneNumber;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_name);

        user_input = findViewById(R.id.user_input);
        user_btn = findViewById(R.id.user_btn);
        user_progressBar= findViewById(R.id.user_progressBar);

        phoneNumber= getIntent().getExtras().getString("phone");
        getUsername();

        user_btn.setOnClickListener((v ->{
            setUsername();
            //startActivity(new Intent(User_Name_Activity.this,MainActivity.class));

        }) );
    }

    void setUsername(){

        String username = user_input.getText().toString();
        if (username.isEmpty() || username.length()<3){
            user_input.setError("Username length should be atleast 3 character");
            return;
        }
        setInProgress(true);

        if (userModel!=null){
            userModel.setUsername(username);
        }else {

            userModel = new UserModel(phoneNumber,username, Timestamp.now(), userModel.getUserId());
        }
        FirebaseUtil.getCurrentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                setInProgress(false);
                if (task.isSuccessful()) {

                    Intent intent = new Intent(User_Name_Activity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    
                }
            }
        });

    }

    void getUsername(){

        setInProgress(true);
        FirebaseUtil.getCurrentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){

            @Override
            public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {

                setInProgress(false);
                if (task.isSuccessful()){
                    UserModel userModel = task.getResult().toObject(UserModel.class);
                    if (userModel!=null){
                        user_input.setText(userModel.getUsername());
                    }
                }

            }
        });
    }



    void setInProgress(boolean inProgress) {
        if (inProgress) {
            user_progressBar.setVisibility(View.VISIBLE);
            user_btn.setVisibility(View.GONE);
        } else {
            user_progressBar.setVisibility(View.GONE);
            user_btn.setVisibility(View.VISIBLE);
        }
    }
}