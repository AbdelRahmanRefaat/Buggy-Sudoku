package com.example.buggysudoku;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class BuggyKeyBoard {

        TextView __Keyboard[];
        Activity GameState;
        public BuggyKeyBoard(Activity gameState){
            __Keyboard = new TextView[12];
            this.GameState = gameState;
        }
        public void InitiateBuggyKeyBaord(){
                    __Keyboard[0] = GameState.findViewById(R.id.btn1);
                    __Keyboard[1] = GameState.findViewById(R.id.btn2);
                    __Keyboard[2] = GameState.findViewById(R.id.btn3);
                    __Keyboard[3] = GameState.findViewById(R.id.btn4);
                    __Keyboard[4] = GameState.findViewById(R.id.btn5);
                    __Keyboard[5] = GameState.findViewById(R.id.btn6);
                    __Keyboard[6] = GameState.findViewById(R.id.btn7);
                    __Keyboard[7] = GameState.findViewById(R.id.btn8);
                    __Keyboard[8] = GameState.findViewById(R.id.btn9);
                    __Keyboard[9] = GameState.findViewById(R.id.btnDel);
                    for(int k = 0; k < 10; ++k){

                        final  int pos = k;
                            __Keyboard[k].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        final int i = GameBoard.selectedPosI;
                                        final int j = GameBoard.selectedPosJ;
                                        if(i == -1 || j == -1)return;

                                        if(pos == 9){

                                            GameBoard.updateCell(i,j,"",false); // delete button
                                            return;
                                        }
                                        GameBoard.updateCell(i,j,__Keyboard[pos].getText().toString(),false);
                                }
                            });
                    }

        }

}
