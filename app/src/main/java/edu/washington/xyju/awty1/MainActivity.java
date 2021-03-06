package edu.washington.xyju.awty1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    Button control;
    EditText message, phoneNum, timeInterval;
    String ms, num, totalM, formatNum;
    Intent content;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        control = (Button) findViewById(R.id.control);
        message = (EditText) findViewById(R.id.etext1);
        phoneNum = (EditText) findViewById(R.id.etext2);
        timeInterval = (EditText) findViewById(R.id.etext3);

        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ms = message.getText().toString();
                num = phoneNum.getText().toString();
                totalM = timeInterval.getText().toString();

                if (message.length() != 0 && phoneNum.length() == 10 && totalM.length() != 0) {

                    if (totalM.charAt(0) == '0') {
                        Toast.makeText(MainActivity.this, "Interval must be greater than 0", Toast.LENGTH_SHORT).show();
                    }else{
                        control.setText("Stop");
                        formatNum = formatPhoneNumber(phoneNum.getText().toString());
                        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);

                        alarmIntent.putExtra("phone", formatNum);
                        alarmIntent.putExtra("message", ms);
                        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

                        start();

                        control.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                control.setText("Start");
                                cancel();
                            }
                        });
                    }
                }
            }
        });
    }

    public static String formatPhoneNumber(String number) {
        number = number.substring(0, number.length() - 4) + "-" + number.substring(number.length() - 4, number.length());
        number = number.substring(0, number.length() - 8) + ")" + number.substring(number.length() - 8, number.length());
        number = number.substring(0, number.length() - 12) + "(" + number.substring(number.length() - 12, number.length());
        return number;
    }

    public void start() {
        totalM = timeInterval.getText().toString();
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = Integer.parseInt(totalM)*60*1000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
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
