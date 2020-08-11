import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Graph extends JComponent implements MouseListener {

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    private ArrayList<Node> shortestPath;

    private double max_latitude, min_latitude, max_longitude, min_longitude;

    //for mouse click
    Node start = null, end = null;

    public Graph(ArrayList<Node> nodes, ArrayList<Edge> edges){
        this.nodes = nodes;
        this.edges = edges;
        this.max_latitude = get_max_latitude();
        this.min_latitude = get_min_latitude();
        this.max_longitude = get_max_longitude();
        this.min_longitude = get_min_longitude();
        this.shortestPath = new ArrayList<>();
        this.addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        graphics.setColor(Color.GRAY);
        for (int i = 0; i<edges.size(); i++){
            graphics.drawLine(
                    get_x(edges.get(i).getIntersection1()),
                    get_y(edges.get(i).getIntersection1()),
                    get_x(edges.get(i).getIntersection2()),
                    get_y(edges.get(i).getIntersection2())
            );
        }
        if (shortestPath.size()!=0){
            graphics.setColor(Color.RED);
            for (int i = 0; i<shortestPath.size()-1; i++){
                graphics.drawLine(
                        get_x(shortestPath.get(i)),
                        get_y(shortestPath.get(i)),
                        get_x(shortestPath.get(i+1)),
                        get_y(shortestPath.get(i+1))
                );
            }
        }
    }

    private int get_x(Node node){
        int temp = (int) ((getWidth()/(max_longitude-min_longitude))*(node.getLongitude()-min_longitude));
        node.x = temp;
        return temp;
    }

    private int get_y(Node node){
        int temp = (int) ((getHeight())-((getHeight()/(max_latitude-min_latitude))*(node.getLatitude()-min_latitude)));
        node.y = temp;
        return temp;
    }

    private double get_max_longitude(){
        double temp = nodes.get(0).getLongitude();
        for (int i = 1; i < nodes.size(); i++){
            if (temp<nodes.get(i).getLongitude()){
                temp=nodes.get(i).getLongitude();
            }
        }
        return temp;
    }

    private double get_min_longitude(){
        double temp = nodes.get(0).getLongitude();
        for (int i = 1; i < nodes.size(); i++){
            if (temp>nodes.get(i).getLongitude()){
                temp=nodes.get(i).getLongitude();
            }
        }
        return temp;
    }

    private double get_max_latitude(){
        double temp = nodes.get(0).getLatitude();
        for (int i = 1; i < nodes.size(); i++){
            if (temp<nodes.get(i).getLatitude()){
                temp=nodes.get(i).getLatitude();
            }
        }
        return temp;
    }

    private double get_min_latitude(){
        double temp = nodes.get(0).getLatitude();
        for (int i = 1; i < nodes.size(); i++){
            if (temp>nodes.get(i).getLatitude()){
                temp=nodes.get(i).getLatitude();
            }
        }
        return temp;
    }

    public void init_nodes(){
        for (int i = 0; i<nodes.size(); i++){
            nodes.get(i).setDistance(Double.MAX_VALUE);
            nodes.get(i).isVisited = false;
        }
    }

    public void computeMinDistanceFrom(Node node){

        Comparator<Node> comparator = new NodeComparator();
        PriorityQueue<Node> queue = new PriorityQueue<>(nodes.size(), comparator);

        //setting the distance of all nodes to Double.MAX_VALUE(infinity) and current node as 0
        //also set all nodes as not isVisited
        init_nodes();
        node.setDistance(0);

        //adding the node to the queue
        queue.add(node);

        //dijkstra's algorithm
        Node currentNode;
        while (!queue.isEmpty()){
            currentNode = queue.poll();
            for (int i = 0; i < currentNode.adjacency_list.size(); i++){
                if (currentNode.adjacency_list.get(i).isVisited){
                    continue;
                }
                else {
                    if ((currentNode.getDistance()+currentNode.distanceTo(currentNode.adjacency_list.get(i)))<(currentNode.adjacency_list.get(i).getDistance())){
                        currentNode.adjacency_list.get(i).setDistance(currentNode.getDistance()+currentNode.distanceTo(currentNode.adjacency_list.get(i)));
                        //adding previous node for the adjacent node as current node to get the shortest route
                        currentNode.adjacency_list.get(i).prevNode = currentNode;
                    }
                    //checking if queue contains the node and if not adding it to the queue
                    if (!queue.contains(currentNode.adjacency_list.get(i))){
                        queue.add(currentNode.adjacency_list.get(i));
                    }
                }
            }
            currentNode.isVisited = true;
        }

    }

    public void shortestDistance(Node from, Node to){

        computeMinDistanceFrom(from);

        //clearing the shortest path list
        shortestPath.clear();

        String temp = "";
        Node currentNode = to;

        while (!currentNode.equals(from)){
            temp = " " + currentNode.intersectionID + temp;
            //adding the node to the list that makes shortest path
            shortestPath.add(currentNode);
            currentNode = currentNode.prevNode;
        }
        shortestPath.add(currentNode); //adding the starting node
        temp = from.intersectionID + temp;

        System.out.println("Shortest Distance is " + to.getDistance() + "km.");

        System.out.println(temp);

        //repainting because the shortestPath list is updated
        repaint();

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();
        if (start!=null && end==null){
            for (int i = 0; i<nodes.size(); i++){
                if (Math.abs(x - nodes.get(i).x) < 5 && Math.abs(y - nodes.get(i).y) < 5) {
                    end = nodes.get(i);
                }
            }
        }
        else if(start==null && end==null){
            for (int i = 0; i<nodes.size(); i++){
                if (Math.abs(x-nodes.get(i).x) < 5 && Math.abs(y-nodes.get(i).y) < 5){
                    start = nodes.get(i);
                }
            }
        }

        if (start!=null && end!=null){
            shortestDistance(start, end);
            start = null;
            end = null;
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
