package com.kakollu.skienabook;

public class CycleVisitor implements GraphVisitor {
    private Graph graph;

    public CycleVisitor(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void processVertexEarly(int v) {

    }

    @Override
    public void processVertexLate(int v) {

    }

    @Override
    public void processEdge(int x, int y) {
        if (graph.parent[x] != y) { // back edge detected
            System.out.printf("Cycle found %d to %d:", y, x);
            graph.findPath(y, x);
            System.out.println();
            System.out.println();
            graph.finished = true;
        }
    }
}
