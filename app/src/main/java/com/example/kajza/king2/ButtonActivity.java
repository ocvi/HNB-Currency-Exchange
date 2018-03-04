package com.example.kajza.king2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kajza.king2.CurrencyView.DateCurrency;
import com.example.kajza.king2.CurrencyView.TodayCurrency;

public class ButtonActivity extends AppCompatActivity {

    private EditText etDate;
    private Button b_currency;
    private Button b_todayCurrency;
    private DateCurrency main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        b_currency =  (Button) findViewById(R.id.b_currency);
        b_todayCurrency  = (Button) findViewById(R.id.b_todayCurrency);

        b_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDate = (EditText) findViewById(R.id.etDate);
                String date = etDate.getText().toString();
                Intent tecaj = new Intent(ButtonActivity.this, DateCurrency.class);
                tecaj.putExtra("date", date);
                startActivity(tecaj);
            }
        });

        b_todayCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tecaj = new Intent(ButtonActivity.this, TodayCurrency.class);
                startActivity(tecaj);
            }
        });
    }
}
