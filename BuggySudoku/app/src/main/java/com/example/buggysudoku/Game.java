package com.example.buggysudoku;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game extends AppCompatActivity {


    GameBoard BuggySudoko;
    BuggyKeyBoard __Keyboard;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent passedInfo = getIntent();
        int GameLevel = passedInfo.getIntExtra("GameLevel",0);
        BuggySudoko = new GameBoard(Game.this, GameLevel);
        BuggySudoko.InitializeBoard();
        __Keyboard = new BuggyKeyBoard(Game.this);
        __Keyboard.InitiateBuggyKeyBaord();




    }

}
