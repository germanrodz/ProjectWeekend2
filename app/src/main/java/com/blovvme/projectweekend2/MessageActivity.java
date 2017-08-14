package com.blovvme.projectweekend2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MessageActivity extends AppCompatActivity {

    private  static final int MY_PERMISIONS_REQUEST_SEND_SMS = 0;
    Button sendBtn;
    EditText txtphoneNo, txtMessage;
    String phoneNo, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        sendBtn = (Button) findViewById(R.id.sendBtn);
        txtphoneNo = (EditText) findViewById(R.id.txtphoneNo);
        txtMessage = (EditText) findViewById(R.id.txtMessage);

        if (ContextCompat.checkSelfPermission(MessageActivity.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MessageActivity.this,
                    Manifest.permission.SEND_SMS)) {
                ActivityCompat.requestPermissions(MessageActivity.this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);
            }
            else {
                ActivityCompat.requestPermissions(MessageActivity.this,
                        new String[] {Manifest.permission.SEND_SMS}, 1);
            }
//            else {
//
//            }
        }//

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNo = txtphoneNo.getText().toString();
                message = txtMessage.getText().toString();

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(MessageActivity.this, "Sent", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(MessageActivity.this, "Message Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }//

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MessageActivity.this,
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "No Permission Granted", Toast.LENGTH_SHORT).show();
                }
                return;
        }

    }
}//
