package Game;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Game {

    private AI ai;

    private static final int[][] board = new int[7][6];//each col is one array and the whole is array of columns
    private static int turn = -1;
    public static boolean clickUnlocked = true;

    public static Game instance;
    public static void startGame(AI ai) throws Exception {
        if (instance == null) {
            instance = new Game(ai);
        }
    }
    public static void startGame(){
        if (instance==null){
            instance = new Game();
        }
    }
    public static Game getInstance(){
        if(instance==null){
            instance = new Game();
        }
        return instance;
    }

    private Game(AI ai) throws Exception {
        this.ai =ai;
        while (true){
            System.out.print("");
            if(!clickUnlocked && turn!=0) {
                System.out.print("");
                int a = this.ai.play(board);
                clickUnlocked = true;
                FrameGenerator.columns()[a].aiClick();
            }
            if(!playable()) {
//                FrameGenerator.winningMessageShower();
                return;
            }
        }
    }

    private Game(){

    }

    public void drawBoard() {
        for (int i=0; i<7; i++){
            FrameGenerator.columns()[i].drawColumn(board[i]);
        }
        if (checkWinner() != 0) {

//            System.out.println(ai.toString());

//            if(ai != null){
//                if (checkWinner() == 1) {
//                    JOptionPane.showMessageDialog(null, "AI Won");
//                } else {
//                    JOptionPane.showMessageDialog(null, "Human Won");
//                }
//            }
//            else {
                if (checkWinner() == 1) {
                    JOptionPane.showMessageDialog(null, "Red Won");
                } else {
                    JOptionPane.showMessageDialog(null, "Yellow Won");
                }
//            }





//            System.out.println((
//                checkWinner() ==-1 ?
//                    "Human Won":
//                    (
//                        checkWinner() ==1?
//                            "AI Won":
//                            "Match Drawn"
//                    )
//            ));
            turn = 0;
        }
    }

    public int checkWinner(){
        for(int i=0; i<7;i++){
            for(int j=0; j<6; j++){
                if(j<3){                                    //check column
                    if(board[i][j]==board[i][j+1] &&
                            board[i][j]==board[i][j+2] &&
                            board[i][j]==board[i][j+3] &&
                            board[i][j]!= 0
                    ) return board[i][j];
                }

                if(i<4){                                    //check row
                    if(board[i][j]==board[i+1][j] &&
                            board[i][j]==board[i+2][j] &&
                            board[i][j]==board[i+3][j] &&
                            board[i][j]!= 0
                    ) return board[i][j];
                }

                if(i<4 && j<3){                             //check diagonal /
                    if(board[i][j]==board[i+1][j+1] &&
                            board[i][j]==board[i+2][j+2] &&
                            board[i][j]==board[i+3][j+3] &&
                            board[i][j]!= 0
                    ) return board[i][j];
                }

                if(i>2 && j<3){                             //check diagonal \
                    if(board[i][j]==board[i-1][j+1] &&
                            board[i][j]==board[i-2][j+2] &&
                            board[i][j]==board[i-3][j+3] &&
                            board[i][j]!= 0
                    ) return board[i][j];
                }
            }
        }
        return 0;
    }

    public void updateBoard(int columnIndex){
        for (int i = 0; i < 6; i++) {
            if (board[columnIndex][i] == 0) {
                board[columnIndex][i] = turn;
                FrameGenerator.columns()[columnIndex].incrementSize();
                drawBoard();
                turn *= -1;


//                FrameGenerator.columns()[columnIndex].incrementSize();
                break;
            }
        }

//        print(board);
//        if(FrameGenerator.hasAI())
//            flipClick();
    }

    private void print(int[][] arr){
        for (int[] a:arr) {
            System.out.println(Arrays.toString(a));
        }
        System.out.println("------------------------------------------------");
    }

    public boolean playable(){
        return turn!=0;
    }

    public boolean clickable(){
        return clickUnlocked;
    }

    public void flipClick(){
        clickUnlocked = !clickUnlocked;
    }

    public int[][] clone(int[][] input){
        int[][] clone= new int[input.length][input[0].length];
        for (int i=0;i<input.length;i++){
            clone[i] = input[i].clone();
        }
        return clone;
    }
}
