package com.example.myprevisaotempo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myprevisaotempo.model.Geocoder;

import java.util.concurrent.CompletableFuture;

public class Pesquisa extends AppCompatActivity {

    private Button btn_Search;
    private EditText edit_city;
    private EditText edit_state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //MainActivity mainActivityInstance = (MainActivity) getIntent().getSerializableExtra("MainActivityInstance");
        Geocoder geo = new Geocoder();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);
        btn_Search = findViewById(R.id.button2);
        edit_city = findViewById(R.id.edit_city);
        edit_state = findViewById(R.id.edit_state);

        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompletableFuture<String[]> future;
                if(!TextUtils.isEmpty(edit_city.getText().toString())) {
                    if (!TextUtils.isEmpty(edit_state.getText().toString())) {
                            future = geo.qCallGeo(edit_city.getText().toString() + '+' + edit_state.getText().toString());
                    } else {

                           future = geo.qCallGeo(edit_city.getText().toString());
                    }
                    System.out.println(edit_city.getText().toString());
                    future.thenCompose(Coord -> MainActivity.GetWeatherRetrifit(Coord[0], Coord[1], edit_city.getText().toString()))
                            .thenAccept(result -> {
                                // Handle the completion of the weather retrieval and any subsequent actions
                                finish();
                            });
                }
                return;
            }
        });


    }

}