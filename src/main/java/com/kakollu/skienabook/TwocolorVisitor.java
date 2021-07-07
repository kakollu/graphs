package com.kakollu.skienabook;

public class TwocolorVisitor implements GraphVisitor {
    Graph graph;

    public TwocolorVisitor(Graph graph) {
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
        if (graph.color[x] == graph.color[y]) {
            graph.bipartite = false;
            System.out.printf("Warning: not bipartite due to (%d, %d)\n",x, y);
        }
        graph.color[y] = TwoColor.complement(graph.color[x]);
    }
}
