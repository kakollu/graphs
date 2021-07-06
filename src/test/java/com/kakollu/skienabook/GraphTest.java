package com.kakollu.skienabook;

import org.junit.BeforeClass;
import org.junit.Test;

public class GraphTest {
    private static Graph simpleGraph1;
    private static Graph componentsGraph;

    @BeforeClass
    public static void setup() {
        simpleGraph1 = new Graph(false);
        simpleGraph1.readGraph("src/test/resources/simple_graph.txt");
        componentsGraph = new Graph(false);
        componentsGraph.readGraph("src/test/resources/graph_components.txt");
    }
    @Test
    public void graphPrintTest() {
        System.out.println(simpleGraph1);
    }

    @Test
    public void bfsTest() {
        GraphVisitor bfsVisitor = new BfsVisitor();
        simpleGraph1.bfs(1,bfsVisitor);
    }

    @Test
    public void bfsPathTest() {
        GraphVisitor bfsVisitor = new BfsVisitor();
        simpleGraph1.bfs(1,bfsVisitor);
        simpleGraph1.findPath(1,4);
    }

    @Test
    public void connectedComponentsTest() {
        GraphVisitor ccVisitor = new ConnectedComponentVisitor();
        componentsGraph.connectedComponents(ccVisitor);
    }
}
