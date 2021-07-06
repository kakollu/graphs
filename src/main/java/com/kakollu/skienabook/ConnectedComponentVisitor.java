package com.kakollu.skienabook;

public class ConnectedComponentVisitor implements GraphVisitor{

    @Override
    public void processVertexEarly(int v) {
        System.out.printf(" %d",v);
    }

    @Override
    public void processVertexLate(int v) {

    }

    @Override
    public void processEdge(int x, int y) {

    }
}
