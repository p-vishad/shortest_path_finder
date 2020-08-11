import java.util.Comparator;

public class NodeComparator implements Comparator<Node>{

    @Override
    public int compare(Node lhs, Node rhs){

        if (lhs.getDistance() < rhs.getDistance()){
            return -1;
        }
        else if (lhs.getDistance() > rhs.getDistance()){
            return 1;
        }
        return 0;

    }

}
