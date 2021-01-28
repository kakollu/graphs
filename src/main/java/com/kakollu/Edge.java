package com.kakollu;

public class Edge {
    private Vertex v1;
    private Vertex v2;
    private long weight = 1;

    public Edge(Vertex v1, Vertex v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Edge(Vertex v1, Vertex v2, long weight) {
        this(v1,v2);
        this.weight = weight;
    }

    public long getWeight() { return weight; }

    public void  setWeight(long weight) { this.weight = weight; }

    public Vertex getV1() { return v1; }
    public Vertex getV2() { return v2; }

    @Override
    public boolean equals (Object o)  {
        if (this  == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge other = (Edge) o;
        // relaxing weight so we can find edges just by vertices - precludes parallel edges.
        // There is a need to find the edge given just vertices.
//        if (weight != other.getWeight())  return false;
        return  (v1.equals(other.getV1())  && v2.equals(other.getV2()));
    }

    @Override
    public int hashCode() {
        return v1.hashCode()*3 + v2.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s --- %d ---> %s\n", v1, weight, v2);
    }
}
