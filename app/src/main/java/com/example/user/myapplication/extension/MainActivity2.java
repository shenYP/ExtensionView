package com.example.user.myapplication.extension;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;

    private ExtensionItemView item_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        item_view = findViewById(R.id.item_view);
        item_view.setImage(R.drawable.extension_person);
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
