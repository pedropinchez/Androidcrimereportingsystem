package com.example.androidcrimereportingsystem.Login;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.androidcrimereportingsystem.HomeActivity;
import com.example.androidcrimereportingsystem.Information;
import com.example.androidcrimereportingsystem.R;
import com.example.androidcrimereportingsystem.Utils.ValidateUserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class signup extends AppCompatActivity {
    EditText password,cpassword,emails,username;
    TextView txt_alreadyHave;
    Button btn_registrar;
    private DatabaseReference mDatabase;
    private CircleImageView setupImage;
    private Uri mainImageURI = null;
    private boolean isChanged = false;

    private ProgressBar loginProgress;
    private StorageReference mImageStorage;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        emails =  findViewById(R.id.email);
        password =  findViewById(R.id.password);
        loginProgress = findViewById(R.id.login_progress);
        username=findViewById(R.id.username);
        cpassword =  findViewById(R.id.cpassword);
        txt_alreadyHave =  findViewById(R.id.txt_already_have);
        txt_alreadyHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this, login.class));
                finish();
            }
        });

        btn_registrar =  findViewById(R.id.btn_register);
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptCreate();
            }
        });

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptCreate() {
        // Store values at the time of the login attempt.
        final String email = emails.getText().toString();
        final String usernames = username.getText().toString();
        String passwords = password.getText().toString();
        String cpasswords = cpassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        ValidateUserInfo validate = new ValidateUserInfo();

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emails.setError("empty email");
            focusView = emails;
            cancel = true;
        } else if (TextUtils.isEmpty(usernames)) {
            username.setError("empty username");
            focusView = username;
            cancel = true;
        } else if (mainImageURI != null) {
            Toast.makeText(this, "user image cant be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(passwords)) {
            password.setError("empty password");
            focusView = password;
            cancel = true;
        } else if (!validate.isEmailValid(email)) {
            emails.setError("invalid email");
            focusView = emails;
            cancel = true;
        } else if (TextUtils.isEmpty(passwords)) {
            password.setError("field required");
            focusView = password;
            cancel = true;
        } else if (!validate.isPasswordValid(passwords)) {
            cpassword.setError("invalid password");
            focusView = cpassword;
            cancel = true;
        } else if (!(passwords.equals(cpasswords))) {
            cpassword.setError("passowrd not same");
            focusView = cpassword;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            signinuser(email,passwords);
        }

    }
    private void signinuser( String email, String password) {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    sendemail();
                    if(!user.isEmailVerified()){
                       // Toast.makeText(signup.this, "Email is sent", Toast.LENGTH_SHORT).show();

                    }

                }else{

                    String error = task.getException().toString();
                    Toast.makeText(signup.this,error,Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void sendemail() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(signup.this,"check email for verification",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), Information.class));

                    }
                    else{
                        String error = task.getException().toString();
                        Toast.makeText(signup.this,error,Toast.LENGTH_LONG).show();
                       // Toast.makeText(signup.this, "send failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}