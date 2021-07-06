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

    public Graph(boolean directed) {
        degree = new int[MAXV+1];
        edges = new Edgenode[MAXV+1];
        init(directed);

        discovered = new boolean[MAXV+1];
        processed = new boolean[MAXV+1];
        parent = new int[MAXV+1];
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
        if (directed == false) {
            insertEdge(y,x,true);
        } else {
            nedges++;
        }
    }

    private void initializeSearch() {
        for (int i=1; i<nvertices; i++) {
            processed[i] = discovered[i] = false;
            parent[i] = -1;
        }
    }

    public void bfs(int start) {
        initializeSearch();

        Queue<Integer> q = new ArrayDeque<>();

        int v;
        int y;
        Edgenode p;

        q.add(start);
        discovered[start] = true;

        while (!q.isEmpty()) {
            v = q.remove();
            processVertexEarly(v);
            processed[v] = true;
            p = edges[v];
            while( p != null) {
                y = p.y;
                if ((processed[y] == false) || directed) {
                    processEdge(v,y);
                }
                if (discovered[y] == false) {
                    q.add(y);
                    discovered[y] = true;
                    parent[y] = v;
                }
                p = p.next;
            }
            processVertexLate(v);
        }
    }

    // These are to be overridden by sub classes of Graph - the key idea from the book to generalize Graph
    protected void processEdge(int x, int y) {
        System.out.printf("processed edge (%d, %d)\n",x,y);
    }

    protected void processVertexEarly(int v) {
        System.out.printf("processed vertex %d\n", v);
    }

    protected void processVertexLate(int v) {

    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

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
