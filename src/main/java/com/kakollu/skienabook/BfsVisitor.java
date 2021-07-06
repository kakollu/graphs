package com.kakollu.skienabook;

public class BfsVisitor implements GraphVisitor{
    // These are to be overridden by sub classes of Graph - the key idea from the book to generalize Graph
    @Override
    public void processEdge(int x, int y) {
        System.out.printf("processed edge (%d, %d)\n", x, y);
    }

    @Override
    public void processVertexEarly(int v) {
        System.out.printf("processed vertex %d\n", v);
    }

    @Override
    public void processVertexLate(int v) {

    }
}