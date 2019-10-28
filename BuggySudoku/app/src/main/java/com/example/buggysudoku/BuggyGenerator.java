package com.example.buggysudoku;

import android.graphics.Color;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Random;

public class BuggyGenerator {
    final  BoardCell[][]Board;
    private boolean solutionFound = false;
    final int GameLevel;
    int SolidCells;
    // cellsList: to hold x , y and value of each cell
    // we will randomize of it to put solid values in the board
    ArrayList< Pair<Pair<Integer,Integer>, Integer> > cellsList;
    BuggyGenerator(BoardCell[][] rhs , int GameLevel){
            Board = rhs;
            this.GameLevel = GameLevel;
            cellsList = new ArrayList< Pair<Pair<Integer,Integer>, Integer> >();
    }
    private int parseInt(String rhs){
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
    // This function tries to find a solution using brute force + backtracking
    // it tries to fill the row one by one as it's faster and do-able
    public void Generate(int i , int j){ // Board Generator
        if(solutionFound)return ; // we found one solution so back we don't need more
        if(i == 9){ // found solution and filled all rows
            solutionFound = true;
            return ;
        }
        for(int k = 1; k <= 9; ++k){
                if(!valid_block(i,j,k) || !valid_col(j,k) || !valid_row(i,k))
                        continue;
                Board[i][j].Cell.setText(""+k);
                if(j == 8)Generate(i + 1,0);
                else Generate(i,j + 1);
                if(solutionFound){
                    cellsList.add(new Pair(new Pair<Integer, Integer>(i,j), k));
                    return;
                }
                Board[i][j].Cell.setText("0"); // reset this cell cause this value in this cell has returned false on us
        }

    }
    // each level has its own number of solid cells
    public void setLeve(){

        switch (GameLevel){
                case GameBoard.EASY:
                    SolidCells = 35;
                    break;
                case GameBoard.MEDIUM:
                    SolidCells = 28;
                    break;
                case GameBoard.HARD:
                    SolidCells = 21;
                    break;
                case GameBoard.TEST:
                    SolidCells = 81;
                    break;
                 default:break;
            }
            initiate();  // Put these amount of SolidCells in the board randomly

    }
    // initiate the board according to the Game Level
    // Randomizing over indices of the cellsList to set the board cells values
    public void initiate(){

        Random k = new Random();
        int i , j , value, indx;
        Log.v("SolidCells" , " Here " + SolidCells);
        while (SolidCells-- > 0){
                 indx = k.nextInt((int)cellsList.size());
                 i = cellsList.get(indx).first.first;
                 j = cellsList.get(indx).first.second;
                 value = cellsList.get(indx).second;

                 Board[i][j].Cell.setText("" + value);
                 Board[i][j].Cell.setTextColor(Color.parseColor("#000000"));
                 Board[i][j].setSolid();
                 cellsList.remove(indx);
        }

        // Reseting the non solid cells now
        for( i = 0; i < 9; ++i){
            for( j = 0; j < 9; ++j){
                if(!Board[i][j].isSolid()){
                    GameBoard.remainingMoves++;
                    Board[i][j].Cell.setText("");
                }
            }
        }
    }
}
