package Game;

import static Game.idc.columns;

public class RandomAI extends AI{
    @Override
    int play() {
        while (true){
            int a = (int) (Math.random()*7);
            if(columns[a].size()<6){
                Game.getInstance().flipClick();
                return a;
            }
        }
    }
}
