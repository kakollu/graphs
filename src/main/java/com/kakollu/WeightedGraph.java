package com.kakollu;

import java.util.*;

public class WeightedGraph {
    private Map<Vertex, List<Edge>> adjMap = new LinkedHashMap<>();
    private Map<Vertex,Vertex> vertexMap = new HashMap<>();
    private Map<Edge, Edge> edgeMap = new HashMap<>();

    public void addVertex(Vertex v) {
        if (!adjMap.containsKey(v)) {
            adjMap.put(v, new ArrayList<>());
            vertexMap.put(v,v);
        }
    }

    public void addEdge(Vertex v1, Vertex v2, long weight) {
        addVertex(v1);
        addVertex(v2);
        v1 = vertexMap.get(v1);
        v2 = vertexMap.get(v2);
        Edge e = new Edge(v1, v2, weight);
        Edge erev = new Edge(v2, v1, weight);
        adjMap.get(v1).add(e);
        adjMap.get(v2).add(erev);
        edgeMap.put(e,e);
        edgeMap.put(erev,erev);
    }

    @Override
    public String toString()  {
        StringBuffer sb = new StringBuffer();
        sb.append("graph:\n");
        adjMap.values().forEach(e -> e.forEach(f -> sb.append(f)));
        return sb.toString();
    }

    /*
    This algorithm is from Prof. Tim Roughgarden's course.
    Honor code: This is a modified a bit from the assignment, but still has overlap with the assignment so please use your judgement.

    A Dynamic programming solution for Traveling Salesman Problem - TSP, O(2^n) instead of n! bruteforce.
    It brings up a interesting use of bits patterns as the sets naturally from the integers.

    Humans beat TSP at small numbers by using heuristics and getting good enough solutions.

    The algorithm as explained in the course.
    Let A = 2D array indexed by subsets S of {1,2,..n} that  contain 1 and destinations j in {1, 2, .. n}

    Base case A[S,1] = 0 if S  = {1} , +inf otherwise

    For m = 2,3,4,..,n ; m is sub problem size
      For each subset S of {1,2,..,n} of size m that contain 1
        For each j in S but j!=1
          A[S,j] = min k in S, k!=j { A[S-{j},k] + C[k,j] }
    return min j = 2, n { A[{1,2,..n}, 1] + C[j, 1] }

    In the following implementation, I will be assigning 1 bit per vertex/node of the graph.
    vertices are numbered 0,1,2,4,..
    The vertex 0 is the node 1 referred to in the algorithm and its position is implied.

    This function returns the shortest TSP tour length.
     */

    public double TSPLength(WeightedGraph g) {
        List<Vertex> V = g.getVertices();
        int n = V.size();
        if (n > 31) throw new RuntimeException("Bit pattern on int supports only 31 cities");
        int sizeOfS = 1; sizeOfS = sizeOfS << (n-1); //2 ^ (n - 1);
        double[][] A = new double[sizeOfS][n];

        // Compute C_kj nC2 edges for the complete graph - About a 1000 edges for a graph size of ~30
        double[][] C = new double[n][n];
        for (int i=0; i<n; i++) {
            for (int j=i; j<n; j++) {
                if (i==j) {
                    C[i][j] = 0;
                } else {
                    C[i][j] = g.getEdgeWeight(V.get(i),(V.get(j)));
                    C[j][i] = C[i][j];
                }
            }
        }

        //base case
        for(int s=0; s<sizeOfS; s++) {
            for (int j=0; j<n; j++) {
                A[s][j] = Double.POSITIVE_INFINITY;
            }
        }
        A[0][0] = 0; // A[{1}][dest 1] = 0

        for (int m=1; m<n; m++) { // m+1 is the size of set 0-1 is the base case.
            System.out.println("subproblem size m+1: "+(m+1));
            // each set of size m+1 including the implicit vertext 1.
            int start = BitPartition.getStartEnd(m,n,true);
            int end = BitPartition.getStartEnd(m,n,false);

            int s_m = start;
            while (true) {
                if (s_m>= end) break;
                // for each j belongs to s, j!=1
                int s_m_remaining_bits = s_m;
                while (s_m_remaining_bits > 0) {
                    // for each j in s_m  -- find leading bit remove, process
                    int j = 32 - Integer.numberOfLeadingZeros(s_m_remaining_bits); // node number
                    int high_bit = j - 1;
                    s_m_remaining_bits = s_m_remaining_bits^(1<<high_bit); // get rid of the high bit till they are all removed. So we look at each j
                    // find min candidate k over set s_m minus {j}
                    int s_m_minus_j = s_m^(1<<high_bit); // remove current working node/bit S_m - {j}, we will look at paths to j
                    // k belongs to s , k!=j
                    int s_m_minus_j_remaining_bits = s_m_minus_j;
                    if (s_m_minus_j_remaining_bits == 0) { // implicit s={1} case
                        int k = 0;
                        double candidate = A[s_m_minus_j][k] + C[k][j];
                        System.out.println("sj:"+s_m_minus_j+" k:"+k+" s:"+s_m+" candidate:"+candidate);
                        if (candidate < A[s_m][j]) A[s_m][j] = candidate;
                    }
                    while (s_m_minus_j_remaining_bits > 0) {
                        int k = 32 - Integer.numberOfLeadingZeros(s_m_minus_j_remaining_bits);
                        double candidate = A[s_m_minus_j][k] + C[k][j];
                        System.out.println("sj:"+s_m_minus_j+" k:"+k+" s:"+s_m+" candidate:"+candidate);
                        if (candidate < A[s_m][j]) A[s_m][j] = candidate;
                        if (k == 1) break;
                        s_m_minus_j_remaining_bits = s_m_minus_j_remaining_bits^(1<<(k-1));
                    } // Code is repeated from implicit case, it maybe possible to combine them into one block.
                }
                if (s_m >= end) break;
                int t = s_m | (s_m -1); // This is trick for lexicographic bit shift, the original author has a solid page for several bit tricks.
                s_m = (t + 1) | (((~t & -~t) - 1) >> (Integer.numberOfTrailingZeros(s_m) + 1));
            }
        }
        double tspLength = Double.POSITIVE_INFINITY;
        for (int j=1; j<n; j++) {
            double candidateJ =A[sizeOfS - 1][j] + C[j][0];
            if (candidateJ < tspLength) tspLength = candidateJ;
        }
        return tspLength;

    }

    public List<Vertex> getVertices() {
        return new ArrayList<Vertex>(vertexMap.keySet());
    }
    private double getEdgeWeight(Vertex v1, Vertex v2) {
        Edge edge = edgeMap.get(new Edge(v1, v2));
        return edge.getWeight();
        // If edge is not found this will throw exception, consider catching and returning Infinity to indicate no edge
        // but for TSP the graph is expected to be fully connected.
    }

    // DEAD CODE
    /* Useful for making sets in TSP DP implementation?
    it was messing with the hashmap lookup
    now it is dead code but could be used to rebuild a new graph with out the hashmap problem.
     */
    public List<Vertex> sequenceVertices() {
        return sequenceVertices(vertexMap.values());
    }

    public static List<Vertex> sequenceVertices(Collection<Vertex> vertices) {
        List<Vertex> vertexList = new ArrayList<>(vertices);
        Collections.sort(vertexList, new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                if (o1.getId() == o2.getId()) return 0;
                return (o1.getId() < o2.getId())?-1:1;
            }
            long count = 0;
        });

        long count = 0;
        for (Vertex v: vertexList) {
            v.setId(count);
            count = count << 1;
        }
        return vertexList;
    }

}
