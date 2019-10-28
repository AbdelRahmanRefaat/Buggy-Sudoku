package com.example.buggysudoku;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class GameBoard  {

    static Activity GameState;
    private static BoardCell [][]Board;

    public static int remainingMoves = 0;
    public static int GameHints = 5;
    private static TextView Hintbutton;
    private static TextView CheckButton;
    private  BuggyGenerator gameGenerator;
    private  BuggySolver solver;


    public static final int EASY   = 0;
    public static final int MEDIUM = 1;
    public static final int HARD   = 2;
    public static final int TEST   = -1;
    public static int selectedPosI;
    public static int selectedPosJ;
    private final static int FullMsk = (1 << 9)-1;
    private int GameLevel;
    public GameBoard(Activity GameState ,int GameLevel){

        this.GameState = GameState;
        selectedPosJ = selectedPosI = -1;
        GameHints = 5;
        this.GameLevel = GameLevel;
        Board = new BoardCell[9][9];
        solver = new BuggySolver();

    }
    public void InitializeBoard(){
        InitializeButtons();
        for(int i = 0; i < 9; ++i){
            for(int j = 0; j < 9; ++j){
                final int r = i , c = j;
                Board[i][j].Cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // this if : for reseting any other selected buttons before we select the clicked one
                        if(selectedPosI != -1 || selectedPosJ != -1){
                            if(selectedPosI == r && selectedPosJ == c) {
                                Board[selectedPosI][selectedPosJ].toggleSelection(selectedPosI, selectedPosJ);
                                return;
                            }
                            Board[selectedPosI][selectedPosJ].toggleSelection(selectedPosI, selectedPosJ);
                        }
                        Board[r][c].toggleSelection(r , c);


                    }
                });
            }
        }
        gameGenerator = new BuggyGenerator(Board,GameLevel);
        gameGenerator.Generate(0,0);
        gameGenerator.setLeve();

    }
    private void InitializeButtons(){



        for(int i = 0; i < 9;++i){
            for(int j = 0; j < 9; ++j){
                Board[i][j] = new BoardCell();
            }
        }
        // Block (1 , 1)
        Board[0][0].Cell = (TextView) GameState.findViewById(R.id.cell_11);
        Board[0][1].Cell = (TextView) GameState.findViewById(R.id.cell_12);
        Board[0][2].Cell = (TextView) GameState.findViewById(R.id.cell_13);
        Board[1][0].Cell = (TextView) GameState.findViewById(R.id.cell_21);
        Board[1][1].Cell = (TextView) GameState.findViewById(R.id.cell_22);
        Board[1][2].Cell = (TextView) GameState.findViewById(R.id.cell_23);
        Board[2][0].Cell = (TextView) GameState.findViewById(R.id.cell_31);
        Board[2][1].Cell = (TextView) GameState.findViewById(R.id.cell_32);
        Board[2][2].Cell = (TextView) GameState.findViewById(R.id.cell_33);

        // Block (1 , 2)
        Board[0][3].Cell = (TextView) GameState.findViewById(R.id.cell_14);
        Board[0][4].Cell = (TextView) GameState.findViewById(R.id.cell_15);
        Board[0][5].Cell = (TextView) GameState.findViewById(R.id.cell_16);
        Board[1][3].Cell = (TextView) GameState.findViewById(R.id.cell_24);
        Board[1][4].Cell = (TextView) GameState.findViewById(R.id.cell_25);
        Board[1][5].Cell = (TextView) GameState.findViewById(R.id.cell_26);
        Board[2][3].Cell = (TextView) GameState.findViewById(R.id.cell_34);
        Board[2][4].Cell = (TextView) GameState.findViewById(R.id.cell_35);
        Board[2][5].Cell = (TextView) GameState.findViewById(R.id.cell_36);

        // Block (1 , 3)
        Board[0][6].Cell = (TextView) GameState.findViewById(R.id.cell_17);
        Board[0][7].Cell = (TextView) GameState.findViewById(R.id.cell_18);
        Board[0][8].Cell = (TextView) GameState.findViewById(R.id.cell_19);
        Board[1][6].Cell = (TextView) GameState.findViewById(R.id.cell_27);
        Board[1][7].Cell = (TextView) GameState.findViewById(R.id.cell_28);
        Board[1][8].Cell = (TextView) GameState.findViewById(R.id.cell_29);
        Board[2][6].Cell = (TextView) GameState.findViewById(R.id.cell_37);
        Board[2][7].Cell = (TextView) GameState.findViewById(R.id.cell_38);
        Board[2][8].Cell = (TextView) GameState.findViewById(R.id.cell_39);

        // Block (2 , 1)
        Board[3][0].Cell = (TextView) GameState.findViewById(R.id.cell_41);
        Board[3][1].Cell = (TextView) GameState.findViewById(R.id.cell_42);
        Board[3][2].Cell = (TextView) GameState.findViewById(R.id.cell_43);
        Board[4][0].Cell = (TextView) GameState.findViewById(R.id.cell_51);
        Board[4][1].Cell = (TextView) GameState.findViewById(R.id.cell_52);
        Board[4][2].Cell = (TextView) GameState.findViewById(R.id.cell_53);
        Board[5][0].Cell = (TextView) GameState.findViewById(R.id.cell_61);
        Board[5][1].Cell = (TextView) GameState.findViewById(R.id.cell_62);
        Board[5][2].Cell = (TextView) GameState.findViewById(R.id.cell_63);

        // Block (2 , 2)
        Board[3][3].Cell = (TextView) GameState.findViewById(R.id.cell_44);
        Board[3][4].Cell = (TextView) GameState.findViewById(R.id.cell_45);
        Board[3][5].Cell = (TextView) GameState.findViewById(R.id.cell_46);
        Board[4][3].Cell = (TextView) GameState.findViewById(R.id.cell_54);
        Board[4][4].Cell = (TextView) GameState.findViewById(R.id.cell_55);
        Board[4][5].Cell = (TextView) GameState.findViewById(R.id.cell_56);
        Board[5][3].Cell = (TextView) GameState.findViewById(R.id.cell_64);
        Board[5][4].Cell = (TextView) GameState.findViewById(R.id.cell_65);
        Board[5][5].Cell = (TextView) GameState.findViewById(R.id.cell_66);


        // Block (2 , 3)
        Board[3][6].Cell = (TextView) GameState.findViewById(R.id.cell_47);
        Board[3][7].Cell = (TextView) GameState.findViewById(R.id.cell_48);
        Board[3][8].Cell = (TextView) GameState.findViewById(R.id.cell_49);
        Board[4][6].Cell = (TextView) GameState.findViewById(R.id.cell_57);
        Board[4][7].Cell = (TextView) GameState.findViewById(R.id.cell_58);
        Board[4][8].Cell = (TextView) GameState.findViewById(R.id.cell_59);
        Board[5][6].Cell = (TextView) GameState.findViewById(R.id.cell_67);
        Board[5][7].Cell = (TextView) GameState.findViewById(R.id.cell_68);
        Board[5][8].Cell = (TextView) GameState.findViewById(R.id.cell_69);


        // Block (3 , 1)
        Board[6][0].Cell = (TextView) GameState.findViewById(R.id.cell_71);
        Board[6][1].Cell = (TextView) GameState.findViewById(R.id.cell_72);
        Board[6][2].Cell = (TextView) GameState.findViewById(R.id.cell_73);
        Board[7][0].Cell = (TextView) GameState.findViewById(R.id.cell_81);
        Board[7][1].Cell = (TextView) GameState.findViewById(R.id.cell_82);
        Board[7][2].Cell = (TextView) GameState.findViewById(R.id.cell_83);
        Board[8][0].Cell = (TextView) GameState.findViewById(R.id.cell_91);
        Board[8][1].Cell = (TextView) GameState.findViewById(R.id.cell_92);
        Board[8][2].Cell = (TextView) GameState.findViewById(R.id.cell_93);


        // Block (3 , 2)
        Board[6][3].Cell = (TextView) GameState.findViewById(R.id.cell_74);
        Board[6][4].Cell = (TextView) GameState.findViewById(R.id.cell_75);
        Board[6][5].Cell = (TextView) GameState.findViewById(R.id.cell_76);
        Board[7][3].Cell = (TextView) GameState.findViewById(R.id.cell_84);
        Board[7][4].Cell = (TextView) GameState.findViewById(R.id.cell_85);
        Board[7][5].Cell = (TextView) GameState.findViewById(R.id.cell_86);
        Board[8][3].Cell = (TextView) GameState.findViewById(R.id.cell_94);
        Board[8][4].Cell = (TextView) GameState.findViewById(R.id.cell_95);
        Board[8][5].Cell = (TextView) GameState.findViewById(R.id.cell_96);


        // Block (3 , 3)
        Board[6][6].Cell = (TextView) GameState.findViewById(R.id.cell_77);
        Board[6][7].Cell = (TextView) GameState.findViewById(R.id.cell_78);
        Board[6][8].Cell = (TextView) GameState.findViewById(R.id.cell_79);
        Board[7][6].Cell = (TextView) GameState.findViewById(R.id.cell_87);
        Board[7][7].Cell = (TextView) GameState.findViewById(R.id.cell_88);
        Board[7][8].Cell = (TextView) GameState.findViewById(R.id.cell_89);
        Board[8][6].Cell = (TextView) GameState.findViewById(R.id.cell_97);
        Board[8][7].Cell = (TextView) GameState.findViewById(R.id.cell_98);
        Board[8][8].Cell = (TextView) GameState.findViewById(R.id.cell_99);
        Hintbutton = GameState.findViewById(R.id.hintbtn);
        Hintbutton.setText("" + GameHints + " HINTS!!!");
        Hintbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    solver.getHint(Board);
                    Hintbutton.setText("" + GameHints + " HINTS!!!");
            }
        });
        CheckButton = GameState.findViewById(R.id.Checkbtn);
        CheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    solver.Check(Board);
            }
        });

    }

    // hinty : is for if it was comming from hint so we change it to solide so when checking for erros we don't mark it
    public static void updateCell(int i, int j, String value , boolean hinty){
            if(i == -1 || j == -1)return; // invalid cell
            if(Board[i][j].isSolid())return; // Non-Changeable Cell
            if(Board[i][j].Cell.getText() != "" && value == "") { // deleting value of a cell
                GameBoard.remainingMoves++;
            }else if(Board[i][j].Cell.getText() == "" && value != ""){ // putting a value of a cell
                GameBoard.remainingMoves--;

            }
            if(hinty) {
                Board[i][j].setSolid();
                Board[i][j].Cell.setBackgroundResource(R.drawable.buggy_cell_boarder);
            }
            Board[i][j].Cell.setText(value);
            selectedPosJ = j;
            selectedPosI = i;
            // Toast.makeText(GameState, "Still !!" + GameBoard.remainingMoves, Toast.LENGTH_SHORT).show();
            //TODO: Add validating function the asure that the board is solved wehn remMoves = 0
            if(GameBoard.remainingMoves == 0){ // board fully filled

                    if(validateBoard()){

                        Toast.makeText(GameState, "Congratulations You Won !!", Toast.LENGTH_SHORT).show();
                    }
            }
    }
    private static int parseInt(String str){
            return (str.charAt(0)-'0');
    }
    // checks if the board is solved correctly
    // condition to call it : - The Board has to be fully filled otherwize you may get error
    private static boolean validateBoard() {
             int val , row_msk , col_msk;
             for (int i  = 0; i < 9; ++i){
                     row_msk  =  col_msk = 0;
                    for(int j = 0; j < 9; ++j){
                           val = parseInt(Board[i][j].Cell.getText().toString()) - 1;
                           row_msk |= (1 << val);
                           val = parseInt(Board[j][i].Cell.getText().toString()) - 1;
                           col_msk |= (1 << val);
                    }
                    if(col_msk != FullMsk || row_msk != FullMsk)
                        return false;

            }
            int block_msk;
            for(int i = 0; i < 9; i += 3){
                for(int j = 0; j < 9; j += 3){
                        block_msk = 0;
                        for(int row = i; row < i + 3; ++row){

                                for(int col = j; col < j + 3; ++col){
                                    val = parseInt(Board[row][col].Cell.getText().toString()) - 1;
                                    block_msk |= (1 << val);
                                }
                        }
                        if(block_msk != FullMsk)return false;
                }
            }
            return true;
    }

}
