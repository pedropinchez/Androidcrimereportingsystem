package com.example.androidcrimereportingsystem.lost;


import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidcrimereportingsystem.R;


public class Connectfound extends AppCompatActivity {


    TextView textView;
    String l = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectfound);
        textView = findViewById(R.id.t);
        l = getIntent().getExtras().getString("Number");
        String h = "0"+ l;
        textView.setText(h);

    }

    public void Call(View view) {
        startActivity(new Intent("android.intent.action.DIAL", Uri.fromParts("tel", "0" + l, null)));
    }

    public void message(View view) {

        String paramView = "0" + l;
      Intent localIntent = new Intent("android.intent.action.VIEW");
        localIntent.setData(Uri.parse("sms:" + paramView));
        localIntent.putExtra("sms_body", "Hello there...");
        startActivity(localIntent);

    }

    public void Whatsapp(View view) {

        Intent localIntent;
        if (a("com.gbwhatsapp"))
        {
            String paramView = "254" + l;
            localIntent = new Intent("android.intent.action.MAIN");
            localIntent.setComponent(new ComponentName("com.gbwhatsapp", "com.gbwhatsapp.Conversation"));
            localIntent.setAction("android.intent.action.SEND");
            localIntent.setType("text/plain");
            localIntent.putExtra("android.intent.extra.TEXT", "Hello there...");
            localIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(paramView) + "@s.whatsapp.net");
            localIntent.setPackage("com.gbwhatsapp");
            startActivity(localIntent);
        } else  if (a("com.whatsapp"))
        {
            String paramView = "254" + this.l;
            localIntent = new Intent("android.intent.action.MAIN");
            localIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            localIntent.setAction("android.intent.action.SEND");
            localIntent.setType("text/plain");
            localIntent.putExtra("android.intent.extra.TEXT", "Hello there...");
            localIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(paramView) + "@s.whatsapp.net");
            localIntent.setPackage("com.whatsapp");
            startActivity(localIntent);
        }
        else {

            Toast.makeText(this, "WhatsApp not installed", Toast.LENGTH_LONG).show();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(Connectfound.this, LostAndFound.class));

    }
}