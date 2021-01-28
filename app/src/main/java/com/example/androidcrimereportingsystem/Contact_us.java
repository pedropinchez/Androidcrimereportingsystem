package com.example.androidcrimereportingsystem;


import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidcrimereportingsystem.R;
import com.example.androidcrimereportingsystem.Utils.Constants;

import java.net.URLEncoder;


public class Contact_us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


    }

    public void PoBox(View view) {
        try {
            Intent toEmail = new Intent("android.intent.action.VIEW", Uri.parse(Constants.POBOX));
            startActivity(toEmail);

        } catch (ActivityNotFoundException paramAnonymousView) {

            Toast.makeText(this, "Unable to connect to Email", Toast.LENGTH_LONG).show();

        }

    }

    public void Phone(View view) {


        startActivity(new Intent("android.intent.action.DIAL", Uri.fromParts("tel", Constants.CRIMESYSTEM_PHONENO, null)));

    }

    public void Email(View view) {

        try {
            Intent toEmail = new Intent("android.intent.action.VIEW", Uri.parse("mailto:crimereportingsystem@gmail.com"));
            startActivity(toEmail);

        } catch (ActivityNotFoundException paramAnonymousView) {

            Toast.makeText(this, "Unable to connect to Email", Toast.LENGTH_LONG).show();

        }

    }
    public void Call(View view) {
        startActivity(new Intent("android.intent.action.DIAL", Uri.fromParts("tel",  Constants.CRIMESYSTEM_PHONENO, null)));
    }

    public void message(View view) {

        String paramView = Constants.CRIMESYSTEM_PHONENO;
        Intent localIntent = new Intent("android.intent.action.VIEW");
        localIntent.setData(Uri.parse("sms:" + paramView));
        localIntent.putExtra("sms_body", "Hello there...");
        startActivity(localIntent);

    }

    public void Whatsapp(View view) {
                Context context;
                PackageManager packageManager = getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);
                String smsNumber = Constants.CRIMESYSTEM_PHONENO;
                String message="Hello @androidcrimereporting system.";

                try {
                    String url = "https://api.whatsapp.com/send?phone="+ smsNumber +"&text=" + URLEncoder.encode(message, "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        startActivity(i);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }



    }


    public boolean a(String paramString)
    {
        PackageManager localPackageManager = getPackageManager();
        try
        {
            localPackageManager.getPackageInfo(paramString, 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException paramStringk) {
            return false;
        }
    }
}