# TimePickerProblem

Simple demo for illustrade TimePicker widget bug and workaround.

After user interact with widget (change time) setHour and setTime methods not works. Deprecated methods setCurrentHour and setCurrentMinute not works to.

Step for reproduce:
0. Comment function call fixValues() in MainActivity.java
1. Run app.
2. Change time in widget.
3. Press reset button. 

Result: no reset affect.

Workaround tested on the api 15-26

Bug on the Issue tracker: https://code.google.com/p/android/issues/detail?id=295123


