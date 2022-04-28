package com.asm.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int time;                   //Current time
    int defaultNum=30;          // default time in seconds
    TextView timer;             //Timer Textview
    int buttonPressed=0;        //Used to toggle between start and stop button
    Button button;              //The only button
    CountDownTimer loopTimer;   //Drives the timer

    public void getTimer(){
        //gets current time from the textview and sets that time to the time variable
        timer=findViewById(R.id.timer);
        String timerText=timer.getText().toString();
        int firstPart= Integer.parseInt(timerText.split(":")[0]);
        int secondPart= Integer.parseInt(timerText.split(":")[1]);
        time=(firstPart*60)+secondPart;//in seconds

    }

    public void setTimer(int second) {
        //set the timer textview to any given seconds
        int firstPart=second/60;
        int secondPart=second%60;
        String total=firstPart+":"+secondPart;
        timer.setText(total);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTimer();//sets the time variable

        MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.air_horn);// gets the finish tune
        SeekBar seekBar=findViewById(R.id.seekBar);//gets the seekbar
        seekBar.setProgress(defaultNum);//Aligning the seekbar to the timer
        button=findViewById(R.id.button);
        seekBar.setMax(120);// Sets Timer and Seekbar limit in Seconds

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setTimer(i);//update timer with seekbar
                getTimer();//sets the time variable
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        button.setOnClickListener(view -> {//on click operation
            if(buttonPressed==0) {
                //for pressing start
                button.setText("Stop");
                seekBar.setEnabled(false);
                buttonPressed = 1;
                loopTimer=new CountDownTimer(time* 1000L,1000){

                    @Override
                    public void onTick(long l) {
                        int seconds=Integer.parseInt(String.valueOf(l/1000));
                        seekBar.setProgress(seconds);
                        setTimer(seconds);
                    }

                    @Override
                    public void onFinish() {
                        mediaPlayer.start();
                        button.setText("Start");
                        buttonPressed=0;
                        loopTimer.cancel();
                        seekBar.setProgress(defaultNum);
                        setTimer(defaultNum);
                        seekBar.setEnabled(true);
                        String statement="Finished";
                        Toast.makeText(MainActivity.this, statement,
                                Toast.LENGTH_LONG).show();
                    }
                }.start();
            }
            else{
                //for pressing stop
                mediaPlayer.pause();
                button.setText("Start");
                buttonPressed=0;
                loopTimer.cancel();
                seekBar.setProgress(defaultNum);
                setTimer(defaultNum);
                seekBar.setEnabled(true);//enable or disabling seekbar


            }

        });






    }
}