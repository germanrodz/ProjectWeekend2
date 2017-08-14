package com.blovvme.projectweekend2;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    String[] namesList;
    boolean[] checkedItems;
    ArrayList<Integer> selectedItems = new ArrayList<>();


    PDFView pdfView;
    Button btn1,btn2,btn3;
    ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //array alert dialog
       btn1 = (Button) findViewById(R.id.btn1);
        //Notificatoin and fragment loop
        btn2 = (Button) findViewById(R.id.btn2);
        //SMS
        btn3 = (Button) findViewById(R.id.btn3);
        imgView = (ImageView) findViewById(R.id.imgView);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromAsset("hydroponic1.pdf").load();


        namesList = getResources().getStringArray(R.array.name);
        checkedItems = new boolean[namesList.length];

        //function to call dialog fragment
        showDialog();

        TimerFragment timerFragment = new TimerFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.timerfrag, timerFragment,null).commit();

        ClockFragment clockFragment = new ClockFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.clockfrag, clockFragment,null).commit();



    }

    private void showDialog(){

        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Hydroponics Information");
        dialog.setContentView(R.layout.dialog_fragment);
        dialog.show();
        //Timer
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                dialog.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 3000); // after 2 second (or 2000 miliseconds), the task will be active.

    }

    public void messageActivity(View view) {
        Intent intent = new Intent( this, MessageActivity.class);
        startActivity(intent);
    }

    public void onNotification(View view) {
      /*  Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);*/

        Intent intent = new Intent(this, HomeActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent =  taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Pending Notification")
                .setContentTitle("Fragment Loop")
                .setContentText("Press Notification to advance to Fragment Loop")
                .setContentIntent(pendingIntent).build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(001, notification);




    }


    public void alertDialog(View view) {
        final AlertDialog aDialog;
        final AlertDialog.Builder arrayDialog = new AlertDialog.Builder(this);
        //tvTextArray = (TextView) findViewById(R.id.tvTextArray);
        arrayDialog.setTitle("Array Dialog").setMultiChoiceItems(namesList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b) {
                    if (!selectedItems.contains(i)) {
                        selectedItems.add(i);
                    } else {
                        selectedItems.remove(i);
                    }
                }
            }
        });
        arrayDialog.setCancelable(false);
        arrayDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String item = "";
                for (int j = 0; j < selectedItems.size(); j++) {
                    item = item + namesList[selectedItems.get(j)];
                    if (j != selectedItems.size() - 1) {
                        item = item + ", ";
                    }
                }
                //tvTextArray.setText(item);
            }
        });
        arrayDialog.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        arrayDialog.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for (int j = 0; j < checkedItems.length; j++) {
                    checkedItems[j] = false;
                    selectedItems.clear();
                    //tvTextArray.setText("");
                }
            }
        });
        aDialog = arrayDialog.create();
        aDialog.show();
    }
}
