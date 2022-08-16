package Game;

import static Game.idc.*;

public class Game {
    private AI ai;

    private static final int[][] board = new int[7][6];//each col is one array and the whole is array of columns
    private static int turn = 1;
    public static boolean clickUnlocked = true;

    public static Game instance;
    public static void startGame(AI ai){
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

    private Game(AI ai){
        while (true){
            System.out.print("");
            if(!clickUnlocked) {
                System.out.print("");
                int a = ai.play();
                columns[a].aiClick();
            }
            if(!Game.getInstance().playable())
                return;
        }
    }

    private Game(){

    }


    public void drawBoard() {
        for (int i=0; i<7; i++){
            columns[i].drawColumn(board[i]);
        }
        if (checkWinner() != 0) {
            System.out.println("player "+checkWinner()+ " winner");
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
                drawBoard();
                turn = (int) (turn * Math.pow(2,(2*(turn%2) - 1)));
                columns[columnIndex].incrementSize();
                break;
            }
        }
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
}
