public class Edge {

    String roadID;
    private Node intersection1, intersection2;
    double weight;

    public Edge(String roadID, Node intersection1, Node intersection2){
        this.roadID = roadID;
        this.intersection1 = intersection1;
        this.intersection2 = intersection2;
        this.weight = getDistance(intersection1.getLatitude(), intersection2.getLatitude(),
                intersection1.getLongitude(), intersection2.getLongitude());
    }

    public Node getIntersection1() {
        return intersection1;
    }

    public Node getIntersection2() {
        return intersection2;
    }

    public double getDistance(double lat1, double lat2, double lon1, double lon2){
        double p = Math.PI/180;
        //Haversine formula
        double a = 0.5 - Math.cos((lat2 - lat1) * p)/2
                + Math.cos(lat1 * p) * Math.cos(lat2 * p) * (1 - Math.cos((lon2 - lon1) * p))/2;
        return 12742 * Math.asin(Math.sqrt(a)); // 2 * R; R = 6371 km
    }

}
