package Game;

import java.util.ArrayList;
import java.util.Arrays;

public class Node {
    Node parent;
    ArrayList<Node> children = new ArrayList<>();
    int[][] state;
    boolean terminalNode = true;
    int eval;
    NodeType type;
    int alphaBeta;
    int tempParentAlphaBeta;

    public Node(int[][] inputState, NodeType nt, Node parent, int level) {
        this.state = inputState;
        this.type = nt;
        MiniMaxAI.nodeCount++;
        this.parent = parent;

        if(type.equals(NodeType.MAX))
            alphaBeta = Integer.MIN_VALUE;
        else alphaBeta = Integer.MAX_VALUE;

        if(parent!=null)
            tempParentAlphaBeta = this.parent.alphaBeta;
        else tempParentAlphaBeta = Integer.MAX_VALUE;

        for(int i=0; i<7;i++){
            for(int j=0; j<6; j++){
                if(state[i][j]==0){
                    terminalNode=false;
                    break;
                }
            }
            if(!terminalNode)
                break;
        }
        if(terminalNode) {
            eval = Game.getInstance().checkWinner();
        }
        else{
            if(Game.getInstance().checkWinner()!=0 || level==0){
                terminalNode = true;
                eval = Game.getInstance().checkWinner();
            }
            else {
                for(int i=0; i<7; i++){
                    if(FrameGenerator.columns()[i].size()<6){
                        int[][] temp = Game.getInstance().clone(state);
                        int replace= type.equals(NodeType.MAX) ? 1 : -1;
                        for (int j = 0; j < 6; j++) {
                            if (temp[i][j] == 0) {
                                temp[i][j] = replace;
                                break;
                            }
                        }

                        NodeType newType = type.equals(NodeType.MAX)? NodeType.MIN:NodeType.MAX;
                        Node n = new Node(temp,newType,this, level-1);
                        children.add(n);

                        if (!pushUpwards()) {
                            break;
                        }
                    }
                }
                eval= evaluate();
                tempParentAlphaBeta = eval;
                setParent();
            }
        }

        if(terminalNode){
            alphaBeta = eval;
            if(pushUpwards()){
                setParent();
            }
        }
    }

    private int evaluate(){
        if(type.equals(NodeType.MAX))
            return max();
        else return min();
    }

    private int max(){
        int childEval= Integer.MIN_VALUE;
        for(Node n: children){
            childEval = Integer.max(n.eval,childEval);
        }
        return childEval;
    }

    private int min(){
        int childEval= Integer.MAX_VALUE;
        for(Node n: children){
            childEval = Integer.min(n.eval,childEval);
        }
        return childEval;
    }

    public int getDifference(Node child) throws Exception {
        for(int i=0; i<7; i++){
            for (int j=0; j<6; j++){
                if (child.state[i][j] != state[i][j]) {
                    if(child.state[i][j]==1 && state[i][j]==0)
                        return i;
                    else if(child.state[i][j]==-1 && state[i][j]==0)
                        throw new Exception("ai -1 daan dise kemne jani");
                    else if(state[i][j]!=0)
                        throw new Exception("ai ager daan nai koira dese kemne?");
                    else throw new Exception("ki hoiche jani na. but check koira dekh");
                }
            }
        }
        throw new Exception("akam chodaiso");
    }

    private void print(int[][] arr){
        for (int[] a:arr) {
            System.out.println(Arrays.toString(a));
        }
        System.out.println("------------------------------------------------");
    }

    private boolean pushUpwards(){
        if(type.equals(NodeType.MAX)){
            if(alphaBeta<= tempParentAlphaBeta){
                tempParentAlphaBeta = alphaBeta;
                return true;
            }
            else return false;
        }
        else {
            if(alphaBeta>=tempParentAlphaBeta){
                tempParentAlphaBeta = alphaBeta;
                return true;
            }
            else return false;
        }
    }

    private void setParent(){
        if(parent != null){
            if(type.equals(NodeType.MAX)){
                if(tempParentAlphaBeta<=parent.alphaBeta){
                    parent.alphaBeta = tempParentAlphaBeta;
                }
            } else {
                if(tempParentAlphaBeta >= parent.alphaBeta){
                    parent.alphaBeta = tempParentAlphaBeta;
                }
            }
        }
    }
}
