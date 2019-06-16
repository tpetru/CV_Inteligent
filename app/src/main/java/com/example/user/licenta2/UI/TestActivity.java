package com.example.user.licenta2.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.licenta2.MainActivity;
import com.example.user.licenta2.R;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button b1, b2;
    private TextView t1, t2, t3;
    private short showId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        b1 = (Button) findViewById(R.id.btnPrevious);
        b1.setOnClickListener(this);

        b2 = (Button) findViewById(R.id.btnNext);
        b2.setOnClickListener(this);

        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.textView2);
        t3 = (TextView) findViewById(R.id.textView3);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                showId++;
                showView(showId);
                break;

            case R.id.btnPrevious:
                showId--;
                showView(showId);
                break;
        }
    }

    private void showView(short view) {
        switch (view) {
            case 0:
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.INVISIBLE);
                t3.setVisibility(View.INVISIBLE);
                break;

            case 1:
                t1.setVisibility(View.INVISIBLE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.INVISIBLE);
                break;

            case 2:
                t1.setVisibility(View.INVISIBLE);
                t2.setVisibility(View.INVISIBLE);
                t3.setVisibility(View.VISIBLE);
                break;

            case 3:
                showId = 0;
                showView(showId);
                break;

            case -1:
                showId = 2;
                showView(showId);
                break;

            default:
                showId = 0;
                showView(showId);
                break;

        }

    }
}
