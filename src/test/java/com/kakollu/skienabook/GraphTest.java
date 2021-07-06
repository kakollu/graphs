package com.kakollu.skienabook;

import org.junit.Test;

public class GraphTest {
    @Test
    public void graphPrintTest() {
        Graph graph = new Graph(false);
        graph.readGraph("src/test/resources/simple_graph.txt");
        System.out.println(graph);
    }

    @Test
    public void bfsTest() {
        Graph graph = new Graph(false);
        graph.readGraph("src/test/resources/simple_graph.txt");
        graph.bfs(1);
    }

    @Test
    public void bfsPathTest() {
        Graph graph = new Graph(false);
        graph.readGraph("src/test/resources/simple_graph.txt");
        graph.bfs(1);
        graph.findPath(1,4);
    }
}
