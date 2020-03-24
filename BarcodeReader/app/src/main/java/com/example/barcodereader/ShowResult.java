package com.example.barcodereader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class ShowResult extends AppCompatActivity {

    public static final String SCAN_DATA = "SCAN_DATA";
    public static final String KEY_TOTAL = "KEY_TOTAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);

        TextView nameView = findViewById(R.id.name);
        TextView priceView = findViewById(R.id.price);
        TextView totalView = findViewById(R.id.total);

        String scanText = getIntent().getStringExtra(MainActivity.INPUT_RESULT);
        String[] split = scanText.split("：");

        if (split.length < 0 || 2 < split.length || !isNumber(split[1])) return;

        nameView.setText("品名 : " + split[0]);
        priceView.setText(" 値段 : " + split[1] + "円");

        SharedPreferences sharedPreferences = getSharedPreferences(SCAN_DATA, MODE_PRIVATE);
        int total = sharedPreferences.getInt(KEY_TOTAL, 0);

        total += Integer.parseInt(split[1]);
        totalView.setText("合計金額 : " + total);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_TOTAL, total);
        editor.apply();

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        };
        timer.schedule(task, 5 * 1000);
    }

    public boolean isNumber(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                break;

            case KeyEvent.KEYCODE_ENTER:
                Toast.makeText(getApplicationContext(), "Clear price data.", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences(SCAN_DATA, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_TOTAL, 0);
                editor.apply();
                break;

            case KeyEvent.KEYCODE_BACK:
                finish();
                break;

            case KeyEvent.KEYCODE_MENU:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }
}
