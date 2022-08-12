package Game;

import javax.swing.*;
import java.awt.*;

public class idc {
    public static ImageIcon red = new ImageIcon("red.png");
    public static ImageIcon yellow = new ImageIcon("yellow.png");
    public static ImageIcon empty = new ImageIcon("empty.png");
    public static JFrame frame;

    public static int[][] board = new int[7][6];//each col is one array and the whole is array of columns
    public static int turn = 1;

    public static Column[] columns = new Column[7];

    public static void main(String[] args) {


        Panel[] panels = new Panel[7];
        for (int i=0;i<7;i++){
            panels[i] = new Panel();
            panels[i].setBounds(100*i,0,100,600);
            panels[i].setLayout(null);
        }


        for (int i =0; i<columns.length;i++){
            columns[i] = new Column(i);
        }


        for (int i=0; i<7;i++){
            JLabel[] x = columns[i].getLabels();
            for(int j=0; j<6; j++){
                panels[i].add(x[j]);
            }
        }



        frame = new JFrame("Connect Four");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(1000,700);
        for (Panel panel : panels) {
            frame.add(panel);
        }
//        frame.pack();
        frame.setVisible(true);

    }

    public static void drawBoard() {
        for (int i=0; i<7; i++){
            columns[i].drawColumn(board[i]);
        }
        if (checkWinner() != 0) {
            System.out.println("player "+checkWinner()+ " winner");
            turn = 0;
        }
    }

    public static int checkWinner(){
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
}
