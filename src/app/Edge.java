package app;

public class Edge {
    Vertex from;
    Vertex to;
    String relation;
    int cost;

    Edge(Vertex from, Vertex to, String rel) {
        this.from = from;
        this.to = to;
        this.relation = rel;
    }
    Edge(Vertex from, Vertex to, int cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    public String toString() {
        return "("+from + " -> " + to + "   d="+cost+")";
    }
}