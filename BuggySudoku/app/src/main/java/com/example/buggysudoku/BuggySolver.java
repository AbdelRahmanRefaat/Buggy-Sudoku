package com.example.buggysudoku;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BuggySolver {

    private BoardCell[][]Board;
    private boolean finished = false;
    ArrayList<Pair< Pair<Integer , Integer > , Integer > > hintsList;
    public BuggySolver(){
        Board = new BoardCell[9][9];


    }
    public void getHint(BoardCell [][] curGame){


        if(GameBoard.selectedPosI == -1 || GameBoard.selectedPosJ == -1)return ;
        if(GameBoard.GameHints == 0)return;
        int i = GameBoard.selectedPosI ;
        int j = GameBoard.selectedPosJ ;
        hintsList = new  ArrayList<Pair< Pair<Integer , Integer > , Integer > >();
        Board = null;
        Board = curGame;
        if(Board[i][j].Cell.getText() != "")return;
        if(!validateBoard())return ; // checks if the player didn't make a mistake and douplicated something
                                    // so that the solver doesn't go in infinte loop
        finished = false;
        solve(0,0);

        if(hintsList.size() == 0)return; // Incase it's a full Board and there's nothing to give hint for



        int value = -1;
        for(int idx = 0; idx < hintsList.size(); ++idx){
                if(hintsList.get(idx).first.first == i && hintsList.get(idx).first.second == j){
                    value = hintsList.get(idx).second;
                    break;
                }
        }
        if(value == -1){
                return;
        }
        GameBoard.updateCell(i,j,""+value, true);

        GameBoard.GameHints--;


    }


    private int parseInt(String rhs){
        if(rhs.length() == 0)return -1;
        return  (rhs.charAt(0) -'0');
    }
    private boolean valid_row(int i, int n){ // Check if the number isn't in the row
        for(int k = 0; k < 9; ++k){

            if(n == parseInt(Board[i][k].Cell.getText().toString())){
                return false;
            }
        }
        return true;
    }
    private boolean valid_col(int j, int n){ // checks if the number isn't in the coloumn
        for(int k = 0; k < 9; ++k){
            if(n == parseInt(Board[k][j].Cell.getText().toString())){
                return false;
            }
        }
        return true;
    }
    private boolean valid_block(int i , int j, int n){ // Checks if the number isn't in that block
        int rowstart = (i/3) * 3; // for upper row of the block : the start of the block
        int colStart = (j/3) * 3; // for the left most col of the block

        for(int row = rowstart; row < rowstart + 3; ++row){
            for(int col = colStart; col < colStart + 3; ++col){
                if(n == parseInt(Board[row][col].Cell.getText().toString()))
                    return false;
            }
        }
        return true;
    }
    private void solve(int i , int j){
            if(finished)
                    return ;
            if(i == 9){
                finished = true;
                return;
            }
            if(Board[i][j].Cell.getText() != "") {
                if(j == 8)
                        solve(i + 1, 0);
                else
                        solve(i , j + 1);
            }else{

                    for(int k = 1; k <= 9; ++k){
                            if(!valid_row(i,k) || !valid_col(j , k) || !valid_block(i , j , k))continue;
                            Board[i][j].Cell.setText(""+k);
                            if(j == 8)solve(i + 1, 0);
                            else solve(i , j + 1);
                            if(finished){
                                hintsList.add(new Pair<Pair<Integer, Integer>, Integer>
                                        (new Pair<Integer, Integer>(i,j),k));
                            }
                            Board[i][j].Cell.setText("");
                            if(finished)
                                    return ;

                    }
            }
    }

    // checks if the player did something wrong while solvint
    // so when we run the solver it doesn't go in infinite loop :)
    private  boolean validateBoard( ) {

        int val , row_msk , col_msk;
        for (int i  = 0; i < 9; ++i){
            row_msk  =  col_msk = 0;
            for(int j = 0; j < 9; ++j){

                val = parseInt(Board[i][j].Cell.getText().toString()) - 1;
                 // the ret of parseint - 1  it will be -2 if it's an emptyCell
                if(val != -2 && (row_msk & (1 << val)) != 0)return false;// means we already had that value
                if(val != -2)
                row_msk |= (1 << val);

                val = parseInt(Board[j][i].Cell.getText().toString()) - 1;
                if(val != -2 && (col_msk & (1 << val)) != 0)return false; // means we already had that value
                if(val != -2)
                col_msk |= (1 << val);
            }
        }
        int block_msk;
        for(int i = 0; i < 9; i += 3){
            for(int j = 0; j < 9; j += 3){
                block_msk = 0;
                for(int row = i; row < i + 3; ++row){

                    for(int col = j; col < j + 3; ++col){
                        val = parseInt(Board[row][col].Cell.getText().toString()) - 1;
                        if(val == -2)continue; // the ret of parseint - 1  it will be -2 if it's an emptyCell
                        if((block_msk & (1 << val)) != 0)return false; // means we already had that value in the same block
                        block_msk |= (1 << val);
                    }
                }

            }
        }
        return true;
    }
    public void Check(BoardCell[][] curGame){

            Board = curGame;

            int rowCounter[] = new int[10];
            int colCounter[] = new int[10];
            int blockCounter[] = new int[10];
            int val;
            // Checking for rows and cols
            for(int i = 0; i < 9; ++i){
                    Arrays.fill(rowCounter,0);
                    Arrays.fill(colCounter,0);
                    for(int j = 0; j < 9; ++j){

                        val = parseInt(Board[i][j].Cell.getText().toString()) - 1;
                        if(val != -2) { // the ret of parseint - 1  it will be -2 if it's an emptyCell
                            rowCounter[val]++;
                        }
                        val = parseInt(Board[j][i].Cell.getText().toString()) - 1;
                        if(val != -2) {
                            colCounter[val]++;
                        }
                    }

                    for(int k = 0; k < 9; ++k){

                        if(rowCounter[k] > 1)rowCounter[k] = 1;
                        else rowCounter[k] = 0;
                        if(colCounter[k] > 1)colCounter[k] = 1;
                        else colCounter[k] = 0;
                    }
                    for(int j = 0; j < 9; ++j){
                        val = parseInt(Board[i][j].Cell.getText().toString()) - 1;
                         // the ret of parseint - 1  it will be -2 if it's an emptyCell
                        if(val != -2 && rowCounter[val] == 1 && !Board[i][j].isSolid()){
                            Board[i][j].Cell.setBackgroundResource(R.drawable.wrong_cell_border);
                        }
                        val = parseInt(Board[j][i].Cell.getText().toString()) - 1;

                        if(val != -2 && colCounter[val] == 1 && !Board[j][i].isSolid()){
                            Board[j][i].Cell.setBackgroundResource(R.drawable.wrong_cell_border);
                        }
                    }
            }

            // Checking for blocks
            for(int i = 0; i < 9; i += 3){

                for(int j = 0; j < 9; j += 3){
                    Arrays.fill(blockCounter,0);
                    for(int row = i; row < i + 3; ++row){

                        for(int col = j; col < j + 3; ++col){
                            val = parseInt(Board[row][col].Cell.getText().toString()) - 1;
                            if(val == -2)continue; // the ret of parseint - 1  it will be -2 if it's an emptyCell

                            blockCounter[val]++;
                            Log.v("Sucks ", " BlockCounter " + val + " " + blockCounter[val]);
                        }
                    }
                    for(int k = 0; k < 9; ++k){
                            if(blockCounter[k] > 1)blockCounter[k] = 1;
                            else blockCounter[k] = 0;
                    }

                    for(int row = i; row < i + 3; ++row){

                        for(int col = j; col < j + 3; ++col){
                            val = parseInt(Board[row][col].Cell.getText().toString()) - 1;
                            if(val == -2)continue; // the ret of parseint - 1  it will be -2 if it's an emptyCell
                            if(blockCounter[val] == 1 && !Board[row][col].isSolid()){
                                Board[row][col].Cell.setBackgroundResource(R.drawable.wrong_cell_border);
                            }

                        }
                    }

                }
            }
    }
}
