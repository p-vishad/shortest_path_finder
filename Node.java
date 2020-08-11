import java.util.ArrayList;

public class Node {

    String intersectionID;
    private double latitude, longitude;
    //distance to the node
    private double distance;

    int x, y;

    ArrayList<Node> adjacency_list;
    boolean isVisited;
    Node prevNode;

    public Node(String intersectionID, double latitude, double longitude){
        this.intersectionID = intersectionID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.adjacency_list = new ArrayList<>();
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    //add connection to adjacency list
    public void addConnection(Node node){
        adjacency_list.add(node);
    }

    public double distanceTo(Node node){
        double p = Math.PI/180;
        double lat1 = this.latitude;
        double lat2 = node.latitude;
        double lon1 = this.longitude;
        double lon2 = node.longitude;
        //Haversine formula
        double a = 0.5 - Math.cos((lat2 - lat1) * p)/2
                + Math.cos(lat1 * p) * Math.cos(lat2 * p) * (1 - Math.cos((lon2 - lon1) * p))/2;
        return 12742 * Math.asin(Math.sqrt(a)); // 2 * R; R = 6371 km

    }

}
