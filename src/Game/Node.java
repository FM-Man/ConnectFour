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
    int level;

    public Node(int[][] inputState, NodeType nt, Node parent, int level) {
        this.state = inputState;
        this.type = nt;
        MiniMaxAI.nodeCount++;
        this.parent = parent;
        this.level= level;

//        if(type.equals(NodeType.MAX))
//            alphaBeta = Integer.MIN_VALUE;
//        else alphaBeta = Integer.MAX_VALUE;
//
//        if(parent!=null)
//            tempParentAlphaBeta = this.parent.alphaBeta;
//        else tempParentAlphaBeta = Integer.MAX_VALUE;

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
            if(Game.getInstance().checkWinner()!=0){
                terminalNode = true;
                eval = Game.getInstance().checkWinner()*10000000;
            }
            else if(level==0){
                terminalNode = true;
                eval = evaluationFunction();
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
                eval= evaluateBasedOnChild();
//                tempParentAlphaBeta = eval;
//                setParent();
            }
        }
//
//        if(terminalNode){
//            alphaBeta = eval;
//            if(pushUpwards()){
//                setParent();
//            }
//        }
    }

    private int evaluateBasedOnChild(){
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

    private int evaluationFunction(){
        int total =0;
        for(int i=0;i<7;i++){
            for (int j=0;j<6;j++){
                //check horizontal
                if(i<4){
                    boolean possible = true;
                    int count =0;
                    int standard = state[i][j];
                    for(int k=i; k<i+4; k++){
                        if(state[k][j]==standard){
                            count++;
                        }
                        //interruption
                        else if(state[k][j]==standard*-1){
                            count = 0;
                            possible = false;
                            break;
                        }
                    }
                    if(possible){
                        total += standard*count;
                    }
                }

                //check vertical
                if(j<3){
                    boolean possible = true;
                    int count =0;
                    int standard = state[i][j];
                    for(int k=j; k<j+4; k++){
                        if(state[i][k]==standard){
                            count++;
                        }
                        //interruption
                        else if(state[i][k]==standard*-1){
                            count = 0;
                            possible = false;
                            break;
                        }
                    }
                    if(possible){
                        total += standard*count;
                    }
                }

                //check diagonal /
                if(i<4 &&j<3){
                    boolean possible = true;
                    int count =0;
                    int standard = state[i][j];
                    for(int k=0; k<4; k++){
                        if(state[i+k][j+k]==standard){
                            count++;
                        }
                        //interruption
                        else if(state[i+k][j+k]==standard*-1){
                            count = 0;
                            possible = false;
                            break;
                        }
                    }
                    if(possible){
                        total += standard*count;
                    }
                }

                //check diagonal \
                if(i>2 &&j<3){
                    boolean possible = true;
                    int count =0;
                    int standard = state[i][j];
                    for(int k=0; k<4; k++){
                        if(state[i-k][j+k]==standard){
                            count++;
                        }
                        //interruption
                        else if(state[i-k][j+k]==standard*-1){
                            count = 0;
                            possible = false;
                            break;
                        }
                    }
                    if(possible){
                        total += standard*count;
                    }
                }

            }
        }

        return total;
    }

    public String toString(){
        String ret="";
        String tabs = "";
        for (int i=4-level;i>=0;i--) tabs+="    ";
        ret+=tabs+"{\n";
        for (int[] a:state) {
            ret+=tabs+"    "+Arrays.toString(a)+"\n";
        }
        ret+=tabs+"    e:" +eval+"\n";
        for (Node c:children)
            ret+=c;

        ret+=tabs+"}"+"\n";
//        System.out.println(ret);
        return ret;
    }
}
