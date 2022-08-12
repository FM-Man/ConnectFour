package Game;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Game.idc.*;


public class Column {
    final int id;
    private int size = 0;
    private final JLabel[] labels = new JLabel[6];

    public Column(int id) {
        this.id = id;
        for (int j=0;j<6;j++){
            JLabel l = new JLabel(empty);
            l.setBounds(0,500-j*100,100,100);
            l.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    clicked();
                }
            });

            labels[j] = l;
        }
    }

    public JLabel[] getLabels(){
        return labels;
    }

    private void clicked(){
        if(size<6 && turn !=0){
            for (int i = 0; i < 6; i++) {
                if (board[id][i] == 0) {
                    board[id][i] = turn;
                    drawBoard();
                    if (turn == 1) turn = 2;
                    else if(turn ==2) turn = 1;
                    size++;
                    break;
                }
            }
        }
    }

    public void drawColumn(int[] status) {
        for (int i=0; i<status.length; i++){
            if(status[i]==0) labels[i].setIcon(empty);
            else if(status[i]==1) labels[i].setIcon(red);
            else if(status[i]==2) labels[i].setIcon(yellow);
        }
    }
}
