package Game;

import static Game.idc.*;

public class Game {
    public Game(AI ai){
        while (true){
            if(!clickUnlocked) {
                int a = ai.play();
                columns[a].aiClick();
            }
            if(turn == 0)
                return;
        }
    }
}
