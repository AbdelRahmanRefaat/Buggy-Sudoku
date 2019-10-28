package com.example.buggysudoku;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.TextView;

public class BoardCell {
    public TextView Cell;
    private boolean solid;
    private boolean selected;
    BoardCell(TextView rhs){
        this.Cell = rhs;
        selected = false;
    }
    BoardCell(){
        selected = false;
    }


    public boolean isSolid(){
            return this.solid;
    }
    public void setSolid(){
            this.solid = true;
    }
    public boolean isSelected(){
            return selected;
    }
    private void setSelection(){
            selected = true;
    }
    private void removeSelection(){
            selected = false;
    }
    public void toggleSelection(int i , int j){

            if(isSolid())return ; // if solid we can't change it or even select it so back off
            if(!selected ) {
                selected = !selected;
                this.Cell.setBackgroundResource(R.drawable.selected_cell_border);
                // SelectedPosI , SelectedPosJ is a static variable in the gameboard tells us which cell is currently selected
                GameBoard.selectedPosI = i;
                GameBoard.selectedPosJ = j;

            }else{

                selected = !selected;
                this.Cell.setBackgroundResource(R.drawable.buggy_cell_boarder);
                // SelectedPosI , SelectedPosJ is a static variable in the gameboard tells us which cell is currently selected
                // this -1,-1 means we're not selecting anythig now so reset it
                GameBoard.selectedPosJ = -1;
                GameBoard.selectedPosI = -1;
            }

    }
}
