import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Project3 {

    public static void main(String[] args) throws Exception{

        HashMap<String, Node> nodes_map = new HashMap<>();

        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();

        BufferedReader sc = new BufferedReader(new FileReader(new File(args[0])));
        String[] current_command;

        String line;
        Node tempNode;
        Edge tempEdge;
        while ((line = sc.readLine()) != null) {
            current_command = line.split("\t");
            if (current_command[0].equals("i")){
                //create new node and add it in array-list of nodes
                tempNode = new Node(current_command[1], Double.parseDouble(current_command[2]), Double.parseDouble(current_command[3]));
                nodes.add(tempNode);
                nodes_map.put(current_command[1], tempNode);
            }
            else if (current_command[0].equals("r")){
                //create new edge and add it in array-list of edges
                tempEdge = new Edge(current_command[1], nodes_map.get(current_command[2]), nodes_map.get(current_command[3]));
                edges.add(tempEdge);
                //adding adjacent node in adjacency list each nodes
                nodes_map.get(current_command[2]).addConnection(nodes_map.get(current_command[3]));
                nodes_map.get(current_command[3]).addConnection(nodes_map.get(current_command[2]));
            }
        }

        Graph graph = new Graph(nodes, edges);

        JFrame Map = new JFrame();
        Map.setLayout(new BorderLayout());
        Map.add(graph, BorderLayout.CENTER);
        Map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Map.setSize(900, 900);
        Map.setTitle("Project3 - Vishad Pokharel");
        Map.setVisible(true);

        graph.shortestDistance(nodes_map.get("i352678"), nodes_map.get("i187372"));

    }

}
