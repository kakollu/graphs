package com.kakollu.skienabook;

public interface GraphVisitor {
    void processVertexEarly(int v);
    void processVertexLate(int v);
    void processEdge(int x, int y);
}
