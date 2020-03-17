package com.example.barcodereader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ShowResult extends AppCompatActivity {

    private static final String SCAN_DATA = "SCAN_DATA";
    private static final String KEY_TOTAL = "KEY_TOTAL";
    private static final String KEY_MASTAR = "KEY_MASTAR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView nameView = findViewById(R.id.name);
        TextView priceView = findViewById(R.id.price);
        TextView totalView = findViewById(R.id.total);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);

        String scanText = getIntent().getStringExtra(MainActivity.INPUT_RESULT);
        String[] split = scanText.split(":");

        if (split.length < 0 || 2 < split.length || !isNumber(split[1])) return;

        nameView.setText("品名 : " + split[0]);
        priceView.setText(" 値段 : " + split[1] + "\\");

        SharedPreferences sharedPreferences = getSharedPreferences(SCAN_DATA, MODE_PRIVATE);
        int total = sharedPreferences.getInt(KEY_TOTAL, 0);
        String strJson = sharedPreferences.getString(KEY_MASTAR,"");

        total += Integer.parseInt(split[1]);
        totalView.setText("合計金額 : " + total);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_TOTAL, total);
        editor.apply();

        ArrayList<String> mastorList = LoadList();
        mastorList.add(scanText);
        SaveList(mastorList);

    }

    public boolean isNumber(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void SaveList(ArrayList<String> list){
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<list.size();i++)jsonArray.put(list.get(i));

        SharedPreferences sharedPreferences = getSharedPreferences(SCAN_DATA,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_MASTAR,jsonArray.toString());
    }

    private ArrayList<String> LoadList(){
        ArrayList<String> list = new ArrayList<String>();
        SharedPreferences sharedPreferences = getSharedPreferences(SCAN_DATA,MODE_PRIVATE);
        String strJson = sharedPreferences.getString(KEY_MASTAR,"");
        if(!strJson.equals("")){
            try {
                JSONArray jsonArray = new JSONArray(strJson);
                for (int i=0;i<jsonArray.length();i++)list.add(jsonArray.getString(i));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return  list;
    }
}
