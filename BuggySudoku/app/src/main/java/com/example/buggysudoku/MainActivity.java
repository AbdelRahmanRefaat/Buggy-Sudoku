package com.example.buggysudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button EasyButton = findViewById(R.id.EasyMoodBtn);
        EasyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(MainActivity.this,Game.class);
                go.putExtra("GameLevel", GameBoard.EASY);
                startActivity(go);
            }
        });
        Button MediumButton = findViewById(R.id.MediumMoodBtn);
        MediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(MainActivity.this,Game.class);
                go.putExtra("GameLevel", GameBoard.MEDIUM);
                startActivity(go);
            }
        });

        Button HardButton = findViewById(R.id.HardMoodBtn);
        HardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(MainActivity.this,Game.class);
                go.putExtra("GameLevel", GameBoard.HARD);
                startActivity(go);
            }
        });
    }
    // TODO : add Game level buttons to the main activity
}
