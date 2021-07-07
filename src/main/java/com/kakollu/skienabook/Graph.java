package com.kakollu.skienabook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Graph {
    /*
    Implementing Skiena book version of Graph data structure to explore how it works for
    adapting algorithms to a generally sub classed graph, there are some nice abstractions in the c code
    that adapt well for OOPS
    His advice is to model the Graph data structure as per Boost/LEDA code

    Vertices were implicit in Skiena's code - I may want an inheritable class that can hold data

    TODO improve data structure to allow simultaneous execution of algorithms.
     Currently state variables are shared by algorithms.
     Maybe the algorithm should bring its state variables
     */
    public static int MAXV = 1000;

    Edgenode[] edges;
    int[] degree;
    int nvertices;
    int nedges;
    boolean directed;

    boolean[] discovered;
    boolean[] processed;
    int[] parent;

    TwoColor[] color;
    boolean bipartite;

    public Graph(boolean directed) {
        degree = new int[MAXV+1];
        edges = new Edgenode[MAXV+1];
        init(directed);

        discovered = new boolean[MAXV+1];
        processed = new boolean[MAXV+1];
        parent = new int[MAXV+1];

        color = new TwoColor[Graph.MAXV+1];
    }

    private void init(boolean directed) {
        nvertices = 0;
        nedges = 0;
        this.directed = directed;

        for (int i=1; i<MAXV; i++) {
            degree[i] = 0;
        }

        for (int i=1; i<MAXV; i++) {
            edges[i] = null;
        }
    }

    public void readGraph(String fileName) {
        init(directed);
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            int m = 0;
            int x, y;
            if (scanner.hasNextLine()) {
                nvertices = scanner.nextInt();
                m = scanner.nextInt();
            }
            for (int i=1; i<=m; i++) {
                x = scanner.nextInt();
                y = scanner.nextInt();
                insertEdge(x,y,directed);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void insertEdge(int x, int y, boolean directed) {
        Edgenode p = new Edgenode();
        p.y = y;
        p.next = edges[x];
        edges[x] = p;
        degree[x]++;
        if (!directed) {
            insertEdge(y,x,true);
        } else {
            nedges++;
        }
    }

    public void initializeSearch() {
        for (int i=1; i<nvertices; i++) {
            processed[i] = discovered[i] = false;
            parent[i] = -1;
        }
    }

    public void bfs(int start, GraphVisitor bfsVisitor) {
//        initializeSearch();

        Queue<Integer> q = new ArrayDeque<>();

        int v;
        int y;
        Edgenode p;

        q.add(start);
        discovered[start] = true;

        while (!q.isEmpty()) {
            v = q.remove();
            bfsVisitor.processVertexEarly(v);
            processed[v] = true;
            p = edges[v];
            while( p != null) {
                y = p.y;
                if ((!processed[y]) || directed) {
                    bfsVisitor.processEdge(v, y);
                }
                if (!discovered[y]) {
                    q.add(y);
                    discovered[y] = true;
                    parent[y] = v;
                }
                p = p.next;
            }
            bfsVisitor.processVertexLate(v);
        }
    }

    // CAREFUL this method needs parents to be filled by prior search
    public void findPath(int start, int end) {
        if (start == end || end == -1) {
            System.out.printf("\n%d",start);
        } else {
            findPath(start,parent[end]);
            System.out.printf(" %d",end);
        }
    }

    public void connectedComponents(GraphVisitor ccVisitor) {
        int c = 0;

        for (int i=1; i<=nvertices; i++) {
            if (!discovered[i]) {
                c++;
                System.out.printf("Component %d:",c);
                bfs(i,ccVisitor);
                System.out.println();
            }
        }
    }

    public void twocolor(GraphVisitor twocolorVisitor) {

        for (int i=1; i<=nvertices; i++) {
            color[i] = TwoColor.UNCOLORED;
        }

        bipartite = true;

        initializeSearch();

        for (int i=1; i<=nvertices; i++) {
            if (!discovered[i]) {
                color[i] = TwoColor.WHITE;
                bfs(i,twocolorVisitor);
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        Edgenode p;
        for (int i=1; i<=nvertices; i++) {
            sb.append(i).append(": ");
            p = edges[i];
            while (p != null) {
                sb.append(" ").append(p.y);
                p = p.next;
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
