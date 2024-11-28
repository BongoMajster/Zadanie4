package com.example.statemanagmentapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_COUNT = "count";
    private static final String KEY_TEXT = "text";
    private static final String KEY_CHECKBOX = "checkbox";
    private static final String KEY_SWITCH = "switch";
    private static final String PREFS_NAME = "MyPrefs";

    private TextView textViewLicznik;
    private Button buttonZwieksz;
    private EditText editTextUzytkownik;
    private CheckBox checkBoxOpcja;
    private Switch switchTryb;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        count = preferences.getInt(KEY_COUNT, 0); // Default to 0 if not found
        String userInput = preferences.getString(KEY_TEXT, "");
        boolean isCheckBoxChecked = preferences.getBoolean(KEY_CHECKBOX, false);
        boolean isSwitchChecked = preferences.getBoolean(KEY_SWITCH, false);

        if (isSwitchChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_main);

        textViewLicznik = findViewById(R.id.textViewLicznik);
        buttonZwieksz = findViewById(R.id.buttonZwieksz);
        editTextUzytkownik = findViewById(R.id.editTextUzytkownik);
        checkBoxOpcja = findViewById(R.id.checkBoxOpcja);
        switchTryb = findViewById(R.id.switchTryb);

        editTextUzytkownik.setText(userInput);
        checkBoxOpcja.setChecked(isCheckBoxChecked);
        switchTryb.setChecked(isSwitchChecked);

        updateLicznikText();

        buttonZwieksz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                updateLicznikText();
            }
        });

        switchTryb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(KEY_COUNT, count);
        editor.putString(KEY_TEXT, editTextUzytkownik.getText().toString());
        editor.putBoolean(KEY_CHECKBOX, checkBoxOpcja.isChecked());
        editor.putBoolean(KEY_SWITCH, switchTryb.isChecked());
        editor.apply();
    }

    private void updateLicznikText() {
        textViewLicznik.setText("Licznik: " + count);
    }
}