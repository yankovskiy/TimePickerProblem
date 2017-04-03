package ru.neverdark.timepickerproblem;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TimePicker mTimePicker;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mButton = (Button) findViewById(R.id.reset);
        mTimePicker = (TimePicker) findViewById(R.id.timePicker);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int currentHour = cal.get(Calendar.HOUR_OF_DAY);
                int currentMinute = cal.get(Calendar.MINUTE);

                int hourFromPicker;
                int minuteFromPicker;

                // without this function we have problem
                fixValues();

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

    private void fixValues() {
        // bug is not reproducible in APIs 24 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) return;
        try {
            int hour, minute;
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                hour = mTimePicker.getHour();
                minute = mTimePicker.getMinute();
            } else {
                hour = mTimePicker.getCurrentHour();
                minute = mTimePicker.getCurrentMinute();
            }

            Field mDelegateField = mTimePicker.getClass().getDeclaredField("mDelegate");
            mDelegateField.setAccessible(true);
            Class<?> clazz;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                clazz = Class.forName("android.widget.TimePickerClockDelegate");
            } else {
                clazz = Class.forName("android.widget.TimePickerSpinnerDelegate");
            }
            Field mInitialHourOfDayField = clazz.getDeclaredField("mInitialHourOfDay");
            Field mInitialMinuteField = clazz.getDeclaredField("mInitialMinute");
            mInitialHourOfDayField.setAccessible(true);
            mInitialMinuteField.setAccessible(true);
            mInitialHourOfDayField.setInt(mDelegateField.get(mTimePicker), hour);
            mInitialMinuteField.setInt(mDelegateField.get(mTimePicker), minute);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
