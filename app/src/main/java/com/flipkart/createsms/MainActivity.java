package com.flipkart.createsms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import static java.lang.Math.round;
import static java.lang.Math.random;
import static java.lang.Math.pow;
import static java.lang.Math.abs;
import static java.lang.Math.min;
import static org.apache.commons.lang3.StringUtils.leftPad;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                Cursor query = getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
//                if(query != null) {
//                    Snackbar.make(view, "Total "+query.getCount()+" messages", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }

                    TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    Snackbar.make(view, mngr.getDeviceId(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
//        new Thread(new SmsWriterThread()).start();
    }

    class SmsWriterThread implements Runnable {

        @Override
        public void run() {
            for(int i = 0 ;i < 1000;i++) {
                ContentValues values = new ContentValues();
                values.put("address", "DM-xyz");
                values.put("body", gen(160));
                values.put("date_sent", Long.toString(System.currentTimeMillis()));
                Uri insert = getContentResolver().insert(Uri.parse("content://sms/inbox"), values);
                Log.d("uri", insert.toString());
            }
        }

        public String gen(int length) {
            StringBuffer sb = new StringBuffer();
            for (int i = length; i > 0; i -= 12) {
                int n = min(12, abs(i));
                sb.append(leftPad(Long.toString(round(random() * pow(36, n)), 36), n, '0'));
            }
            return sb.toString();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
