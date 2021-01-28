package com.kakollu;

/**
 * TSP test
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        WeightedGraph graph = new WeightedGraph();

        graph.addEdge(new Vertex(0),  new Vertex(1), 4);
        graph.addEdge(new Vertex(0),  new Vertex(2), 2);
        graph.addEdge(new Vertex(0),  new Vertex(4), 3);
        graph.addEdge(new Vertex(1),  new Vertex(2), 11);
        graph.addEdge(new Vertex(1),  new Vertex(4), 7);
        graph.addEdge(new Vertex(2),  new Vertex(4), 23);

        System.out.println(graph);

        double len = graph.TSPLength(graph);
        System.out.println(len);
    }
}
