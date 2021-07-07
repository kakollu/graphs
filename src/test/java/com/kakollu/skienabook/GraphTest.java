package com.kakollu.skienabook;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class GraphTest {
    private static Graph simpleGraph1;
    private static Graph componentsGraph;
    private static Graph bipartiteGraph;

    @BeforeClass
    public static void setup() {
        simpleGraph1 = new Graph(false);
        simpleGraph1.readGraph("src/test/resources/simple_graph.txt");
        componentsGraph = new Graph(false);
        componentsGraph.readGraph("src/test/resources/graph_components.txt");
        bipartiteGraph = new Graph(false);
        bipartiteGraph.readGraph("src/test/resources/twocolor_graph.txt");
    }

    @Before
    public void initGraphs() {
        simpleGraph1.initializeSearch();
        componentsGraph.initializeSearch();
        bipartiteGraph.initializeSearch();
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

    @Test
    public void bipartiteTest() {
        GraphVisitor bipartiteVisitor = new TwocolorVisitor(bipartiteGraph);
        bipartiteGraph.twocolor(bipartiteVisitor);
        assertTrue(bipartiteGraph.bipartite);
    }

    @Test
    public void dfsTest() {
        GraphVisitor bfsVisitor = new BfsVisitor();
        simpleGraph1.dfs(1,bfsVisitor);
        simpleGraph1.findPath(1,4);
    }
}
