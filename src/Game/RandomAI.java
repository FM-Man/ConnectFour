package Game;

public class RandomAI extends AI{
    @Override
    int play(int[][] boardStatus) {
        while (true){
            int a = (int) (Math.random()*7);
            if(FrameGenerator.columns()[a].size()<6){
                Game.getInstance().flipClick();
                return a;
            }
        }
    }
}
