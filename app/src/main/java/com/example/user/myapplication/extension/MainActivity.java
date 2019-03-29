package com.example.user.myapplication.extension;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);


        btn1.setOnFocusChangeListener(onFocusChangeListener);
        btn2.setOnFocusChangeListener(onFocusChangeListener);
        btn3.setOnFocusChangeListener(onFocusChangeListener);
        btn4.setOnFocusChangeListener(onFocusChangeListener);
        btn5.setOnFocusChangeListener(onFocusChangeListener);
        btn6.setOnFocusChangeListener(onFocusChangeListener);
        btn7.setOnFocusChangeListener(onFocusChangeListener);
        btn8.setOnFocusChangeListener(onFocusChangeListener);
    }

    private View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                v.setScaleX(1.5F);
                v.setScaleY(1.5F);
            } else {
                v.setScaleX(1.0F);
                v.setScaleY(1.0F);
            }
        }
    };
}
