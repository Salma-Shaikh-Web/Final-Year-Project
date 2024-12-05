    package com.example.hajricard;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ProgressBar;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import com.example.hajricard.utils.Android;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.FirebaseApp;
    import com.google.firebase.FirebaseException;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.PhoneAuthCredential;
    import com.google.firebase.auth.PhoneAuthOptions;
    import com.google.firebase.auth.PhoneAuthProvider;

    import java.util.Timer;
    import java.util.TimerTask;
    import java.util.concurrent.TimeUnit;

    public class OTP_Activity extends AppCompatActivity {

        String phoneNumber;
        Long timeoutSeconds=60L;
        String verificationCode;
        PhoneAuthProvider.ForceResendingToken ResendingToken;

        EditText otp_input;
        Button otp_button;
        ProgressBar otp_progressbar;
        TextView resend_otp;

        private FirebaseAuth mAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            FirebaseApp.initializeApp(this);
            //EdgeToEdge.enable(this);
            setContentView(R.layout.activity_otp);

            mAuth = FirebaseAuth.getInstance();

            otp_input = findViewById(R.id.otp_input);
            otp_button = findViewById(R.id.otp_button);
            otp_progressbar = findViewById(R.id.otp_progressbar);
            resend_otp = findViewById(R.id.resend_otp);

            phoneNumber = getIntent().getExtras().getString("phone");
            Toast.makeText(getApplicationContext(), phoneNumber, Toast.LENGTH_SHORT).show();

            sendOtp(phoneNumber,false);

            otp_button.setOnClickListener(v -> {
                String enterOtp=otp_input.getText().toString();
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationCode,enterOtp);
                signIn(credential);
            });

            resend_otp.setOnClickListener((v )->{

                sendOtp(phoneNumber,true);
            });


        }

        void sendOtp(String phoneNumber, boolean isResend) {
            startResendTimer();
            setInProgress(true);
            PhoneAuthOptions.Builder optionsBuilder =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(phoneNumber)
                            .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                            .setActivity(this)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    signIn(phoneAuthCredential);
                                    setInProgress(false);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Android.showToast(getApplicationContext()," OTP Verification Failed");
                                    setInProgress(false);

                                }

                                @Override
                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(s, forceResendingToken);
                                    verificationCode =  s;
                                    ResendingToken = forceResendingToken;
                                    Android.showToast(getApplicationContext(),"OTP send Successfully");
                                    setInProgress(false);


                                }
                            });
            if(isResend) {
                PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.setForceResendingToken(ResendingToken).build());
            }else {
                PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build());


            }

        }

        void setInProgress(boolean inProgress) {
            if (inProgress) {
                otp_progressbar.setVisibility(View.VISIBLE);
                otp_button.setVisibility(View.GONE);
            } else {
                otp_progressbar.setVisibility(View.GONE);
                otp_button.setVisibility(View.VISIBLE);
            }
        }
        void signIn(PhoneAuthCredential phoneAuthCredential){

            setInProgress(true);
            mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    setInProgress(false);
                    if (task.isSuccessful()){
                        Intent intent = new Intent(OTP_Activity.this,User_Name_Activity.class);
                        intent.putExtra("phone",phoneNumber);
                        startActivity(intent);
                        finish();
                    }else {

                        Android.showToast(getApplicationContext(),"OTP varification failed");
                    }
                }
            });


        }
        void startResendTimer(){

            resend_otp.setEnabled(false);
            Timer timer= new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timeoutSeconds--;
                    resend_otp.setText("Resend OTP in "+timeoutSeconds+ " seconds");
                    if (timeoutSeconds<=0){
                        timeoutSeconds=60L;
                        timer.cancel();
                        runOnUiThread(()->{

                            resend_otp.setEnabled(true);
                        });
                    }

                }
            },0,1000);
        }
    }
