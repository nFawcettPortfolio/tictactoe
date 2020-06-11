package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    boolean playerTurn=true;
    boolean gameOver=true;
    boolean singlePlayer;
    char[] gameState= {'_', '_', '_', '_', '_', '_', '_', '_', '_'};
    char[] copyState = new char [9];
    int[][] winningPos = {{0,1,2},{3,4,5,},{6,7,8}, // Horizontal
            {0,3,6},{1,4,7},{2,5,8,}, // Vertical
            {0,4,8},{2,4,6}}; // Diagonal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView winText = (TextView)findViewById(R.id.winnerText);
        Button onePlayerBtn = (Button)findViewById(R.id.onePlayerButton);
        Button twoPlayerBtn = (Button)findViewById(R.id.twoPlayerButton);
        winText.setVisibility(View.INVISIBLE);
    }

    //  GAME FUNCTIONALITY ****************************************************************************
    public void imgFadeIn(View view){
        ImageView image = (ImageView)view;
        int counterPos = Integer.parseInt(image.getTag().toString());
        if (!gameOver){
            if (gameState[Integer.parseInt(image.getTag().toString())]=='_'){
                if (playerTurn){
                    image.setImageResource(R.drawable.x);
                    image.animate().alpha(1).setDuration(2000).setInterpolator(new AccelerateInterpolator()).start();
                    gameState[counterPos]='x';
                    playerTurn=false;
                    checkGameState();
                    if (singlePlayer && !gameOver) {
                        boolean notAvailable = true;
                        while (notAvailable) {
                            Random r = new Random();
                            int choice = (int) (r.nextInt(9));
                            if (gameState[choice] == '_') {
                                gameState[choice]='o';
                                ImageView v = (ImageView)findViewById(R.id.mainActivity).findViewWithTag(String.valueOf(choice));
                                v.setImageResource(R.drawable.o);
                                v.animate().alpha(1).setDuration(2000).setInterpolator(new AccelerateInterpolator()).start();
                                notAvailable=false;
                            }

                        }
                        checkGameState();
                        playerTurn=true;
                    }
                    // 2 Player
                }else {
                        image.setImageResource(R.drawable.o);
                        image.animate().alpha(1).setDuration(2000).setInterpolator(new AccelerateInterpolator()).start();
                        playerTurn = true;
                        gameState[counterPos] = 'o';
                        checkGameState();
                }
            }
        }

    }
    public void checkGameState(){ // ACTUAL CHECK STATE TO WIN
        TextView winText = (TextView)findViewById(R.id.winnerText);
        Button onePlayerBtn = (Button)findViewById(R.id.onePlayerButton);
        Button twoPlayerBtn = (Button)findViewById(R.id.twoPlayerButton);
        for (int[] pos : winningPos) {
            if (gameState[pos[0]] == 'x' && gameState[pos[1]] == 'x' && gameState[pos[2]] == 'x') {
                winText.setText("X Wins!");
                winText.setVisibility(View.VISIBLE);
                onePlayerBtn.setVisibility(View.VISIBLE);
                twoPlayerBtn.setVisibility(View.VISIBLE);
                gameOver = true;
            }
            if (gameState[pos[0]] == 'o' && gameState[pos[1]] == 'o' && gameState[pos[2]] == 'o') {
                winText.setText("O Wins!");
                winText.setVisibility(View.VISIBLE);
                onePlayerBtn.setVisibility(View.VISIBLE);
                twoPlayerBtn.setVisibility(View.VISIBLE);
                gameOver = true;
            }
        }
        int count=0;
        if (!gameOver){
            for (char c : gameState){
                if (c=='_'){
                    count++;
                }
            }
            if(count==0){
                winText.setText("Tie Game!");
                winText.setVisibility(View.VISIBLE);
                onePlayerBtn.setVisibility(View.VISIBLE);
                twoPlayerBtn.setVisibility(View.VISIBLE);
                gameOver = true;
            }
        }
    }

 // NEW GAME ****************************************************************************
    public void newGame(View view) { // Restart game
        switch (view.getId()){
            case R.id.onePlayerButton:
                singlePlayer=true;
                break;
            case R.id.twoPlayerButton:
                singlePlayer=false;
                break;
        }
        Log.i("Player mode:",String.valueOf(singlePlayer));
        TextView winText = (TextView)findViewById(R.id.winnerText);
        Button onePlayerBtn = (Button)findViewById(R.id.onePlayerButton);
        Button twoPlayerBtn = (Button)findViewById(R.id.twoPlayerButton);
        winText.setVisibility(View.INVISIBLE);
        onePlayerBtn.setVisibility(View.INVISIBLE);
        twoPlayerBtn.setVisibility(View.INVISIBLE);
        for (int i =0;i<gameState.length;i++) {
            gameState[i]='_';
        }
        gameOver=false;
        androidx.gridlayout.widget.GridLayout layout = findViewById(R.id.gridLayout);
        for(int i = 0; i<layout.getChildCount();i++){
            ImageView child = (ImageView)layout.getChildAt(i);
            child.setImageDrawable(null);
        }
        playerTurn=true;

    }
}