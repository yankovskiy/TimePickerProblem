package ru.neverdark.timepickerproblem;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private TimePicker mTimePicker;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.reset);
        mTimePicker = (TimePicker) findViewById(R.id.timePicker);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int currentHour = cal.get(Calendar.HOUR_OF_DAY);
                int currentMinute = cal.get(Calendar.MINUTE);

                int hourFromPicker;
                int minuteFromPicker;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mTimePicker.setHour(currentHour);
                    mTimePicker.setMinute(currentMinute);
                    hourFromPicker = mTimePicker.getHour();
                    minuteFromPicker = mTimePicker.getMinute();
                } else {
                    mTimePicker.setCurrentHour(currentHour);
                    mTimePicker.setCurrentMinute(currentMinute);
                    hourFromPicker = mTimePicker.getCurrentHour();
                    minuteFromPicker = mTimePicker.getCurrentMinute();
                }

                Log.v(TAG, "onClick hour: " + currentHour + "=>" + hourFromPicker);
                Log.v(TAG, "onClick minute: " + currentMinute + "=>" + minuteFromPicker);
            }
        });
    }
}
