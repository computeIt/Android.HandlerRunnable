package com.example.addy.androidhandleroncheckedchange;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView textView;
    CheckBox checkBox;

    Handler handler;

    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setMax(100);
        progressBar.setProgress(0);

        textView = findViewById(R.id.textview);
        checkBox = findViewById(R.id.checkbox);

        handler = new Handler();

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textView.setVisibility(View.VISIBLE);
                    handler.post(showInfo);
                } else {
                    textView.setVisibility(View.GONE);
                    handler.removeCallbacks(showInfo);
                }
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (count = 1; count < 100; count++) {
                        TimeUnit.MILLISECONDS.sleep(100);
                        //обновляем progressbar
                        handler.post(updateProgress);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }

    //обновление проргессбара
    Runnable updateProgress = new Runnable() {
        @Override
        public void run() {
            progressBar.setProgress(count);
        }
    };

    //
    Runnable showInfo = new Runnable() {
        @Override
        public void run() {
            textView.setText("count = " + count);
            handler.postDelayed(showInfo, 1000);
        }
    };

}

